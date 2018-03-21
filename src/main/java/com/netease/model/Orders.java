package com.netease.model;

import java.util.Date;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-18.
 */
public class Orders {
    private int id;
    private String orderNumber;
    private int comId;
    private String comTitle;
    private String comPicUri;
    private int comQuantity;
    private int payStatus;
    private float perPriceSnapshot;
    private Date payTime;
    private Date createTime;
    private int creatorId;

    public String getComPicUri() {
        return comPicUri;
    }

    public void setComPicUri(String comPicUri) {
        this.comPicUri = comPicUri;
    }
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

    public int getComQuantity() {
        return comQuantity;
    }

    public void setComQuantity(int comQuantity) {
        this.comQuantity = comQuantity;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public float getPerPriceSnapshot() {
        return perPriceSnapshot;
    }

    public void setPerPriceSnapshot(float perPriceSnapshot) {
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
                  String comPicUri, int comQuantity, int payStatus,
                  float perPriceSnapshot, Date payTime, Date createTime,
                  int creatorId) {

        this.id = id;
        this.orderNumber = orderNumber;
        this.comId = comId;
        this.comTitle = comTitle;
        this.comPicUri = comPicUri;
        this.comQuantity = comQuantity;
        this.payStatus = payStatus;
        this.perPriceSnapshot = perPriceSnapshot;
        this.payTime = payTime;
        this.createTime = createTime;
        this.creatorId = creatorId;
    }
    public Orders() {
    }
}
