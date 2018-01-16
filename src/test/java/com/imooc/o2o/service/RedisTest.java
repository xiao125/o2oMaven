package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class RedisTest extends BaseTest {


    @Test
    public void methodOne(){

        Jedis jedis = new Jedis("39.108.13.136",6379);
        jedis.set("name","xixi");
        String vale = jedis.get("name");
        System.out.println(vale);
        jedis.close();

    }


}
