package com.netease.model;

import java.util.Date;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-20.
 *
 * 用于orders和commodity联表查询的model，为用户提供购商品买数量的支持
 */
public class CommodityForBuyer extends Commodity {
    private int purchasedQuantity; //购买数量 0表示未购买
    private double perPriceSnapshot; // 购买时的单价

    public int getPurchasedQuantity() {
        return purchasedQuantity;
    }

    public void setPurchasedQuantity(int purchasedQuantity) {
        this.purchasedQuantity = purchasedQuantity;
    }

    public double getPerPriceSnapshot() {
        return perPriceSnapshot;
    }

    public void setPerPriceSnapshot(double perPriceSnapshot) {
        this.perPriceSnapshot = perPriceSnapshot;
    }

    public CommodityForBuyer() {

    }
    public CommodityForBuyer(int id, String comCode, String title,
                     String comAbstract, float perPrice,
                     String detail, String picURI, int picType,
                     int publisherId, Date pubTime, int storageAmount,
                     int soldQuantity, int pubStatus,int purchasedQuantity,double perPriceSnapshot) {
        super(id, comCode, title, comAbstract, perPrice, detail, picURI,
                picType, publisherId, pubTime, storageAmount, soldQuantity, pubStatus);
        this.purchasedQuantity = purchasedQuantity;
        this.perPriceSnapshot = perPriceSnapshot;
    }
}
