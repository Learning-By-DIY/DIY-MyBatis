package com.lbd.batis;

import java.sql.SQLException;
import java.util.List;

import com.lbd.batis.constants.SqlType;
import com.lbd.batis.bean.User;
import com.lbd.batis.mapper.UserMapper;
import com.lbd.batis.mapping.MappedStatement;
import com.lbd.batis.session.Configuration;
import com.lbd.batis.session.SqlSession;
import com.lbd.batis.session.SqlSessionFactory;
import com.lbd.batis.session.defaults.DefaultSqlSessionFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
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
    public void testSelect() throws ClassNotFoundException {
        Configuration configuration = new Configuration();

        MappedStatement statement = new MappedStatement();

        String namespace = "com.lbd.batis.mapper.UserMapper";
        String sqlId = namespace + "." + "getUser";

        statement.setResultType("com.lbd.batis.bean.User");
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

        Assertions.assertEquals(user.getId(), 1);
        Assertions.assertEquals(user.getName(), "hanzejl");
        Assertions.assertEquals(user.getPassword(), "12345678");
        Assertions.assertEquals(user.getAge(), 20);

        // test selectAll
        MappedStatement statement2 = new MappedStatement();
        sqlId = namespace + "." + "getAll";

        statement2.setResultType("com.lbd.batis.bean.User");
        statement2.setSqlCommandType(SqlType.SELECT);
        statement2.setSqlId(sqlId);
        statement2.setNamespace(namespace);
        statement2.setSql("select * from user");

        configuration.addMappedStatement(sqlId, statement2);

        List<User> users = userMapper.getAll();
        Assertions.assertEquals(1, users.size());
        Assertions.assertEquals(user, users.get(0));

        // test delete
        /*
        MappedStatement statementDelete = new MappedStatement();
        sqlId = namespace + "." + "delete";

        statementDelete.setSqlCommandType(SqlType.DELETE);
        statementDelete.setSqlId(sqlId);
        statementDelete.setNamespace(namespace);
        statementDelete.setSql("delete from user where id = #{id}");
        configuration.addMappedStatement(sqlId, statementDelete);

        boolean deleteStatus = userMapper.delete("1");
        Assertions.assertEquals(true, deleteStatus);
        */
    }
}
