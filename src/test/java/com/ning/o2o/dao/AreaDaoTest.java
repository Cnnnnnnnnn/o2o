package com.ning.o2o.dao;


import com.ning.o2o.BaseTest;
import com.ning.o2o.entity.Area;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AreaDaoTest extends BaseTest {

    @Autowired
    private AreaDao areaDao;
    @Test
    public void queryArea() {
        List<Area> areas = areaDao.queryArea();
//        assertEquals(2,areas.size());

        /**
         * 参数1：为预期值
         * 参数2：为实际值
         * 当实际值与预期值不同时就会报failNotEquals
         */
        Assert.assertEquals(3,areas.size());
    }
}
