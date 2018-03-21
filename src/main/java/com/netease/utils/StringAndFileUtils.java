package com.netease.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-19.
 */
public class StringAndFileUtils {

    private static final int TITLE_LOWER = 2;
    private static final int TITLE_UPPER = 80;

    private static final int ABSTRACT_LOWER = 2;
    private static final int ABSTRACT_UPPER = 140;

    private static final int DETAIL_LOWER = 2;
    private static final int DETAIL_UPPER = 1000;

    private static final int PIC_SIZE = 1024 * 1024;

    private static final String[] PIC_FORMAT = {"jpeg", "png", "gif"};

    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {//存在一个可见字符即判定为非空白
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    // 不能小于下边界，也不能大于上边界
    private static boolean checkStringInRangeOf(int lower, int upper, String str) {

        // check params
        if (lower < 0 || upper <= lower) {
            throw new IllegalArgumentException("Illegal: low < 0 or upper <= lower");
        }

        // check blank
        if (isBlank(str)) {
            return false;
        }
        int length = str.length();

        return (length >= lower && length <= upper);
    }


    //检查title
    public static boolean checkTitle(String string) {
        return checkStringInRangeOf(TITLE_LOWER, TITLE_UPPER, string);
    }

    //检查摘要
    public static boolean checkAbstract(String string) {
        return checkStringInRangeOf(ABSTRACT_LOWER, ABSTRACT_UPPER, string);
    }

    //检查正文
    public static boolean checkDetail(String string) {
        return checkStringInRangeOf(DETAIL_LOWER, DETAIL_UPPER, string);
    }

    //检查全部
    public static boolean checkCommodityAllContent(String title, String comAbstract, String detail) {
        return (checkTitle(title) && checkAbstract(comAbstract) && checkDetail(detail));
    }

    //检查float
    public static boolean isDouble(String floatStr) {
        boolean isLegal = true;
        try {
            Double.valueOf(floatStr);
        } catch (NumberFormatException e) {
            isLegal = false;
        }
        return isLegal;
    }

    //检查图片文件格式
    public static boolean isLegalPicFileType(String fileType) {
        for (int i = 0; i < PIC_FORMAT.length; i++) {
            if (fileType.equals(PIC_FORMAT[i])) {
                return true;
            }
        }
        return false;
    }

    // 浮点数据截断
    public static double dataRruncation(double data) {
        BigDecimal b = new BigDecimal(data);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    // 浮点数据类型显示
    public static String dataShow(double data) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(data);
    }
}