package com.netease.service;

import com.netease.dao.CommodityDao;
import com.netease.dao.CommodityForBuyerDao;
import com.netease.model.Commodity;
import com.netease.model.CommodityForBuyer;
import com.netease.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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

    /**
     * 根据用户类型进行主页显示
     */
    public void indexShowAdaptor(Model model, User user,boolean isShowUnBuy) {

        // 0:消费者 1:商家 2:未登录
        int userType = 2;
        if (user != null) {
            userType = user.getType();
        }

        // 0-消费用户
        if (userType == 0) {
            // 全部显示
            if (!isShowUnBuy){

                model.addAttribute("commodities",
                        commodityForBuyerDao.getAllCommodityListWithTypeOfPurchased(user.getId()));
            }else {//只显示未购买
                model.addAttribute("commodities",
                        commodityForBuyerDao.getCommodityListWithTypeOfUnPurchased(user.getId()));
            }

        }
        // 1-商家
        else if (userType == 1) {
            model.addAttribute("commodities",
                    commodityDao.getCommodityListByPubId(user.getId()));
        }

        // 2-未登录用户
        else {
            List<Commodity> commodityList = commodityDao.getAllCommodityListOrderByPubTime();
            for (Commodity commodity : commodityList) {
                System.out.println("已售："+commodity.getSoldQuantity());
            }
            model.addAttribute("commodities", commodityDao.getAllCommodityListOrderByPubTime());
        }
    }
}
