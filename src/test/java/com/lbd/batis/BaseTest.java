package com.lbd.batis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.lbd.batis.utils.JdbcUtil;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;


@TestInstance(Lifecycle.PER_CLASS)
public class BaseTest {
    protected static Connection conn = JdbcUtil.getConnectionWithoutException();

    @BeforeEach
    public void init() throws SQLException {
        String createSql = "CREATE TABLE user (" + "id INT NOT NULL, " + "name VARCHAR (20) NOT NULL, "
                + "age INT NOT NULL, " + "password VARCHAR(64) NOT NULL, " + "PRIMARY KEY (ID))";

        Statement statement = conn.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS user");
        statement.executeUpdate(createSql);

        String insertSql = "INSERT INTO user (id, name, age, password) VALUES (1, 'hanzejl', 20, '12345678')";
        PreparedStatement psmt = conn.prepareStatement(insertSql);
        psmt.execute();
    }

    @AfterAll
    public static void destroy() {
        JdbcUtil.release(conn);
    }
}
