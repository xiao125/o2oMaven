package com.imooc.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exceptions.ProductOperationException;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/8.
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    // 支持上传商品详情图的最大数量
    private static final int IMAGEMAXCOUNT =6;


    @RequestMapping(value = "/addproduct",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addProduct(HttpServletRequest request){

        Map<String,Object> modelMap = new HashMap<String, Object>();
        //验证码
        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","验证码不对");
            return modelMap;
        }

        // 接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper objectMapper = new ObjectMapper();
        Product product =null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        try {
            // 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
            if (multipartResolver.isMultipart(request)){
                thumbnail = handleImage(request,thumbnail,productImgList);
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg","上传图片不能为空");
                return modelMap;
            }

        }catch (Exception e){

            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }

        try {

           String productStr = HttpServletRequestUtil.getString(request,"productStr");
            // 尝试获取前端传过来的表单string流并将其转换成Product实体类
           product = objectMapper.readValue(productStr,Product.class);

        }catch (Exception e){

            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }


        // 若Product信息，缩略图以及详情图列表为非空，则开始进行商品添加操作
        if (product !=null && thumbnail !=null && productImgList.size() >0){

            try{
                // 从session中获取当前店铺的Id并赋值给product，减少对前端数据的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                //执行添加操作
                ProductExecution pe = productService.addProduct(product,thumbnail,productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()){

                    modelMap.put("success",true);
                }else {

                    modelMap.put("success",false);
                    modelMap.put("errMsg",pe.getStateInfo());
                }

            }catch (ProductOperationException e){

                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
                return modelMap;
            }
        }else {

            modelMap.put("success",false);
            modelMap.put("errMsg","请输入商品信息");
        }
        return modelMap;
    }


    private ImageHolder handleImage(HttpServletRequest request,
                                    ImageHolder thumbnail,List<ImageHolder> productImageList) throws IOException {

        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        //取出缩略图并构建ImageHolder对象
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
        if (thumbnailFile !=null){

            thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
        }

        // 取出详情图列表并构建List<ImageHolder>列表对象，最多支持六张图片上传
        for (int i=0;i<IMAGEMAXCOUNT;i++){

            CommonsMultipartFile prouductImgFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg"+i);

            if (prouductImgFile !=null){
                // 若取出的第i个详情图片文件流不为空，则将其加入详情图列表
                ImageHolder productImg = new ImageHolder(prouductImgFile.getOriginalFilename(),
                        prouductImgFile.getInputStream());

                productImageList.add(productImg);
            }else {
                // 若取出的第i个详情图片文件流为空，则终止循环
                break;
            }

        }

        return thumbnail;

    }




}
