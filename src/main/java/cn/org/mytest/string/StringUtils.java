package cn.org.mytest.string;

public class StringUtils {
    public static boolean isEmpty(String str) {
        return str == null || "".equalsIgnoreCase(str);
    }
}
