package com.ning.o2o.service.impl;

import com.ning.o2o.dao.ShopDao;
import com.ning.o2o.dto.ShopExecution;
import com.ning.o2o.entity.Shop;
import com.ning.o2o.enums.ShopStateEnum;
import com.ning.o2o.exceptions.ShopOperationException;
import com.ning.o2o.service.ShopService;
import com.ning.o2o.util.FileUtil;
import com.ning.o2o.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Date;
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;
    @Override
    @Transactional  //表示此方法需要事务的支持
    public ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName) {
        if(shop == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            shop.setEnableStatus(ShopStateEnum.CHECK.getState());//传入店铺的状态
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int effectedNum = shopDao.insertShop(shop);//调用插入店铺的方法
            if(effectedNum<=0){
                //自定义的异常处理类
                throw new ShopOperationException("店铺创建失败");
            }else {
                if(shopImgInputStream != null){
                    //如果店铺的图片不为空就保存图片
                    try{
                        addShopImg(shop,shopImgInputStream,fileName);
                    }catch (Exception e){
                        throw new ShopOperationException("addShopImg"+e.getMessage());
                    }
                    //将数据库中的图片字段更新
                    effectedNum = shopDao.updateShop(shop);
                    if(effectedNum<=0){
                        throw new ShopOperationException("更新店铺图片失败");
                    }
                }
            }
        }catch (Exception e){
            throw new ShopOperationException("addrShop error-----"+e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    private void addShopImg(Shop shop, InputStream shopImgInputStream,String fileName) {
        String dest = FileUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnails(shopImgInputStream,dest,fileName);

        shop.setShopImg(shopImgAddr);
    }
}
