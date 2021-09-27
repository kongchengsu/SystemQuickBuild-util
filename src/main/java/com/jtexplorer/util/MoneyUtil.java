package com.jtexplorer.util;

import java.math.BigDecimal;

/**
 * MoneyUtil class
 *
 * @author 苏友朋
 * @date 2019/03/23 9:36
 */
public class MoneyUtil {
    /**
     * 将分转换为元
     * @param money 金额（分为单位）转换为：金额（元为单位）
     */
    public static BigDecimal getMoneyToY(BigDecimal money) {
        return transformUnit(money,"0.01");
    }

    /**
     * 将元转换为分
     * @param money 金额（元为单位）转换为：金额（分为单位）
     */
    public static BigDecimal getMoneyToF(BigDecimal money) {
        return transformUnit(money,"100");
    }

    /**
     * 金额按照汇率进行兑换（乘法）
     * @param money 金额
     * @param rate  汇率
     */
    private static BigDecimal transformUnit(BigDecimal money, String rate){
        return money.multiply(new BigDecimal(rate));
    }
}
