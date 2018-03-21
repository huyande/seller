package com.netease.dao;

import com.netease.model.Commodity;
import com.netease.model.CommodityForBuyer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-20.
 */
@Component
@Mapper
public interface CommodityForBuyerDao {

    //----------------------------------------------------
    // commodity和orders联表查询部分 返回列表默认排序按照发布时间排序
    //----------------------------------------------------

    // 返回所有商品，含有该用户是否购买的标识
    List<CommodityForBuyer> getAllCommodityListWithTypeOfPurchased(int buyerId);
    // 返回所有该用户没有购买的商品，不用还有标识
    List<Commodity> getCommodityListWithTypeOfUnPurchased(int buyerId);
}
