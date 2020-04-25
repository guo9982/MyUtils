package cn.org.mytest.dbutile;

import cn.org.mytest.conf.ConfigurationManager;
import cn.org.mytest.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class DbUtils {
    private static final Logger logger = LoggerFactory.getLogger(DbUtils.class);

    static {
        logger.info("加重数据库驱动！");
        String driver = (String) ConfigurationManager.getStringProperty(Constants.JDBC_DRIVER, "");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用单例模式
     */
    private static class DbSub{
        private static final DbUtils INSTANCE = new DbUtils();
    }
    public static DbUtils getInstance() {
        return DbSub.INSTANCE;
    }

    // 数据库连接池
    private LinkedList<Connection> dataSource = new LinkedList<>();
    private DbUtils(){
        int dataSourceSize = (Integer) ConfigurationManager.getStringProperty(Constants.JDBC_DATASOURCE_SIZE, "int");
        for (int i = 0; i < dataSourceSize; i++) {

            String url = (String) ConfigurationManager.getStringProperty(Constants.JDBC_URL, "");
            String userName = (String) ConfigurationManager.getStringProperty(Constants.JDBC_USER, "");
            String userPassword = (String) ConfigurationManager.getStringProperty(Constants.JDBC_PASSWORD, "");

            try {
                Connection conn = DriverManager.getConnection(url, userName, userPassword);
                dataSource.push(conn);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    /**
     * 获取数据库连接
     * @return 数据库连接
     */
    public synchronized Connection getConnection() {
        while(dataSource.size() == 0) {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //检索并移除此列表的头元素(第一个元素)
        return dataSource.poll();
    }

    /**
     * 静态内部接口：查询回调接口
     *
     */
    public interface QueryCallback {

        /**
         * 处理查询结果
         * @param rs
         * @throws Exception
         */
        void process(ResultSet rs) throws Exception;

    }

}
