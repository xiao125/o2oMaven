package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ProductCategory;

import java.util.List;

/**
 * Created by Administrator on 2017/12/8.
 */
public interface ProductCategoryDao {


    /**
     * 通过shop id查询店铺商品类别
     * @param shopId
     * @return
     */
    List<ProductCategory> queryProductCategoryList(long shopId);


}
