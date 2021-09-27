package com.jtexplorer.util;


import java.math.BigDecimal;

/**
 * CalculateUtil class
 *
 * @author 苏友朋
 * @date 2019/03/06 15:53
 */
public class CalculateUtil {
    /**
     * 返回大的数
     * @param one 第一个数
     * @param two 第二个数
     * @return  Long
     */
    public static Long getMax(Long one, Long two) {
        if(one > two){
            return one;
        }else {
            return two;
        }
    }

    /**
     * 返回小的数
     * @param one 第一个数
     * @param two 第二个数
     * @return  Long
     */
    public static Long getMin(Long one, Long two) {
        if(one > two){
            return two;
        }else {
            return one;
        }
    }

    /**
     * 返回大的数
     * @param one 第一个数
     * @param two 第二个数
     * @return  Long
     */
    public static BigDecimal getMax(BigDecimal one, BigDecimal two) {
        if(one.compareTo(two) == 1){
            return one;
        }else {
            return two;
        }
    }

    /**
     * 返回小的数
     * @param one 第一个数
     * @param two 第二个数
     * @return  Long
     */
    public static BigDecimal getMin(BigDecimal one, BigDecimal two) {
        if(one.compareTo(two) == 1){
            return two;
        }else {
            return one;
        }
    }

    /**
     * 与0比较，大于0返回true
     * @param one 第一个数
     * @return  Long
     */
    public static boolean getZero(BigDecimal one) {
        if(one.compareTo(new BigDecimal("0")) == 1){
            return true;
        }else {
            return false;
        }
    }

}
