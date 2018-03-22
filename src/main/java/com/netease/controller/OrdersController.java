package com.netease.controller;

import com.alibaba.fastjson.JSON;
import com.netease.dao.OrdersDao;
import com.netease.model.Orders;
import com.netease.model.PerRequestUserHolder;
import com.netease.model.User;
import com.netease.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-21.
 */

@RequestMapping(value = "orders")
@Controller
public class OrdersController {
    @Autowired
    PerRequestUserHolder perRequestUserHolder;

    @Autowired
    OrdersService ordersService;

    /**
     * 用户点击购买按钮提交订单
     * TODO 事务
     */
    @RequestMapping(value = {"/api/addshopcar"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String addShoppingCar(@RequestParam("commodityId") int commodityId,
                                 @RequestParam("purchasedQuantity") int purchasedQuantity,
                                 @RequestParam("returnUtl") String returnUrl,
                                 Model model) {

        User user = perRequestUserHolder.getLocalUser();

        Map<String, String> message =
                ordersService.addShoppingCarTransaction(commodityId, purchasedQuantity, user.getId());

        if (message.containsKey("error")) {
            model.addAttribute("errorMessage", message.get("error"));
            return "error";
        }

        // 返回到购买之前的页面 TODO：任务书上没有要求跳转到哪里
        return "redirect:" + returnUrl;
    }

    /**
     * 显示购物车的内容
     *
     * 为了配合前端，把数据放在cookie里
     */
    @RequestMapping(value = {"page/shoppingcar"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String shoppingCarPage(Model model, HttpServletResponse response) {
        User user = perRequestUserHolder.getLocalUser();
        List<Orders> ordersList = ordersService.getOrdersList(user.getId());

        Cookie cookie = new Cookie("products", JSON.toJSONString(ordersList));
        cookie.setPath("/");
        response.addCookie(cookie);

        return "shoppingCar";
    }

    /**
     * 结算购物车
     */
    @RequestMapping(value = {"api/pay"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String payMoney(Model model,
                           @RequestBody String jsonData) {

        User user = perRequestUserHolder.getLocalUser();

        ordersService.payMoney(user.getId(), jsonData);
        return "redirect:/orders/page/shoppingcar";
    }
}
