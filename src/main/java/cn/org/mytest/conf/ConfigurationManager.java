package cn.org.mytest.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationManager.class);

    private static final Properties properties = new Properties();

    /**
     * 初始化的时候，加载一次配置
     * 采用静态模式进行加载
     */
    static {
        logger.info("开始加重配置文件。。。");
        InputStream in = ConfigurationManager.class.getClassLoader().getResourceAsStream("conf.properties");
        try {
            properties.load(in);
            logger.info("配置文件加重完成！");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }
    }

    /**
     * 获取属性。
     * @param key 属性字段
     * @param type 返回类型
     * @return 属性值
     */
    public static Object getStringProperty(String key, String type) {
        String res = properties.getProperty(key, "");
        if (type.equalsIgnoreCase("int")) {
            return Integer.valueOf(res);
        } else if (type.equalsIgnoreCase("double")) {
            return Double.valueOf(res);
        } else if (type.equalsIgnoreCase("long")) {
            return Long.valueOf(res);
        } else {
            return res;
        }
    }

}
