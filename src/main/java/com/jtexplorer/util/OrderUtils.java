package com.jtexplorer.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xuwang
 */
public class OrderUtils {


    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMddHHmmssSSS");
    static SimpleDateFormat simpleDateFormatYYYYMMDD = new SimpleDateFormat("YYYYMMddHHmmssSSS");

    /**
     * 生成订单编号
     * @return yyyyMMddHHmmssSS+四位随机数
     */
    public static String generateOrderNumber(){
        return simpleDateFormat.format(new Date())+ StringUtil.getRandNumStr(4);
    }
    /**
     * 生成订单编号
     * @return 头字符+yyyyMMddHHmmssSS+四位随机数+尾字符串
     */
    public static String generateOrderNumber(String headStr, String endStr){
        return headStr+simpleDateFormat.format(new Date())+ StringUtil.getRandNumStr(4)+endStr;
    }



}
