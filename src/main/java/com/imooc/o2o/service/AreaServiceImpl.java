package com.imooc.o2o.service;

import com.imooc.o2o.dao.AreaDao;
import com.imooc.o2o.entity.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.xml.ws.ServiceMode;
import java.util.List;

/**
 * Created by Administrator on 2017/11/26.
 */

@Service
public class AreaServiceImpl implements AreaService {
//@Autowired : 运行时AreaDao自动注入到里面来

    @Autowired
    private AreaDao areaDao;




    @Transactional
    public List<Area> getAreaList() {

        // 定义接收对象
        List<Area> areaList = null;

        // 从数据库里面取出相应数据
        areaList = areaDao.queryArea();

        return areaList;
    }



}
