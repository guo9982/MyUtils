package cn.org.mytest.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    public static String copyFile17(String srcPathStr,String desPathStr) throws IOException {
        String result;
        File srcFile = new File(srcPathStr);
        if (!srcFile.exists()) {
            logger.error("srcFile not exists");
            result = "ERROR";
        } else {
            FileChannel srcChannel = FileChannel.open(Paths.get(srcPathStr), StandardOpenOption.READ);
            FileChannel desChannel = FileChannel.open(Paths.get(desPathStr), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            srcChannel.transferTo(0, srcChannel.size(), desChannel);
            srcChannel.close();
            desChannel.close();
            result = "SUCCESS";
        }

        return result;
    }
}
