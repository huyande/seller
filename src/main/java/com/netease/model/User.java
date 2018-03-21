package com.netease.model;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-18.
 */
public class User {
    private int id;
    private String name;
    private String password;
    private int type; // 0:消费者,1:商户

    public User() {
    }

    public User(int id, String name, String password, int type) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }





}
