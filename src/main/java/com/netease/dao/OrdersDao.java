package com.netease.dao;

import com.netease.model.Orders;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-19.
 */
@Component
@Mapper
public interface OrdersDao {

    String TABLE_NAME = " orders ";
    String INSERT_FIELDS = "order_number,com_id,com_title,com_pic_url,purchased_quantity,pay_status," +
            "per_price_snapshot,pay_time,create_time,creator_id ";
    String SELECT_FIELDS = "id,"+INSERT_FIELDS;

    /**
     * 添加订单
     *
     * TODO:添加订单意味着该商品的库存减少(有剩余库存)，销量增加，所以必须在orders表和commodity表之间开启事务来支持数据一致性
     */

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{orderNumber},#{comId},#{comTitle},#{comPicUrl}," +
                    "#{purchasedQuantity},#{payStatus},#{perPriceSnapshot}," +
                    "#{payTime},#{createTime},#{creatorId})"})
    int addOrders(Orders orders);

    /**
     * 按照用户ID获取未支付订单列表,按照支付时间排序（购物车）
     */
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where creator_id=#{creatorId} and pay_status=0" +
                    "order by create_time desc"})
    List<Orders> getUnPayOrdersListByCreatorId(@Param("creatorId") int creatorId);

    /**
     * 按照用户ID获取已支付订单列表,按照支付时间排序（账务）
     */
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where creator_id=#{creatorId} and pay_status=2" +
                    " order by pay_time desc"})
    List<Orders> getPayedOrdersListByCreatorId(@Param("creatorId") int creatorId);
    /**
     * 按照订单id获取
     */
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Orders getOrdersById(@Param("id") int id);

    /**
     * 按照商品id获取
     */
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where com_id=#{comId}"})
    Orders getOrdersByComId(@Param("comId") int comId);

    /**
     * 按照用户ID获取未支付订单列表(shopping car购物车),按照创建时间排序
     */
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where creator_id=#{creatorId} " +
                    "order by create_time desc"})
    List<Orders> getOrdersListForShoppingCarByCreatorId(@Param("creatorId") int creatorId);

    /**
     * 支付订单 更新订单的支付状态以及支付时间
     */
    @Update({"update ", TABLE_NAME, " set pay_status=#{payStatus}, pay_time=#{payStatus} where id=#{id}"})
    int updateStatusAndPayTimeById(@Param("id") int id,
                                   @Param("payStatus") int payStatus,
                                   @Param("createTime") Date createTime);
}
