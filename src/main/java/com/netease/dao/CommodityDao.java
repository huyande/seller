package com.netease.dao;

import com.netease.model.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
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
            "pic_uri,pic_type,publisher_id,pub_time,storage_amount,sold_quantity,pub_status ";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;


    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "comCode", column = "com_code"),
            @Result(property = "title", column = "title"),
            @Result(property = "abstract", column = "abstract"),
            @Result(property = "perPrice", column = "per_price"),
            @Result(property = "detail", column = "detail"),
            @Result(property = "picURI", column = "pic_uri"),//不一致
            @Result(property = "picType", column = "pic_type"),
            @Result(property = "publisherId", column = "publisher_id"),
            @Result(property = "pubTime", column = "pub_time"),
            @Result(property = "storageAmount", column = "storage_amount"),
            @Result(property = "soldQuantity", column = "sold_quantity"),
            @Result(property = "pubStatus", column = "pub_status")
    })

        // 添加商品
    int addCommodity(Commodity commodity);

    // 获取所有商品列表，并按照发布时间排序
    List<Commodity> getAllCommodityListOrderByPubTime();


    // 按照商品ID获取
    Commodity getCommodityById(int id);

    // 按照发布商家id获取商品，并按照发布日期排序
    List<Commodity> getCommodityByPubId(int publisherId);


    // 按照ID删除未出售的商品(状态设置为2)
    int deleteCommodityById(int id);

    // 根据pubID和comCode来查询刚刚添加的商品
    Commodity getAddedCommodityByPubIdAndComCode(int publisherId, String comCode);

    // 更新商品内容
    int updateCommodity(Commodity commodity);
}