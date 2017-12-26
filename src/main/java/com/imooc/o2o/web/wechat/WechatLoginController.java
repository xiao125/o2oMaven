package com.imooc.o2o.web.wechat;

import com.imooc.o2o.dto.UserAccessToken;
import com.imooc.o2o.dto.WechatAuthExecution;
import com.imooc.o2o.dto.WechatUser;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.WechatAuth;
import com.imooc.o2o.enums.WechatAuthStateEnum;
import com.imooc.o2o.service.PersonInfoService;
import com.imooc.o2o.service.WechatAuthService;
import com.imooc.o2o.util.wechat.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("wechatlogin")
public class WechatLoginController {

    private static Logger log = LoggerFactory.getLogger(WechatController.class);
    private static final String FRONTEND = "1";
    private static final String SHOPEND = "2";


    @Autowired
    private PersonInfoService personInfoService;

    @Autowired
    private WechatAuthService wechatAuthService;



    @RequestMapping(value = "/logincheck",method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, HttpServletResponse response) {

        log.debug("weixin login get....");
        // 获取微信公众号传输过来的code,通过code可获取access_token,进而获取用户信息
        String code = request.getParameter("code");
        // 这个state可以用来传我们自定义的信息，方便程序调用，这里也可以不用
        String roleType = request.getParameter("state");

        log.debug("weixin login code" + code);

        WechatUser user =null;
        String openId =null;
        WechatAuth auth = null;
        if (null !=code){
            UserAccessToken token;
            try {
                // 通过code获取access_token
                token = WechatUtil.getUserAccessToken(code);
                log.debug("weixin login token:" + token.toString());
                //通过token获取accessToken
                String accessToken = token.getAccessToken();
                //通过token获取openId
                openId = token.getOpenId();

                // 通过access_token和openId获取用户昵称等信息
                user = WechatUtil.getUserInfo(accessToken,openId);
                log.debug("weixin login user:" + user.toString());
                request.getSession().setAttribute("openId",openId);
               // 通过openId查找平台对应的微信帐号
                auth = wechatAuthService.getWechatAuthByOpenId(openId);

            }catch (Exception e){
                log.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + e.toString());
                e.printStackTrace();
            }
        }

        // 若微信帐号为空则需要注册微信帐号，同时注册用户信息
        if (auth == null){
            PersonInfo personInfo = WechatUtil.getPersonInfoFromRequest(user);
            auth = new WechatAuth();
            auth.setOpenId(openId);
            if (FRONTEND.equals(roleType)){
                personInfo.setUserType(1);
            }else {

                personInfo.setUserType(2);
            }

            auth.setPersonInfo(personInfo);
            //注册
            WechatAuthExecution we = wechatAuthService.register(auth);
            if (we.getState() != WechatAuthStateEnum.SUCCESS.getState()){
                return null;
            }else {
                // 根据用户Id获取personInfo信息
                personInfo = personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
                request.getSession().setAttribute("user",personInfo);
            }
        }

        //若用户点击的是前端展示系统按钮则进入前端展示系统
        if (FRONTEND.equals(roleType)){
            return "frontend/index";
        }else {
            return "shop/shoplist";
        }




    }



}
