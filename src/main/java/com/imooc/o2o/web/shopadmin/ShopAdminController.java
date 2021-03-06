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


    //商品分类管理页面： http://localhost:8080/o2oMaven/shopadmin/productcategorymanagement
    @RequestMapping(value = "/productcategorymanagement",method =RequestMethod.GET)
    public String productCategoryManage(){

        // 转发至商品类别管理页面
        return "shop/productcategorymanagement";

    }


    // 添加页面： http://localhost:8080/o2oMaven/shopadmin/productoperation
    //编辑页面 ：http://localhost:8080/o2oMaven/shopadmin/productoperation?productId=1
    @RequestMapping(value = "/productoperation")
    public String productOperation() {
        // 转发至商品添加/编辑页面
        return "shop/productoperation";
    }


    //http://localhost:8080/o2oMaven/shopadmin/productmanagement
    @RequestMapping(value = "/productmanagement")
    public String productManagement(){

        //转发至商品管理页面
        return "shop/productmanagement";

    }


}
