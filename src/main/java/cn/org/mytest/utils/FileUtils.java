package cn.org.mytest.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 拷贝文件
     *
     * @param srcPathStr 源文件地址。
     * @param desPathStr 目标文件地址。
     * @return
     * @throws IOException
     */
    public static String copyFile17(String srcPathStr, String desPathStr) throws IOException {
        String result;
        File srcFile = new File(srcPathStr);
        if (!srcFile.exists()) {
            logger.error("源文件不存在！");
            result = "ERROR";
        } else {
            FileChannel srcChannel = FileChannel.open(Paths.get(srcPathStr), StandardOpenOption.READ);
            FileChannel desChannel = FileChannel.open(Paths.get(desPathStr), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            srcChannel.transferTo(0, srcChannel.size(), desChannel);
            srcChannel.close();
            desChannel.close();
            result = "SUCCESS";
            logger.info("文件复制成功！");
        }

        return result;
    }

    /**
     * 获取文件的MD5值
     * @param strFilePath 文件地址。
     * @return
     */
    public static String getFileMD5code(String strFilePath) {
        // 输入合理性检查
        if (strFilePath == null || strFilePath.equalsIgnoreCase("")) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            FileInputStream stream = new FileInputStream(strFilePath);
            byte[] bytes = new byte[1024];
            int length = -1;
            while ((length = stream.read(bytes, 0, 1024)) != -1) {
                md.update(bytes, 0, length);
            }
            stream.close();
            byte[] digest = md.digest();
            BigInteger bigInteger = new BigInteger(1, digest);
            return bigInteger.toString(16);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        } finally {

        }
    }

}
