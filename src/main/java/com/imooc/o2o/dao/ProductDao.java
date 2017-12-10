package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017/12/10.
 */
public interface ProductDao {


    /**
     * 插入商品
     * @param product
     * @return
     */
    int insertProduct(Product product);



    /**
     * 删除商品类别之前，将商品类别ID置为空
     * @param productCategoryId
     * @return
     */
    int updateProductCategoryToNull(long productCategoryId);


    /**
     * 删除商品
     * @param productId
     * @param shopId
     * @return
     */
    int deleteProduct(@Param("productId") long productId,@Param("shopId") long shopId);


}
