package com.imooc.o2o.exceptions;

/**
 * Created by Administrator on 2017/11/29.
 */
public class ProductCategoryOperationException extends RuntimeException {


    private static final long serialVersionUID = 1182563719599527969L;

    public ProductCategoryOperationException(String msg){
        super(msg);
    }

}
