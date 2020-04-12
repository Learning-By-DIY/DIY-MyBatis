package com.lbd.batis;

import java.sql.SQLException;

import com.lbd.batis.constants.SqlType;
import com.lbd.batis.dao.User;
import com.lbd.batis.dao.UserMapper;
import com.lbd.batis.mapping.MappedStatement;
import com.lbd.batis.session.Configuration;
import com.lbd.batis.session.SqlSession;
import com.lbd.batis.session.SqlSessionFactory;
import com.lbd.batis.session.defaults.DefaultSqlSessionFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class MainTest extends BaseTest {
    @BeforeEach
    public void init() throws SQLException {
        super.init();
        super.insertInit();
    }

    @AfterAll
    public void destroy() {
        super.destroy();
    }

    @Test
    public void testSelectOne() throws ClassNotFoundException {
        Configuration configuration = new Configuration();

        MappedStatement statement = new MappedStatement();

        String namespace = "com.lbd.batis.dao.UserMapper";
        String sqlId = namespace + "." + "getUser";

        statement.setResultType("com.lbd.batis.dao.User");
        statement.setSqlCommandType(SqlType.SELECT);
        statement.setSqlId(sqlId);
        statement.setNamespace(namespace);
        statement.setSql("select * from user where id = #{id}");

        configuration.addMappedStatement(sqlId, statement);
        configuration.addMapper(Class.forName(namespace));

        SqlSessionFactory factory = new DefaultSqlSessionFactory(configuration);
        SqlSession session = factory.openSession();

        UserMapper userMapper = session.getMapper(UserMapper.class);
        User user = userMapper.getUser("1");
        System.out.println(user);
    }

    @Test
    public void testSelectList() {
    }
}
