package cn.org.mytest.string;

/**
 * 字符串工具类
 */
public class StringUtils {
    /**
     * 检查字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equalsIgnoreCase(str);
    }

    /**
     * 判断字符串是否不为空。
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !"".equals(str);
    }

    /**
     * 截断字符串两侧的逗号
     * @param str 字符串
     * @return 字符串
     */
    public static String trimComma(String str) {
        if(str.startsWith(",")) {
            str = str.substring(1);
        }
        if(str.endsWith(",")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * 补全两位数字
     * @param str
     * @return
     */
    public static String fulfuill(String str) {
        if(str.length() == 1)
            return "0" + str;
        return str;
    }
}
