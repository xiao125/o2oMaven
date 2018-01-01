package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Administrator on 2017/11/26.
 */
public class AreaServiceTest extends BaseTest {

    @Autowired
    private AreaService areaService;
    @Autowired
    private CacheService cacheService;

    @Test
    @Ignore
    public void testGetAreaList(){
        List<Area> areaList = areaService.getAreaList();
        assertEquals("西苑",areaList.get(0).getAreaName());


    }

}
