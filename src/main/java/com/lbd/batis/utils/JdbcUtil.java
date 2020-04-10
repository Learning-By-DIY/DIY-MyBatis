package com.lbd.batis.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.lbd.batis.constants.Constant;


public class JdbcUtil {

    private static String username;

    private static String password;

    private static String driver;

    private static String url;

    static {
        loadConfig();
    }

    public static void loadConfig() {
        try {
            InputStream inStream = JdbcUtil.class.getResourceAsStream(Constant.CONFIG_FILE_LOCATION);
            Properties prop = new Properties();
            prop.load(inStream);

            driver = prop.getProperty(Constant.JDBC_DRIVER);
            url = prop.getProperty(Constant.JDBC_URL);

            if (prop.contains(Constant.JDBC_USERNAME)) {
                username = prop.getProperty(Constant.JDBC_USERNAME);
                password = prop.getProperty(Constant.JDBC_PASSWORD);
            }
        } catch (Exception e) {
            throw new RuntimeException("read jdbc.properties exception", e);
        }
    }

    private JdbcUtil() {}

    public static Connection getConnection() throws SQLException {
        Connection connection = null;

        try {
            Class.forName(driver);
            if (username == null) {
                connection = DriverManager.getConnection(url);
            } else {
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void release(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnectionWithoutException() {
        try {
            return getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
