package com.imooc.o2o.service;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.exceptions.ProductOperationException;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2017/12/10.
 */
public interface ProductService {


    /**
     *
     * @param product
     * @param imageHolder 缩略图 ,缩略图名称
     * @param imageHolderList 图片详情列,图片详情列名称
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImageHolder imageHolder ,
                                List<ImageHolder> imageHolderList) throws ProductOperationException;


}
