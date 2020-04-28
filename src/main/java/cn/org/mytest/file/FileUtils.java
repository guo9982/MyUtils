package cn.org.mytest.file;

import java.io.File;

public class FileUtils {
    public static String copyFile(String src,String dsrc) {
        String result = "";
        File srcFile = new File(src);
        if (srcFile.exists()) {
            if (srcFile.isFile()) {
                result = "";
            }
        } else {
            result = "源文件不存在！";
        }

        return result;
    }
}
