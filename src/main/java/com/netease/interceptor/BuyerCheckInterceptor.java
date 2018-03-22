package com.netease.interceptor;

import com.netease.model.PerRequestUserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-22.
 */
@Component
public class BuyerCheckInterceptor implements HandlerInterceptor{

    @Autowired
    private PerRequestUserHolder localUserHolder;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object o) throws Exception {

        if (localUserHolder.getLocalUser().getType() != 0) {  // 不是购买用户
            httpServletResponse.sendRedirect("/"); // 跳转到主页
            return false;
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o,
                                Exception e) throws Exception {

    }
}
