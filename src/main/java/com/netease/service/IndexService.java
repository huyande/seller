package com.netease.service;

import com.netease.dao.CommodityDao;
import com.netease.dao.CommodityForBuyerDao;
import com.netease.model.Commodity;
import com.netease.model.CommodityForBuyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-20.
 * <p>
 * 对应任务的R1部分内容
 */
@Service
public class IndexService {

    @Autowired
    private CommodityDao commodityDao;

    @Autowired
    private CommodityForBuyerDao commodityForBuyerDao;

    //登陆buyer用户的商品浏览列表(所有商品)
    public List<CommodityForBuyer> loginBuyerIndexAllCommShowList(int buyerId) {
        return commodityForBuyerDao.getAllCommodityListWithTypeOfPurchased(buyerId);
    }

    // 登录buyer用户显示未购买商品列表
    public List<Commodity> loginBuyerIndexUnPurchasedCommShowList(int buyerId) {
        return commodityForBuyerDao.getCommodityListWithTypeOfUnPurchased(buyerId);
    }

    //未登陆用户的商品浏览列表
    public List<Commodity> unLoginUserIndexCommShowList() {
        return commodityDao.getAllCommodityListOrderByPubTime();
    }

    //登陆seller用户的商品浏览列表
    public List<Commodity> loginSellerIndexCommShowList(int sellerId) {
        return commodityDao.getCommodityByPubId(sellerId);
    }

}
