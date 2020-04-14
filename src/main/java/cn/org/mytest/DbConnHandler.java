package cn.org.mytest;

import cn.org.mytest.conf.ConfigurationManager;
import cn.org.mytest.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class DbConnHandler {
    private static final Logger logger = LoggerFactory.getLogger(DbConnHandler.class);

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
        private static final DbConnHandler INSTANCE = new DbConnHandler();
    }
    public static DbConnHandler getInstance() {
        return DbSub.INSTANCE;
    }

    // 数据库连接池
    private LinkedList<Connection> dataSource = new LinkedList<Connection>();
    private DbConnHandler(){
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

}
