package cn.org.mytest.dbUtile;

import cn.org.mytest.conf.ConfigurationManager;
import cn.org.mytest.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class DbUtils {
    private static final Logger logger = LoggerFactory.getLogger(DbUtils.class);

    /*
     第一步：在静态代码块中，直接加载数据库的驱动
     加载驱动，不是直接简单的，使用com.mysql.jdbc.Driver就可以了
     之所以说，不要硬编码，他的原因就在于这里

     com.mysql.jdbc.Driver只代表了MySQL数据库的驱动
     那么，如果有一天，我们的项目底层的数据库要进行迁移，比如迁移到Oracle
     或者是DB2、SQLServer
     那么，就必须很费劲的在代码中，找，找到硬编码了com.mysql.jdbc.Driver的地方，然后改成其他数据库的驱动类的类名
     所以正规项目，是不允许硬编码的，那样维护成本很高

     通常，我们都是用一个常量接口中的某个常量，来代表一个值
     然后在这个值改变的时候，只要改变常量接口中的常量对应的值就可以了

     项目，要尽量做成可配置的
     就是说，我们的这个数据库驱动，更进一步，也不只是放在常量接口中就可以了
     最好的方式，是放在外部的配置文件中，跟代码彻底分离
     常量接口中，只是包含了这个值对应的key的名字
     */
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
    private final LinkedList<Connection> dataSource = new LinkedList<>();
    private DbUtils(){
        int dataSourceSize = (Integer) ConfigurationManager.getStringProperty(Constants.JDBC_DATASOURCE_SIZE, "int");
        logger.info("创建数据库连接池。");
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



    /**
     * 执行查询SQL语句
     * @param sql
     * @param params
     * @param callback
     */
    public void executeQuery(String sql, Object[] params,
                             QueryCallback callback) {
        Connection conn = null;
        PreparedStatement pstmt ;
        ResultSet rs ;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            if(params != null && params.length > 0) {
                for(int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }

            rs = pstmt.executeQuery();

            callback.process(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                dataSource.push(conn);
            }
        }
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
