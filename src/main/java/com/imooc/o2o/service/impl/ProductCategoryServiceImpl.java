package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ProductCategoryDao;
import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.enums.ProductCategoryStateEnum;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;
import com.imooc.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/12/8.
 */

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }


    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
     throws ProductCategoryOperationException{

        if (productCategoryList != null && productCategoryList.size()>0){

            try {
                int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if (effectedNum <=0){

                    throw new ProductCategoryOperationException("店铺类别添加失败");
                }else {

                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }


            }catch (Exception e){

                throw new ProductCategoryOperationException("batchAddProductCategory error: " + e.getMessage());
            }
        }else {

            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }

    }


}
