package com.imooc.o2o.service;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.exceptions.ShopOperationException;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/11/29.
 */
public interface ShopService {


    /**
     * 注册店铺信息，包括图片处理
     * @param shop
     * @param
     * @return
     * @throws ShopOperationException
     */
    ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName) throws ShopOperationException;


    /**
     * 更新店铺信息，包括对图片的处理
     * @param shop
     * @param shopimgInputstream
     * @param fileName
     * @return
     * @throws ShopOperationException
     */
    ShopExecution  modifyShop(Shop shop,InputStream shopimgInputstream,String fileName) throws ShopOperationException;




}
