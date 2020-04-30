package cn.org.mytest.file;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class FileUtils {
    public static String copyFile(String srcPathStr,String desPathStr) throws IOException {
        String result = "error";
        File srcFile = new File(srcPathStr);
        if (!srcFile.exists()) {
            result = "源文件不存在";
        } else {
            FileChannel.open(Paths.get(srcPathStr), StandardOpenOption.READ);
        }

        return result;
    }
}
