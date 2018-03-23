package com.netease.controller;

import com.netease.model.PerRequestUserHolder;
import com.netease.model.User;
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

    @Autowired
    PerRequestUserHolder perRequestUserHolder;

    @ResponseBody
    @RequestMapping(path = {"/api/login"}, method = {RequestMethod.POST})
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam(value = "returnUrl", required = false) String returnUrl,
                        @RequestParam(value = "rememberMe", defaultValue = "true") String rememberMe,
                        HttpServletResponse response) {

        User user = perRequestUserHolder.getLocalUser();
        if (user != null) {
            return "redirect:/";
        }

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

                // 配合Ajax
                response.setStatus(200);

                // 跳转到登录前的页面
                if (!StringAndFileUtils.isBlank(returnUrl)) {
                    return  returnUrl;
                }
                // 跳转到根
                return  "/";
            } else { // 跳转到登录页面

                // 验证失败：重新输入表单内容
                response.setStatus(201);
                return message.get("message").toString();
            }
        } catch (Exception e) {
            logger.error("登录异常：" + e.getMessage());
            response.setStatus(500);
            return "服务器内部错误";
        }
    }

    @RequestMapping(path = {"/api/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket, HttpServletResponse response) {
        if (ticket != null) {
            // 删除服务器ticket
            userService.logout(ticket);
        }
        //删除Cookie
        Cookie cookie = new Cookie("ticket", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }

    // 登录页
    @RequestMapping(path = {"/page/login"}, method = {RequestMethod.GET})
    public String getLogin() {
        User user = perRequestUserHolder.getLocalUser();
        if (user != null) {
            return "redirect:/";
        }
        return "login";
    }

//    // 错误引导页 TODO 完成功能时，将错误引导页加上
//    @ExceptionHandler()
//    public String error(Model model, Exception e) {
//        model.addAttribute("errorMessage", "Inner Error: " + e.getMessage());
//        return "error";
//    }


}
