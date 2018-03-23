package com.netease.dao;

import com.netease.model.Commodity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-19.
 */
@Component
@Mapper
public interface CommodityDao {

    String TABLE_NAME = "commodity ";
    String INSERT_FIELDS = "com_code,title,com_abstract,per_price,detail," +
            "pic_url,pic_type,publisher_id,pub_time,storage_amount,pub_status ";

    /**
     * 添加商品
     */
    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,
            ") values (#{comCode},#{title},#{comAbstract},#{perPrice},#{detail}," +
                    "#{picUrl},#{picType},#{publisherId},#{pubTime}," +
                    "#{storageAmount},#{pubStatus})"})
    int addCommodity(Commodity commodity);

    /**
     * 获取所有商品列表，并按照发布时间排序 desc
     */
    @Select({"select *", " from ", TABLE_NAME,
            " where pub_status=1 " +
                    "order by pub_time desc" })
    List<Commodity> getAllCommodityListOrderByPubTime();


    /**
     * 按照商品ID获取
     */
    @Select({"select *", " from ", TABLE_NAME,
            " where id=#{id}"})
    Commodity getCommodityById(@Param("id") int id);

    /**
     * 按照发布商家id获取已发布商品，并按照发布日期排序
     */
    @Select({"select *", " from ", TABLE_NAME,
            " where publisher_id=#{publisherId} and pub_status=1"})
    List<Commodity> getCommodityListByPubId(@Param("publisherId") int publisherId);

    /**
     * 按照ID删除未出售的商品(状态设置为2)
     */
    @Update({"update ", TABLE_NAME, " set pub_status=#{payStatus} where id=#{commodityId}"})
    void deleteCommodityById(@Param("payStatus") int payStatus,
                            @Param("commodityId") int commodityId);

    /**
     * 根据pubID和comCode来查询刚刚添加的商品
     */
    @Select({"select *", " from ", TABLE_NAME,
            " where publisher_id=#{publisherId} and com_code=#{comCode}"})
    Commodity getAddedCommodityByPubIdAndComCode(@Param("publisherId") int publisherId,
                                                 @Param("comCode") String comCode);

    /**
     * 保存修改的商品内容
     */
    @Update({"update ",TABLE_NAME,
            " set title=#{title},com_abstract=#{comAbstract},pic_url=#{picUrl}," +
                    "detail=#{detail},pic_type=#{picType},per_price=#{perPrice} " +
                    "where id=#{id}"})
    void updateCommodityEdit(Commodity commodity);

    /**
     * TODO 下订单时，库存减少，销售增加,还得检查库存是否剩余
     */
    @Update({"update ", TABLE_NAME,
            " set storage_amount=storage_amount-#{purchasedAmount},sold_quantity=sold_quantity+#{purchasedAmount} " +
                    "where id=#{id}"})
    void updateCommodityOrders(@Param("purchasedAmount") int purchasedAmount,
                               @Param("id") int id);

}