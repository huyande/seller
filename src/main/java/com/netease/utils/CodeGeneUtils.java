package com.netease.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-19.
 *
 * code 生成工具类
 * 用于商品和订单编号以及图片名称
 */
public class CodeGeneUtils {
    // 商品和订单编号生成器
    // 格式：comm/order-id-data
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 商品和订单编码生成器
     *
     * @param isOrder 是订单
     * @param id 创建者ID
     */
    public static String comAndOrdCodeGen(boolean isOrder, int id) {
        StringBuilder stringBuilder = new StringBuilder();
        if (isOrder)
            stringBuilder.append("order-");
        else
            stringBuilder.append("comm-");

        stringBuilder.append(String.valueOf(id));
        stringBuilder.append("-");

        Date now = new Date();
        stringBuilder.append(dateFormat.format(now));

        return stringBuilder.toString();
    }

    /**
     * 本地图片名称生成器
     * 格式：data-uuid
     */

    public static String picNameUUIDGen() {
        StringBuilder stringBuilder = new StringBuilder();
        Date now = new Date();
        stringBuilder.append(dateFormat.format(now));
        stringBuilder.append("-");
        UUID uuid = UUID.randomUUID();
        stringBuilder.append(uuid.toString().replaceAll("-", ""));

        return stringBuilder.toString();
    }
}
