package com.netease.service;

import com.netease.dao.LoginTicketDao;
import com.netease.dao.UserDao;
import com.netease.model.LoginTicket;
import com.netease.model.User;
import com.netease.utils.StringAndFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-19.
 */

@Service
@PropertySource(value = {"classpath:conf.properties"}, encoding = "utf-8")
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${EXPIRED_DAY}")
    private int EXPIRED_DAY; // 注入的属性不能用static修饰

    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginTicketDao loginTicketDao;

    public User selectByName(String name) {
        return userDao.selectByName(name);
    }

    public User selectById(int id) {
        return userDao.selectById(id);
    }

    public void logout(String ticket) {
        loginTicketDao.updateStatus(ticket, 1);
    }

    public Map<String, Object> register(String userName, String password, int type) {
        HashMap<String, Object> message = new HashMap<String, Object>();
        if (StringAndFileUtils.isBlank(userName)) {
            message.put("message", "用户名为空");
            return message;
        }
        if (StringAndFileUtils.isBlank(password)) {
            message.put("message", "密码为空");
            return message;
        }

        if (type != 0 && type != 1) {
            message.put("message", "用户类型不匹配");
            return message;
        }

        User user = userDao.selectByName(userName);
        if (user != null) {
            message.put("message", "用户名已存在");
            return message;
        }

        user = new User();
        user.setName(userName);
        user.setPassword(password);
        user.setType(type);

        // 插入数据
        userDao.addUser(user);

        // 下发ticket
        String ticket = generateTicket(user.getId(),user.getType());
        message.put("ticket", ticket);
        message.put("userId", user.getId());
        message.put("userType", user.getType());
        return message;
    }

    private String generateTicket(int userId,int type) {
        LoginTicket ticket = new LoginTicket();

        ticket.setUserId(userId);
        // 当前日期
        Date date = new Date();
        // 设置失效时间
        date.setTime(date.getTime() + 1000 * 3600 * 24 * EXPIRED_DAY);
        ticket.setExpired(date);
        ticket.setStatus(0);
        //生成 UUID ticket
        String UID = UUID.randomUUID().toString().replaceAll("-", "");
        if (type == 0)
            ticket.setTicket("buyer" + UID);
        else
            ticket.setTicket("seller" + UID);

        loginTicketDao.addTicket(ticket);
        return ticket.getTicket();
    }

    public Map<String, Object> login(String userName, String password) {
        HashMap<String, Object> message = new HashMap<String, Object>();
        if (StringAndFileUtils.isBlank(userName)) {
            message.put("message", "用户名为空");
            return message;
        }
        if (StringAndFileUtils.isBlank(password)) {
            message.put("message", "密码为空");
            return message;
        }

        User user = userDao.selectByName(userName);
        if (user == null) {
            message.put("message", "用户名不存在");
            return message;
        }

        if (user.getType() != 0 && user.getType() != 1) {
            message.put("message", "用户类型不匹配");
            return message;
        }

        if (!password.equals(user.getPassword())) {
            message.put("message", "密码不正确");
            return message;
        }

        // 验证成功，下放ticket
        String ticket = generateTicket(user.getId(),user.getType());
        message.put("ticket", ticket);
        message.put("userId", user.getId());
        message.put("userType", user.getType());
        return message;
    }
}

