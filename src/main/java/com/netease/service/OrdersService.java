package com.netease.service;

import com.alibaba.fastjson.JSONArray;
import com.netease.dao.CommodityDao;
import com.netease.dao.OrdersDao;
import com.netease.model.Commodity;
import com.netease.model.JsonSimpleOrder;
import com.netease.model.Orders;
import com.netease.utils.CodeGeneUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-21.
 */
@Service
public class OrdersService {

    @Autowired
    CommodityDao commodityDao;

    @Autowired
    OrdersDao ordersDao;

    /**
     * 添加到购物车事务
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.REPEATABLE_READ,
            timeout = 120,rollbackFor = Exception.class)
    public Map<String,String> addShoppingCarTransaction(int commodityId,int purchasedQuantity,int creatorId) {

        Map<String, String> message = new HashMap<String, String>();

        Commodity commodity = commodityDao.getCommodityById(commodityId);

        Orders existOrders = ordersDao.getOrdersByComId(commodityId);

        // 购买数量为0或者负数||没有这个商品||库存不足||订单已经存在，不可重复购买
        if (purchasedQuantity <= 0 ||
                commodity == null ||
                commodity.getStorageAmount() < purchasedQuantity ||
                existOrders != null) {

            message.put("error", "无法购买:购买数量为0或者负数||没有这个商品||库存不足");
            return message;
        }
        commodityDao.updateCommodityOrders(purchasedQuantity, commodityId);
        Orders orders = new Orders();
        orders.setOrderNumber(CodeGeneUtils.comAndOrdCodeGen(true, creatorId));
        orders.setComId(commodityId);
        orders.setComTitle(commodity.getTitle());
        orders.setComPicUrl(commodity.getPicUrl());
        orders.setPurchasedQuantity(purchasedQuantity);
        orders.setPayStatus(0);
        orders.setPerPriceSnapshot(commodity.getPerPrice());

        //TODO 设置默认支付时间，实际没有支付，所以只有支付状态为已支付这个字段才有意义
        orders.setPayTime(new Date());

        orders.setCreateTime(new Date());
        orders.setCreatorId(creatorId);
        ordersDao.addOrders(orders);
        return message;
    }

    /**
     * 结算购物车
     *
     * 把购物车里所有的订单放在一个事务里，如果发生可知订单不可支付的情况
     * 则记录下来详细的信息，然后继续执行可支付的订单，最后把支付失败的订单
     * 返回到错误详情页显示
     *
     * TODO事务:
     * 显然如果在购物车里对购买数量进行修改，那么为了数据一致性也要开启事务
     *
     * @param userId 购买者ID
     * @param ordersListJson 订单列表<id,number>
     */
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.REPEATABLE_READ,
            timeout = 120,rollbackFor = Exception.class)
    public Map<String,Object> payMoney(int userId, String ordersListJson) {
        // 解析JSON
        List<JsonSimpleOrder> ordersList =
                new ArrayList<JsonSimpleOrder>(
                        JSONArray.parseArray(ordersListJson,
                                JsonSimpleOrder.class));

        Map<String, Object> message = new HashMap<String, Object>();
        for (JsonSimpleOrder orders : ordersList) {

            Orders ordersTmp = ordersDao.getOrdersById(orders.getId());
            Commodity commodity = commodityDao.getCommodityById(ordersTmp.getComId());

            // 购买数量为0或者负数||没有这个商品||库存不足||订单已支付或取消
            if (orders.getPurchasedQuantity() <= 0 ||
                    commodity == null ||
                    commodity.getStorageAmount() < orders.getPurchasedQuantity() ||
                    ordersTmp.getPayStatus() != 0) {

                if (!message.containsKey("error")) {
                    message.put("error", new ArrayList<String>());
                }

                ArrayList<String> errorList = (ArrayList<String>) message.get("error");
                errorList.add("购买数量【" + orders.getPurchasedQuantity() + "】"
                        + "商品名【" + (commodity != null ? commodity.getTitle() : "无") + "】"
                        + "商品编码【" + (commodity != null ? commodity.getComCode() : "无") + "】"
                        + "库存【" + (commodity != null ? commodity.getStorageAmount() : "无") + "】");
                continue;
            }
            // 订单支付完成，支付状态为2
            commodityDao.updateCommodityOrders(orders.getPurchasedQuantity(), commodity.getId());
            ordersDao.updateStatusAndPayTimeById(orders.getId(),2, new Date());
        }
        return message;
    }

    /**
     * 购物车的内容（未支付的订单）
     */
    public List<Orders> getUnPayOrdersList(int userId) {
        return ordersDao.getUnPayOrdersListByCreatorId(userId);
    }

    /**
     * 账务-已经支付的订单
     */
    public List<Orders> getPayedOrdersList(int userId) {
        return ordersDao.getPayedOrdersListByCreatorId(userId);
    }
}
