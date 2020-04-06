package com.lbd.batis.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



public class JdbcUtilTest {
    @Test
    public void testJdbcUtil() throws SQLException {
        Connection conn = JdbcUtil.getConnection();

        Statement statement = conn.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS user");
        statement.executeUpdate("CREATE TABLE user (id INT NOT NULL, name VARCHAR (20) NOT NULL, age INT NOT NULL, password VARCHAR(64) NOT NULL, PRIMARY KEY (ID))");

        String insertSql =
            "INSERT INTO user (id, name, age, password) VALUES (1, 'hanzejl', 20, '12345678')";

        PreparedStatement psmt = conn.prepareStatement(insertSql);
        psmt.execute();

        String querySql = "SELECT * FROM user";
        psmt = conn.prepareStatement(querySql);

        ResultSet resultSet = psmt.executeQuery();

        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        Assertions.assertEquals(4, cols_len);

        Assertions.assertEquals(1, (int) resultSet.getObject("id"));
        Assertions.assertEquals("hanzejl", (String) resultSet.getObject("name"));
        Assertions.assertEquals(20, (int) resultSet.getObject("age"));
        Assertions.assertEquals("12345678", (String) resultSet.getObject("password"));
    }
}
