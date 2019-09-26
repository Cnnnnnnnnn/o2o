package com.ning.o2o.service;

import com.ning.o2o.dto.ShopExecution;
import com.ning.o2o.entity.Shop;
import com.ning.o2o.exceptions.ShopOperationException;
import java.io.InputStream;

public interface ShopService {
    /**
     * 新增店铺
     * @param shop
     * @param shopImgInputStream
     * @return
     * @throws ShopOperationException
     */
    ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName) throws ShopOperationException;
}
