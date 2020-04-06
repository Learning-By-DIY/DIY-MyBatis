package com.lbd.batis.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


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
            InputStream inStream = JdbcUtil.class.getResourceAsStream("/jdbc.properties");
            Properties prop = new Properties();
            prop.load(inStream);

            driver = prop.getProperty("jdbc.driver");
            url = prop.getProperty("jdbc.url");

            if (prop.contains("jdbc.username")) {
                username = prop.getProperty("jdbc.username");
                password = prop.getProperty("jdbc.password");
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
}
