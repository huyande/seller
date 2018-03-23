package com.netease.dao;

import com.netease.model.Commodity;
import com.netease.model.CommodityForBuyer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-20.
 *
 * commodity和orders联表查询部分 返回列表默认排序按照发布时间排序
 */
@Component
@Mapper
public interface CommodityForBuyerDao {

    // 左联接 右表为空表

    /**
     * 返回所有已发布商品，含有该用户购买的数量
     *
     * 解决表设计的不合理（左联接右表联接条件不唯一），将orders表的com_id设为唯一
     *
     */
    @Select("select c.*,o.purchased_quantity,o.per_price_snapshot" +
            "from " +
            "(select * from commodity where pub_status=1) c " +
            "left join " +
            "(select * from orders where creator_id = #{buyerId}) o  " +
            "on c.id =  o.com_id " +
            "order by c.pub_time desc")
    List<CommodityForBuyer> getAllCommodityListWithTypeOfPurchased(@Param("buyerId") int buyerId);

    /**
     * 返回所有该用户没有购买的已发布商品，不用返回购买的数量
     *
     * TODO：在左联接时，如果右表为空表，但是总表过滤时用到了右表的字段则无匹配，返回空集,所以采用not in来操作
     */

    @Insert("select * from commodity " +
            "where id not in " +
            "(distinct select com_id from orders where creator_id = #{buyerId})")
    List<Commodity> getCommodityListWithTypeOfUnPurchased(@Param("buyerId") int buyerId);

    /**
     * TODO: ? mybatis如何映射联表查询的列名的，是否包含表名前缀
     * 根据buyerId和commodityId来获取商品，包含是否购买的信息
     */
    @Select("select commodity.* orders.purchased_quantity,orders.per_price_snapshot from " +
            "commodity,orders where commodity.id=#{commodityId} and orders.creator_id=#{buyerId}")
    CommodityForBuyer getCommodityByBuyerIdAndCommId(@Param("buyerId") int buyerId,
                                                     @Param("commodityId") int commodityId);
}
