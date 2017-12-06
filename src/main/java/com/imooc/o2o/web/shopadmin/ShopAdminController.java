package com.imooc.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 主要用来解析路由并转发到相应的html中
 */
@Controller
@RequestMapping(value = "shopadmin",method = RequestMethod.GET)
public class ShopAdminController {

    //完整url： http://127.0.0.1:8080/o2oMaven/shopadmin/shopoperation?shopId=1
   @RequestMapping(value = "/shopoperation")
    public String shopOperation(){
        //转发至店铺注册、编辑页面
        return "shop/shopoperation";
    }

    @RequestMapping(value = "/shoplist")
    public String shopList(){

        //转发至店铺列表页面
        return "shop/shoplist";
    }

    @RequestMapping(value = "/shopmanagement")
    public String shopManagement(){

        //转发至店铺管理页面
        return "shop/shopmanagement";
    }

}
