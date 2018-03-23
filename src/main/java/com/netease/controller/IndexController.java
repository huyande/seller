package com.netease.controller;

import com.netease.model.PerRequestUserHolder;
import com.netease.model.User;
import com.netease.service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-20.
 */

@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    PerRequestUserHolder localUserHolder;

    @Autowired
    IndexService indexService;


    /**
     * 主页显示
     *
     * 0- 登录购买用户
     * 要点：商品列表上含有已购买标记（通过购买数量来进行判断）
     * 可以只查看未购买商品
     *
     * 1- 用户商家用户
     * 要点：已经出售的商品，标明出售的数量
     *
     * 2- 未登录
     * 显示所有商品，不可查看未购买商品
     */
    @RequestMapping(value = {"/", "/index"}, method = {RequestMethod.GET})
    public String index(Model model) {

        User localUser = localUserHolder.getLocalUser();
        indexService.indexShowAdaptor(model,localUser,false);
        model.addAttribute("showType", 0);
        return "index";
    }

    /**
     * 未购买商品列表
     * TODO 在拦截器里对登录状态和类型进行验证
     */
    @RequestMapping(value = {"/page/notbuy"}, method = {RequestMethod.GET})
    public String showUnBuyCommodity(Model model) {
        User localUser = localUserHolder.getLocalUser();
        indexService.indexShowAdaptor(model,localUser,true);
        // showType-1表示只显示未购买
        model.addAttribute("showType", 1);
        return "index";
    }

    //TODO 完成功能时，将错误引导页加上
//    // 错误引导页
//    @ExceptionHandler()
//    public String error(Model model, Exception e) {
//        model.addAttribute("errorMessage", "Inner Error: " + e.getMessage());
//        return "error";
//    }
}
