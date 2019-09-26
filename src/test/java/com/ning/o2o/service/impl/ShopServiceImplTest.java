package com.ning.o2o.service.impl;

import com.ning.o2o.BaseTest;
import com.ning.o2o.dto.ShopExecution;
import com.ning.o2o.entity.Area;
import com.ning.o2o.entity.PersonInfo;
import com.ning.o2o.entity.Shop;
import com.ning.o2o.entity.ShopCategory;
import com.ning.o2o.enums.ShopStateEnum;
import com.ning.o2o.service.ShopService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.*;

public class ShopServiceImplTest extends BaseTest {

    @Autowired
    private ShopService shopService;
    @Test
    public void addShop() throws FileNotFoundException {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();

        owner.setUserId(1L);
        area.setAreaId(2);

        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopName("诸葛烤鱼");
        shop.setShopDesc("一家烤鱼店");
        shop.setShopAddr("测试地址--烤鱼店");
        shop.setPhone("12300001111");
        shop.setCreateTime(new Date());
        shop.setPriority(2);
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("管理员提示");
        File file = new File("E:/o2o/image/timg4.jpg");
        InputStream imgInputStream = new FileInputStream(file);
        ShopExecution se = shopService.addShop(shop, imgInputStream, file.getName());
        assertEquals(ShopStateEnum.CHECK.getState(),se.getState());
    }
}