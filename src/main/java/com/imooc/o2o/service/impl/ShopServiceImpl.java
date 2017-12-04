package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.InputStream;
import java.util.Date;

/**
 * Created by Administrator on 2017/11/29.
 */

@Service
public class ShopServiceImpl implements ShopService{


    @Autowired
    private ShopDao shopDao;

    public ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName) throws ShopOperationException {

        // 空值判断
        if (shop == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }

        try{

            // 给店铺信息赋初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());

          int effectedNum = shopDao.insertShop(shop); //添加新店铺
          if (effectedNum <=0){

              throw new ShopOperationException("店铺创建失败");
          }else {

              if (shopImgInputStream !=null){

                  // 存储图片
                  try {
                      addShopImg(shop,shopImgInputStream,fileName);

                  }catch (Exception e){

                      throw new ShopOperationException("addShopImg error:" + e.getMessage()); //使用RuntimeException 数据库可以事务回滚
                  }

                  // 更新店铺的图片地址
                  effectedNum = shopDao.updateShop(shop);
                  if (effectedNum <=0){
                      throw new ShopOperationException("更新图片地址失败");
                  }

              }

          }

        }catch (Exception e){
            throw new ShopOperationException("addShop error:"+e.getMessage());
        }

        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }


    public ShopExecution modifyShop(Shop shop, InputStream shopimgInputstream, String fileName) throws ShopOperationException {

        if (shop ==null && shop.getShopId() == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else {

            // 1.判断是否需要处理图片

            try {
                if (shopimgInputstream !=null && fileName !=null && !"".equals(fileName)){

                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() !=null){
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg()); //删除图片
                    }
                    addShopImg(shop,shopimgInputstream,fileName); //重新添加

                }

                //2.更新店铺信息

                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop); //更新店铺信息
                if (effectedNum <=0){
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                }else {
                    shop = shopDao.queryByShopId(shop.getShopId()); //通过shop id 查询店铺
                    return  new ShopExecution(ShopStateEnum.SUCCESS,shop);

                }

            }catch (Exception e){

                throw new ShopOperationException("modifyShop error:" +e.getMessage());
            }
        }


    }

    public Shop getByShopId(Long shopId) {

        return shopDao.queryByShopId(shopId);
    }


    private void addShopImg(Shop shop, InputStream shopImageInputStream,String fileName){

        String dest = PathUtil.getShopImagePath(shop.getShopId()); //项目图片的子路径
        String shopImgAddr = ImageUtil.generateThumbnail(shopImageInputStream,fileName,dest);  // 获取shop图片目录的相对值路径

        shop.setShopImg(shopImgAddr);

    }








}
