package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.assertEquals;

import java.util.List;

public class AreaDaoTest extends BaseTest {

    @Autowired
    private AreaDao areaDao;

    @Test
    @Ignore
    public void testQueryArea(){

        List<Area> areaList = areaDao.queryArea();
        assertEquals(2,areaList.size());

    }

}
