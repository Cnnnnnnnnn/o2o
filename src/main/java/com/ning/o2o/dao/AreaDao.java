package com.ning.o2o.dao;

import com.ning.o2o.entity.Area;

import java.util.List;

public interface AreaDao {
    /**
     * 查询出区域列表
     * @return
     */
    List<Area> queryArea();
}
