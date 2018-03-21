package com.netease.model;

import java.util.Date;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-18.
 */
public class Commodity {

    private int id;
    private String comCode;
    private String title;
    private String comAbstract; // the SQL column is commodity.abstract
    private double perPrice;
    private String detail;
    private String picURI;
    private int picType;
    private int publisherId;
    private Date pubTime;
    private int storageAmount;
    private int soldQuantity;
    private int pubStatus; // 商品的状态，0：未发布,1：已发布，2：已删除


    public int getStorageAmount() {
        return storageAmount;
    }

    public void setStorageAmount(int storageAmount) {
        this.storageAmount = storageAmount;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComAbstract() {
        return comAbstract;
    }

    public void setComAbstract(String comAbstract) {
        this.comAbstract = comAbstract;
    }

    public double getPerPrice() {
        return perPrice;
    }

    public void setPerPrice(double perPrice) {
        this.perPrice = perPrice;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPicURI() {
        return picURI;
    }

    public void setPicURI(String picURI) {
        this.picURI = picURI;
    }

    public int getPicType() {
        return picType;
    }

    public void setPicType(int picType) {
        this.picType = picType;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    public int getPubStatus() {
        return pubStatus;
    }

    public void setPubStatus(int pubStatus) {
        this.pubStatus = pubStatus;
    }

    public Commodity(int id, String comCode, String title,
                     String comAbstract, float perPrice,
                     String detail, String picURI, int picType,
                     int publisherId, Date pubTime, int storageAmount,
                     int soldQuantity, int pubStatus) {
        this.id = id;
        this.comCode = comCode;
        this.title = title;
        this.comAbstract = comAbstract;
        this.perPrice = perPrice;
        this.detail = detail;
        this.picURI = picURI;
        this.picType = picType;
        this.publisherId = publisherId;
        this.pubTime = pubTime;
        this.storageAmount = storageAmount;
        this.soldQuantity = soldQuantity;
        this.pubStatus = pubStatus;
    }

    public Commodity() {
    }
}
