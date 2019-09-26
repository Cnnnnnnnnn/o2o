package com.ning.o2o.service;

import com.ning.o2o.BaseTest;
import com.ning.o2o.entity.Area;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class AreaServiceTest extends BaseTest {

    @Autowired
    private AreaService areaService;

    @Test
    public void getAreaList() {
        List<Area> areaList = areaService.getAreaList();

        Assert.assertEquals("南苑",areaList.get(0).getAreaName());
    }
}