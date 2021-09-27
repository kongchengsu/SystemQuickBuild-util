package com.jtexplorer.util;


import java.util.List;

/**
 * @author 苏友朋
 * @date 2016/7/10
 */
public class ListUtil {
    /**
     * 判断字符串是否是null或空字符串：是，返回true
     *
     * @param str 要判断的字符串
     * @return boolean  是，返回true    否，返回false
     */
    public static boolean isEmpty(List list) {
        return !isNotEmpty(list);
    }

    /**
     * 判断字符串是否是null或空字符串：否，返回true
     *
     * @param str 要判断的字符串
     * @return boolean  否，返回true    是，返回false
     */
    public static boolean isNotEmpty(List list) {
        return list != null && list.size() > 0;
    }
}
