package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.HeadLine;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/12/16.
 */
public class HeadLineServiceTest extends BaseTest {

    @Autowired
    private HeadLineService headLineService;

    @Test
    @Ignore
    public void testGetHeadLineList() throws IOException {

        List<HeadLine> headLineList = headLineService.getHeadLineList(new HeadLine());
        System.out.println(headLineList.get(0).getLineName());
        HeadLine headLineCondition = new HeadLine();
        headLineCondition.setEnableStatus(1);
        headLineList = headLineService.getHeadLineList(headLineCondition);
        System.out.println(headLineList.get(0).getLineName());

    }


}
