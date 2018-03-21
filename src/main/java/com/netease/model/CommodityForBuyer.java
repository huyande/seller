package com.netease.model;

import java.util.Date;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-20.
 */
public class CommodityForBuyer extends Commodity {

    private int isPurchased; // 0:未购买  1:已购买
    public int getIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(int isPurchased) {
        this.isPurchased = isPurchased;
    }

    public CommodityForBuyer() {

    }
    public CommodityForBuyer(int id, String comCode, String title,
                     String comAbstract, float perPrice,
                     String detail, String picURI, int picType,
                     int publisherId, Date pubTime, int storageAmount,
                     int soldQuantity, int pubStatus,int isPurchased) {
        super(id, comCode, title, comAbstract, perPrice, detail, picURI, picType, publisherId, pubTime, storageAmount, soldQuantity, pubStatus);
        this.isPurchased = isPurchased;
    }
}
