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


}
