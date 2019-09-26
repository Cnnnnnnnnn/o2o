package com.ning.o2o.dto;

import com.ning.o2o.entity.Shop;
import com.ning.o2o.enums.ShopStateEnum;

import java.util.List;

/*
添加店铺的返回类型
 */
public class ShopExecution {
    //结果状态
    private int state;
    //状态标识
    private String stateInfo;
    //店铺数量
    private int count;
    //操作的shop
    private Shop shop;
    //查询店铺返回的
    private List<Shop> shopList;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

    public ShopExecution(){

    }
    //店铺操作失败的构造器
    public ShopExecution(ShopStateEnum shopStateEnum){
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
    }
    //店铺操作成功时返回店铺信息的构造器
    public ShopExecution(ShopStateEnum shopStateEnum,Shop shop){
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
        this.shop = shop;
    }
    //返回店铺集合的构造器
    public ShopExecution(ShopStateEnum shopStateEnum,List<Shop> shopList){
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
        this.shopList = shopList;
    }
}
