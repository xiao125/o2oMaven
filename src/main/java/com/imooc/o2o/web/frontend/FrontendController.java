package com.imooc.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/12/16.
 */
@Controller
@RequestMapping(value = "/frontend")
public class FrontendController {

    /**
     * 首页路由
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    private String index(){

        return "frontend/index";

    }

    /**
     * 商品列表页路由
     * @return
     */
    @RequestMapping(value = "/shoplist",method = RequestMethod.GET)
    private String showShopList(){

        return "frontend/shoplist";
    }


}
