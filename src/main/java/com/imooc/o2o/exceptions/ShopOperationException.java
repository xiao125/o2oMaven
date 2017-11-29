package com.imooc.o2o.exceptions;

/**
 * Created by Administrator on 2017/11/29.
 */
public class ShopOperationException extends RuntimeException {

    private static final long serialVersionUID = 4771094100507194189L;


    public ShopOperationException(String msg){
        super(msg);
    }

}
