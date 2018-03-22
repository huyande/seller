package com.netease.model;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-22.
 */
public class JsonSimpleOrder {
    private int id;
    private int purchasedQuantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPurchasedQuantity() {
        return purchasedQuantity;
    }

    public void setPurchasedQuantity(int purchasedQuantity) {
        this.purchasedQuantity = purchasedQuantity;
    }

    public JsonSimpleOrder(int id, int number) {
        this.id = id;
        this.purchasedQuantity = number;
    }

    public JsonSimpleOrder() {
    }
}
