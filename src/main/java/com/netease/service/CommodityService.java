package com.netease.service;

import com.netease.dao.CommodityDao;
import com.netease.dao.CommodityForBuyerDao;
import com.netease.model.Commodity;
import com.netease.model.CommodityForBuyer;
import com.netease.model.PerRequestUserHolder;
import com.netease.model.User;
import com.netease.utils.CodeGeneUtils;
import com.netease.utils.StringAndFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-20.
 */

@PropertySource(value = {"classpath:conf.properties"}, encoding = "utf-8")
@Service
public class CommodityService {
    private static final Logger logger = LoggerFactory.getLogger(CommodityService.class);

    @Autowired
    private CommodityDao commodityDao;

    @Autowired
    private CommodityForBuyerDao commodityForBuyerDao;

    @Autowired
    PerRequestUserHolder perRequestUserHolder;


    @Value(("${PIC_MAX_SIZE}"))
    private long PIC_MAX_SIZE;

    @Value(("${STATIC_STORAGE_MAPPER_PATH}"))
    private String STATIC_STORAGE_MAPPER_PATH;

    /**
     * 添加商品,并返回插入商品的ID
     */
    public Map<String, Object> addCommodity(Commodity commodity) {
        Map<String, Object> message = new HashMap<String, Object>();

        // 检查商品内容的长度
        if (!StringAndFileUtils.checkCommodityAllContent(commodity.getTitle(),
                commodity.getComAbstract(), commodity.getDetail())) {
            message.put("outOfRange", "后端插入检查异常：商品内容信息可能过长或过段");
            return message;
        }

        int result = commodityDao.addCommodity(commodity);

        message.put("addResult", result);
        if (result > 0) {
            // 根据publisherId和comCode来获取刚刚插入的商品id
            Commodity addedCommodity =
                    commodityDao.getAddedCommodityByPubIdAndComCode(commodity.getPublisherId(),
                            commodity.getComCode());

            // 判断刚才插入的数据是否存在
            if (addedCommodity != null) {
                message.put("addedCommodityId", addedCommodity.getId());

                //TODO: ComAbstract 内容无法显示
                logger.warn(addedCommodity.getComAbstract());
                logger.warn(addedCommodity.getDetail());
            }
        }
        return message;

    }

    /**
     * 删除商品
     */
    public void deleteCommodityById(int commodityId) {
        // 2 表示未发布，相当于保存历史记录，但是并不真实的删除商品记录
         commodityDao.deleteCommodityById(2,commodityId);
    }

    /**
     * 更新商品信息
     */
    public Map<String, String> updateCommodity(Commodity commodity) {
        Map<String, String> message = new HashMap<String, String>();

        // 检查商品内容的长度
        if (!StringAndFileUtils.checkCommodityAllContent(commodity.getTitle(),
                commodity.getComAbstract(), commodity.getDetail())) {
            message.put("outOfRange", "后端插入检查异常：商品内容信息可能过长或过段");
            return message;
        }
        commodityDao.updateCommodityEdit(commodity);
        return message;
    }

    /**
     * 根据id获取商品
     */
    public Commodity showCommodityInfo(int commodityId) {
        User user = perRequestUserHolder.getLocalUser();
        // 0:消费者 1:商家 2:未登录
        int userType = 2;
        if (user != null) {
            userType = user.getType();
        }
        // 0-消费用户
        if (userType == 0) {
            return commodityForBuyerDao.getCommodityByBuyerIdAndCommId(user.getId(), commodityId);
        }

        // 1-商家 2-未登录用户
        else {
            return commodityDao.getCommodityById(commodityId);
        }
    }

    /**
     * 上传图片文件
     */
    public Map<String, String> saveFile(MultipartFile file) {
        Map<String, String> message = new HashMap<String, String>();

        if (file != null && !file.isEmpty()) {

            if (file.getSize() >= PIC_MAX_SIZE) {
                message.put("error", "上传失败，文件太大");
                return message;
            }

            // 获取文件类型
            String contentType = file.getContentType();
            String fileType = contentType.split("/")[1];
            //检查文件格式
            if (!StringAndFileUtils.isLegalPicFileType(fileType)) {
                message.put("error", "不是图片格式文件");
                return message;
            }

            // 服务器文件系统图片存储目录(绝对路径)
            String picLocalStorageDir =  System.getProperty("user.home")+
                    STATIC_STORAGE_MAPPER_PATH.replaceAll("/",File.separator);

            String picName = CodeGeneUtils.picNameUUIDGen() + "." + fileType;
            String path = picLocalStorageDir + picName;


            logger.info("get a File:" + fileType + "|" + picName + "|" + path);

            try {
                // 存入到映射静态路径
                file.transferTo(new File(path));
                logger.info("save success:"+path);
            } catch (Exception e) {
                message.put("error", e.getMessage());
                return message;
            }
            // 设置静态资源地址
            message.put("path", STATIC_STORAGE_MAPPER_PATH + picName);
            return message;
        }
        message.put("error", "上传失败，文件可能不存在或者为空");
        return message;
    }
}
