package com.lbd.batis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.lbd.batis.utils.JdbcUtil;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;


public class BaseTest {
    protected static Connection conn = JdbcUtil.getConnectionWithoutException();

    public void init() {
        try {
            String createSql = "CREATE TABLE user (" + "id INT NOT NULL, " + "name VARCHAR (20) NOT NULL, "
                    + "age INT NOT NULL, " + "password VARCHAR(64) NOT NULL, " + "PRIMARY KEY (ID))";

            Statement statement = conn.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS user");
            statement.executeUpdate(createSql);

            String insertSql = "INSERT INTO user (id, name, age, password) VALUES (1, 'hanzejl', 20, '12345678')";
            PreparedStatement psmt = conn.prepareStatement(insertSql);
            psmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        try {
            Statement statement = conn.createStatement();
            boolean result = statement.execute("DROP TABLE IF EXISTS user");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void destroy() {
        System.out.println("call destory");
        JdbcUtil.release(conn);
    }
}
