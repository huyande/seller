package com.netease.model;

import java.util.Date;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-18.
 */
public class LoginTicket {
    private int id;
    private int userId;
    private String ticket;
    private Date expired;
    private int status;// 0有效，1无效

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LoginTicket(int id, int userId, String ticket, Date expired, int status) {
        this.id = id;
        this.userId = userId;
        this.ticket = ticket;
        this.expired = expired;
        this.status = status;
    }

    public LoginTicket() {
    }
}
