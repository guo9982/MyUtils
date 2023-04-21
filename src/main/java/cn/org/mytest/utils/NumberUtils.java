package cn.org.mytest.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {
    /**
     * 格式化小数
     * @param num 字符串?
     * @param scale 四舍五入的位数
     * @return 格式化小数
     */
    public static double formatDouble(double num, int scale) {
        BigDecimal bd = new BigDecimal(num);
        return bd.setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }
}
