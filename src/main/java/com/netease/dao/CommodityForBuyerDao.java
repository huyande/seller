package com.netease.dao;

import com.netease.model.Commodity;
import com.netease.model.CommodityForBuyer;
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

    /**
     * 返回所有已发布商品，含有该用户购买的数量
     */
    @Select("select c.*,o.purchased_quantity,o.per_price_snapshot from " +
            "(select * from commodity where pub_status=1) c " +
            "left join " +
            "(select * from orders where creator_id = #{buyerId}) o  " +
            "on c.id =  o.com_id " +
            "order by c.pub_time desc")
    List<CommodityForBuyer> getAllCommodityListWithTypeOfPurchased(int buyerId);

    /**
     * 返回所有该用户没有购买的已发布商品，不用返回购买的数量
     */
    @Select("select c.* from " +
            "(select * from commodity where pub_status=1) c " +
            "left join " +
            "(select * from orders where creator_id = #{buyerId}) o  " +
            "on c.id =  o.com_id " +
            "where o.purchased_quantity=0 " +
            "order by c.pub_time desc")
    List<Commodity> getCommodityListWithTypeOfUnPurchased(int buyerId);

    /**
     * TODO: ? mybatis如何映射联表查询的列名的，是否包含表名前缀
     * 根据buyerId和commodityId来获取商品，包含是否购买的信息
     */
    @Select("select c.*,o.purchased_quantity, o.per_price_snapshot from " +
            "(select * from commodity where id = #{commodityId}) c " +
            "left join " +
            "(select * from orders where creator_id = #{buyerId}) o  " +
            "on c.id =  o.com_id")
    CommodityForBuyer getCommodityByBuyerIdAndCommId(@Param("buyerId") int buyerId,
                                                     @Param("commodityId") int commodityId);
}
