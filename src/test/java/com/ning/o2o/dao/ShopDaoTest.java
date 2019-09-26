package com.ning.o2o.dao;

import com.ning.o2o.BaseTest;
import com.ning.o2o.entity.Area;
import com.ning.o2o.entity.PersonInfo;
import com.ning.o2o.entity.Shop;
import com.ning.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;

    @Test
    public void insertShop() {
        Area area = new Area();
        Shop shop = new Shop();
        PersonInfo personInfo = new PersonInfo();
        ShopCategory shopCategory = new ShopCategory();

        area.setAreaId(2);
        personInfo.setUserId(1L);
        shopCategory.setShopCategoryId(1L);

        shop.setArea(area);
        shop.setOwner(personInfo);
        shop.setShopCategory(shopCategory);

        shop.setShopId(1L);
        shop.setShopName("阿姨奶茶");
        shop.setShopDesc("奶茶店");
        shop.setShopAddr("测试地址");
        shop.setPhone("13800000000");
        shop.setShopImg("测试图片");
        shop.setPriority(1);
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(0);
        shop.setAdvice("管理员警告");


        int i = shopDao.insertShop(shop);

        assertEquals(1,i);

    }

    @Test
    public void updateShop() {
        Shop shop = new Shop();
        Area area = new Area();
        PersonInfo personInfo = new PersonInfo();
        ShopCategory shopCategory = new ShopCategory();

        shop.setShopId(1L);
        shop.setShopName("奶茶妹");
        area.setAreaId(2);
        personInfo.setUserId(2L);
        shopCategory.setShopCategoryId(2L);

        //这里因为实体类中的此字段是一个类，所以不能传简单的值
        //shop.setOwner(2L);
        shop.setOwner(personInfo);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);

        int i = shopDao.updateShop(shop);

        assertEquals(1,i);

    }
}