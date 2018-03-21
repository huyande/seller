package com.netease.dao;

import com.netease.model.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-19.
 */
@Component
@Mapper
public interface OrdersDao {

    String TABLE_NAME = " orders ";
    String INSERT_FIELDS = "com_id,com_title,com_pic_uri,com_quantity,pay_status," +
            "per_price_snapshot,pay_time,create_time,creator_id ";
    String SELECT_FIELDS = "id,"+INSERT_FIELDS;

    // 添加订单
    int addOrders(Orders orders);

    // 按照用户ID获取订单列表,按照支付时间排序
    List<Orders> getOrdersListByCreatorId(int creatorId);

    // 按照订单id获取
    Orders getOrdersById(int id);

    // 按照




}
