package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dao.ProductImgDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exceptions.ProductOperationException;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/10.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;


    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
       // 页码转换成数据库的行码，并调用dao层取回指定页码的商品列表
      int rowIndex =  PageCalculator.calculateRowIndex(pageIndex,pageSize);
      List<Product> productList = productDao.queryProductList(productCondition,rowIndex,pageSize);
      //基于同样的查询条件返回该查询条件下的商品总数
        int count = productDao.queryProductCount(productCondition);
         ProductExecution pe =new ProductExecution();
         pe.setProductList(productList);
         pe.setCount(count);
        return pe;
    }

    /**
     * 通过商品Id查询唯一的商品信息
     * @param productId
     * @return
     */
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }

    /**
     * 商品添加
     * @param product
     * @param   thumbnail 缩略图 ,缩略图名称
     * @param  imageHolderList  图片详情列,图片详情列名称
     * @return
     * @throws ProductOperationException
     */
    public ProductExecution addProduct(Product product, ImageHolder thumbnail,
                                       List<ImageHolder> imageHolderList) throws ProductOperationException {

        //空值判断
        if (product !=null && product.getShop() !=null && product.getShop().getShopId() !=null){

            //给商品设置上默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());

            // 默认为上架的状态
            product.setEnableStatus(1);
            // 若商品缩略图不为空则添加
            if (thumbnail !=null){
                //添加缩略图路径文件
                addThumbnail(product,thumbnail);
            }

            try{

                 //创建商品信息
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <=0){
                    throw new ProductOperationException("创建商品失败");
                }

            }catch (Exception e){

                throw new ProductOperationException("创建商品失败:"+ e.toString());

            }

            //若商品详情图不为空则添加
            if (imageHolderList !=null && imageHolderList.size()>0){

                //批量添加图片
                addProductImgList(product,imageHolderList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS,product);


        }else {
            // 传参为空则返回空值错误信息
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }


    /**
     * 商品编辑
     * @param product
     * @param thumbnail
     * @param productImageHolderList
     * @return
     * @throws ProductOperationException
     */
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
                                          List<ImageHolder> productImageHolderList) throws ProductOperationException {
        return null;
    }





    /**
     * 添加缩略图
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product,ImageHolder thumbnail){

        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail,dest);
        product.setImgAddr(thumbnailAddr); //添加缩略图相对路径

    }


    /**
     * 批量添加图片
     * @param product
     * @param productImgHolderList
     */
    private void addProductImgList(Product product,List<ImageHolder> productImgHolderList){

        //获取图片存储路径，这里直接存放到相应店铺的文件夹底下
         String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
         List<ProductImg> productImgList = new ArrayList<ProductImg>();
        // 遍历图片一次去处理，并添加进productImg实体类里
         for (ImageHolder productImgHolder : productImgHolderList){

             String imgAddr = ImageUtil.generateThumbnail(productImgHolder,dest);
             ProductImg productImg = new ProductImg();
             productImg.setImgAddr(imgAddr);
             productImg.setProductId(product.getProductId());
             productImg.setCreateTime(new Date());
             productImgList.add(productImg);
         }

        // 如果确实是有图片需要添加的，就执行批量添加操作
        if (productImgList.size()>0){

             try {

                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectedNum <=0){

                    throw new ProductOperationException("创建商品详情图片失败");
                }

             }catch (Exception e){

                 throw new ProductOperationException("创建商品详情图片失败:"+e.toString());

             }
        }


    }



}
