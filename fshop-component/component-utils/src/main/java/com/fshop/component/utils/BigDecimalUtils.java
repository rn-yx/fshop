/*
 * Copyright [2020] [rannuo]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fshop.component.utils;

import java.math.BigDecimal;

/**
 * Description：BigDecimal 运算工具
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
public class BigDecimalUtils {

    /**
     * 加法
     *
     * @param arg1 加数1
     * @param arg2 加数2
     * @return 和
     */
    public static BigDecimal add(BigDecimal arg1, BigDecimal arg2) {
        checkParam(arg1, arg2);
        return arg1.add(arg2);
    }

    /**
     * 减法
     *
     * @param arg1 被减数
     * @param arg2 减数
     * @return 差
     */
    public static BigDecimal sub(BigDecimal arg1, BigDecimal arg2) {
        checkParam(arg1, arg2);
        return arg1.subtract(arg2);
    }

    /**
     * 乘法
     *
     * @param multiplier   因数1
     * @param multiplicand 因数2
     * @return 积
     */
    public static BigDecimal multiply(BigDecimal multiplier, BigDecimal multiplicand) {
        checkParam(multiplier, multiplicand);
        return multiplier.multiply(multiplicand);
    }

    /**
     * 除法
     *
     * @param dividend     被除数
     * @param divisor      除数
     * @param scale        精确位数
     * @param roundingMode 舍入模式
     * @return 商
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, int scale, int roundingMode) {
        checkParam(dividend, divisor);
        if (divisor.doubleValue() == 0) {
            throw new IllegalArgumentException("运算参数有误：除数不能为0");
        }
        return dividend.divide(divisor, scale, roundingMode);
    }

    /**
     * 除法（精确到小数点后两位、舍入模式为6）
     *
     * @param divisor  除数
     * @param dividend 被除数
     * @return 商
     */
    public static BigDecimal divide(BigDecimal divisor, BigDecimal dividend) {
        return divide(divisor, dividend, 2, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * 四舍五入保留N位小数 先四舍五入再使用double值自动去零
     *
     * @param arg 原始数
     * @param scare 保留位数
     * @return
     */
    public static String roundToN(BigDecimal arg, int scare) {
        return String.valueOf(arg.setScale(scare, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    /**
     * 转化成百分数
     *
     * @param arg
     * @param scare
     * @return
     */
    public static String convert2percent(BigDecimal arg, int scare, int roundingMode) {
       return multiply(arg.setScale(scare, roundingMode), new BigDecimal("100")).doubleValue() + "%";
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            // 去掉多余的0
            s = s.replaceAll("0+?$", "");
            // 如最后一位是.则去掉
            s = s.replaceAll("[.]$", "");
        }
        return s;
    }

    /**
     * 检查参数
     *
     * @param arg1
     * @return
     */
    private static void checkParam(BigDecimal arg1, BigDecimal arg2) {
        if (null == arg1 || null == arg2) {
            throw new IllegalArgumentException("运算参数有误：参与运算的参数不能为null");
        }
    }

}