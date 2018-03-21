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
    String INSERT_FIELDS = "com_code,title,abstract,per_price,detail," +
            "pic_uri,pic_type,publisher_id,pub_time,storage_amount,pub_status ";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    @Results({
          @Result(property = "comAbstract", column = "abstract"),//不一致
            // 经过测试并不仅仅按照驼峰映射而是根据分割符格式化后匹配，下面可不显示映射
          @Result(property = "picURI", column = "pic_uri")//不一致
    })

    // 添加商品
    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,
            ") values (#{comCode},#{title},#{comAbstract},#{perPrice},#{detail}," +
                    "#{picURI},#{picType},#{publisherId},#{pubTime}," +
                    "#{storageAmount},#{pubStatus})"})
    int addCommodity(Commodity commodity);

    // 获取所有商品列表，并按照发布时间排序
    List<Commodity> getAllCommodityListOrderByPubTime();


    // 按照商品ID获取
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where id=#{id}"})
    Commodity getCommodityById(@Param("id") int id);

    // 按照发布商家id获取商品，并按照发布日期排序
    List<Commodity> getCommodityByPubId(int publisherId);


    // 按照ID删除未出售的商品(状态设置为2)
    int deleteCommodityById(int id);

    // 根据pubID和comCode来查询刚刚添加的商品
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where publisher_id=#{publisherId} and com_code=#{comCode}"})
    Commodity getAddedCommodityByPubIdAndComCode(@Param("publisherId") int publisherId,
                                                 @Param("comCode") String comCode);

    // 更新商品内容
    int updateCommodity(Commodity commodity);
}