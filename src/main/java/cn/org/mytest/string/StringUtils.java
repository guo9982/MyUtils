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
     * 从拼接的字符串中提取字段
     * @param str 字符串
     * @param delimiter 分隔符
     * @param field 字段
     * @return 字段值
     * name=zhangsan|age=18
     */
    public static String getFieldFromConcatString(String str,String delimiter, String field) {
        try {
            String[] fields = str.split(delimiter);
            for(String concatField : fields) {
                // searchKeywords=|clickCategoryIds=1,2,3
                if(concatField.split("=").length == 2) {
                    String fieldName = concatField.split("=")[0];
                    String fieldValue = concatField.split("=")[1];
                    if(fieldName.equals(field)) {
                        return fieldValue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
