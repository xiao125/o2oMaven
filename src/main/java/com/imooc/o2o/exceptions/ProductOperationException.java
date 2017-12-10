package com.imooc.o2o.exceptions;

/**
 * Created by Administrator on 2017/12/10.
 */
public class ProductOperationException  extends RuntimeException{


    private static final long serialVersionUID = -5658071154280254892L;

    public ProductOperationException(String msg){
        super(msg);
    }
}
