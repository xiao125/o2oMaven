package com.imooc.o2o.web.shopadmin;

import com.imooc.o2o.entity.Product;
import com.imooc.o2o.service.ProductCategoryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/8.
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {


    @Autowired
    private ProductCategoryService productCategoryService;








}
