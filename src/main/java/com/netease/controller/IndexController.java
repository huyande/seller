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

    @RequestMapping(value = {"/", "/index"}, method = {RequestMethod.GET})
    public String index(Model model) {

        User localUser = localUserHolder.getLocalUser();

        // 0:消费者 1:商家 2:未登录
        int userType = 2;
        if (localUser != null) {
            userType = localUser.getType();
        }


        // 0-消费用户
        if (userType == 0) {
            model.addAttribute("commodities",
                    indexService.loginBuyerIndexAllCommShowList(localUser.getId()));
            return "buyerIndex";
        }

        // 1-商家
        else if (userType == 1) {
            model.addAttribute("commodities",
                    indexService.loginSellerIndexCommShowList(localUser.getId()));
            return "sellerIndex";
        }

        // 2-未登录用户
        else {
            model.addAttribute("commodities", indexService.unLoginUserIndexCommShowList());
            return "unLoginUserIndex";
        }
    }


    // 未购买商品列表 在拦截器里对登录状态和类型进行验证
    @RequestMapping(value = {"/page/notbuy"}, method = {RequestMethod.GET})
    public String showUnBuyCommodity(Model model) {

        User localUser = localUserHolder.getLocalUser();
        model.addAttribute("commodities",
                indexService.loginBuyerIndexUnPurchasedCommShowList(localUser.getId()));
        return "buyerIndex";
    }
}
