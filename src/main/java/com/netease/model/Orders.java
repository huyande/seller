package com.netease.model;

import java.util.Date;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-18.
 */
public class Orders {
    private int id;
    private String orderNumber;
    private int comId; //unique
    private String comTitle;
    private String comPicUrl;
    private int purchasedQuantity;
    private int payStatus;
    private double perPriceSnapshot;
    private Date payTime;
    private Date createTime;
    private int creatorId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    public String getComTitle() {
        return comTitle;
    }

    public void setComTitle(String comTitle) {
        this.comTitle = comTitle;
    }

    public String getComPicUrl() {
        return comPicUrl;
    }

    public void setComPicUrl(String comPicUrl) {
        this.comPicUrl = comPicUrl;
    }

    public int getPurchasedQuantity() {
        return purchasedQuantity;
    }

    public void setPurchasedQuantity(int purchasedQuantity) {
        this.purchasedQuantity = purchasedQuantity;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public double getPerPriceSnapshot() {
        return perPriceSnapshot;
    }

    public void setPerPriceSnapshot(double perPriceSnapshot) {
        this.perPriceSnapshot = perPriceSnapshot;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public Orders(int id, String orderNumber, int comId, String comTitle,
                  String comPicUrl, int purchasedQuantity, int payStatus,
                  float perPriceSnapshot, Date payTime, Date createTime, int creatorId) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.comId = comId;
        this.comTitle = comTitle;
        this.comPicUrl = comPicUrl;
        this.purchasedQuantity = purchasedQuantity;
        this.payStatus = payStatus;
        this.perPriceSnapshot = perPriceSnapshot;
        this.payTime = payTime;
        this.createTime = createTime;
        this.creatorId = creatorId;
    }

    public Orders() {
    }
}
