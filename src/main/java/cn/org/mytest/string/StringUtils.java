package cn.org.mytest.string;

/**
 * 字符串工具类
 */
public class StringUtils {



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
}
