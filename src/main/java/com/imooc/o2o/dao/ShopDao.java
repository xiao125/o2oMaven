package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Shop;

/**
 * Created by Administrator on 2017/11/27.
 */
public interface ShopDao {

    /**
     * 添加店铺信息
     * @param shop
     * @return
     */
    int insertShop(Shop shop);


    /**
     * 更新店铺信息
     * @param shop
     * @return
     */
    int updateShop(Shop shop);



}
