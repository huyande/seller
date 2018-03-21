package com.netease.controller;

import com.netease.service.UserService;
import com.netease.utils.StringAndFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-19.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Value("${COOKIE_AGE}")
    private int COOKIE_AGE;

    @Autowired
    UserService userService;

    @RequestMapping(path = {"/api/login"}, method = {RequestMethod.POST})
    public String login(Model model, @RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam(value = "returnUrl", required = false) String returnUrl,
                        @RequestParam(value = "rememberMe", defaultValue = "true") String rememberMe,
                        HttpServletResponse response) {
        try {
            Map<String, Object> message = userService.login(userName, password);
            // 含有ticket
            if (message.containsKey("ticket")) {
                // 设置cookie
                Cookie cookie = new Cookie("ticket", (String) message.get("ticket"));
                cookie.setPath("/");

                // 设置cookie时效，默认为临时时效
                if (rememberMe.equals("true")) {
                    cookie.setMaxAge(3600 * 24 * COOKIE_AGE);
                }
                response.addCookie(cookie);

                // 跳转到登录前的页面
                if (!StringAndFileUtils.isBlank(returnUrl)) {
                    return "redirect:" + returnUrl;
                }
                // 跳转到根
                return "redirect:" + "/";
            } else { // 跳转到登录页面
                model.addAttribute("message", message.get("message"));
                return "login";
            }
        } catch (Exception e) {
            logger.error("登录异常：" + e.getMessage());
            return "login";
        }
    }

    @RequestMapping(path = {"/api/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }


    // 错误引导页
    @ExceptionHandler()
    public String error(Model model, Exception e) {
        model.addAttribute("errorMessage", "Inner Error: " + e.getMessage());
        return "error";
    }

    // 登录页(只有未登录用户可见)
    @RequestMapping(path = {"/page/login"}, method = {RequestMethod.GET})
    public String getLogin() {
        return "login";
    }
}
