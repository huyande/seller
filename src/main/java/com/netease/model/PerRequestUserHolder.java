package com.netease.model;

import org.springframework.stereotype.Component;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-19.
 *
 *  每次请求到来时，在一个拦截器执行链中的User对象的holder
 * 一个线程对应完整请求过程，请求独立原则与线程数据私有对应:
 * 因为一次请求的拦截处理链由一个线程执行,所以可以使用ThreadLocal来完成
 *
 */
@Component
public class PerRequestUserHolder {

    private static ThreadLocal<User> requestLocalUsers = new ThreadLocal<User>();

    public User getLocalUser() {
        return requestLocalUsers.get();
    }

    public void setLocalUser(User user) {
        requestLocalUsers.set(user);
    }

    public void removeLocalUser() {
        requestLocalUsers.remove();
    }
}
