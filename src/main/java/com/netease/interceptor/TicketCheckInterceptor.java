package com.netease.interceptor;

import com.netease.dao.LoginTicketDao;
import com.netease.dao.UserDao;
import com.netease.model.LoginTicket;
import com.netease.model.PerRequestUserHolder;
import com.netease.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-19.
 *
 * 对ticket进行检查,对登录用户添加本地User对象对模型中
 */

@Component
public class TicketCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginTicketDao loginTicketDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PerRequestUserHolder localUserHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object o) throws Exception {

        String ticket = null;
        Cookie[] cookies = null;
        if ((cookies = httpServletRequest.getCookies() )!= null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }

        if (ticket != null) {
            LoginTicket loginTicket = loginTicketDao.selectByTicket(ticket);
            // ticket时效判断
            if (loginTicket == null || loginTicket.getExpired().before((new Date()))) { // 无效
                return true;
            }
            // 有效
            User user = userDao.selectById(loginTicket.getUserId());
            // 绑定local User对象到请求处理线程
            localUserHolder.setLocalUser(user);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

        // 将user添加到视图当中，便于显示
        if (modelAndView != null && localUserHolder.getLocalUser() != null) {
            modelAndView.addObject("user", localUserHolder.getLocalUser());
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {

        // 一次请求完成则清除这个Local User对象
        localUserHolder.removeLocalUser();

    }
}
