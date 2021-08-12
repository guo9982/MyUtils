package cn.org.mytest.string;

import java.util.Random;

/**
 * 字符串工具类
 */
public  class StringUtil {


    /**
     * 检查字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty10(String str) {
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

    /**
     * 自动补全指定长度的字符串。
     * @param str 需要处理的字符串。
     * @param len 输出的字符串长度。
     * @return
     */
    public static String getStrWithLength(String str, int len) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        while (sb.length() < len) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    /**
     * 从拼接的字符串中给字段设置值
     * @param str 字符串
     * @param delimiter 分隔符
     * @param field 字段名
     * @param newFieldValue 新的field值
     * @return 字段值
     *  name=zhangsan|age=12
     */
    public static String setFieldInConcatString(String str,
                                                String delimiter,
                                                String field,
                                                String newFieldValue) {

        // searchKeywords=iphone7|clickCategoryIds=1,2,3

        String[] fields = str.split(delimiter);

        for(int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].split("=")[0];
            if(fieldName.equals(field)) {
                String concatField = fieldName + "=" + newFieldValue;
                fields[i] = concatField;
                break;
            }
        }

        StringBuilder buffer = new StringBuilder();
        for(int i = 0; i < fields.length; i++) {
            buffer.append(fields[i]);
            if(i < fields.length - 1) {
                buffer.append("|");
            }
        }

        return buffer.toString();
    }

    /**
     * 获取指定长度的数字，大写字母混合字符串。
     * @param len 字符串长度
     * @return String
     */
    public static String getStrWithLength(int len) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        String charStr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int charLength = charStr.length();

        for (int i = 0; i < len; i++) {
            sb.append(charStr.charAt(random.nextInt(charLength)));
        }

        return sb.toString();
    }
}
