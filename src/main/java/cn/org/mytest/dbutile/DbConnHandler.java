package cn.org.mytest.dbutile;

import cn.org.mytest.conf.ConfigurationManager;
import cn.org.mytest.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

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
    private LinkedList<Connection> dataSource = new LinkedList<>();
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
     * 执行增删改SQL语句，返回影响的行数
     * @param sql
     * @param params
     * @return 影响的行数
     */
    public int executeUpdate(String sql, Object[] params) {
        int rtn = 0;
        Connection conn = null;
        PreparedStatement pstmt ;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(sql);

            if(params != null && params.length > 0) {
                for(int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }

            rtn = pstmt.executeUpdate();

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                dataSource.push(conn);
            }

        }

        return rtn;
    }

}
