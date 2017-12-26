package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 2017/12/14.
 */
public class ProductServiceTest extends BaseTest {

    @Autowired
    private ProductService productService;


    @Test
    @Ignore
    public void testModifyProduct() throws ShopOperationException,FileNotFoundException{

        //创建shopId为1且productCategoryId为1的商品实例并给其成员变量赋值
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(1L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductId(1L);
        product.setProductName("正式的商品2");
        product.setProductDesc("正式的商品2");

        //创建缩略图文件流
        File thumbnailFile = new File("F:/IdeaProjects/shuai.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(),is);
        //创建两个商品详情图文件流并将他们添加到详情图列表中
        File productImgl = new File("F:/IdeaProjects/kaixin.jpg");
        InputStream is1 = new FileInputStream(productImgl);
        File productImg2 = new File("F:/IdeaProjects/dabai.jpg");
        InputStream is2 = new FileInputStream(productImg2);
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(productImgl.getName(),is1));
        productImgList.add(new ImageHolder(productImg2.getName(),is2));

        //添加商品并验证
        ProductExecution pe = productService.modifyProduct(product,thumbnail,productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());



    }

}
