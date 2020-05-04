package com.lbd.batis;

import java.sql.SQLException;
import java.util.List;

import com.lbd.batis.bean.User;
import com.lbd.batis.mapper.UserMapper;
import com.lbd.batis.session.Configuration;
import com.lbd.batis.session.SqlSession;
import com.lbd.batis.session.SqlSessionFactory;
import com.lbd.batis.session.SqlSessionFactoryBuilder;
import com.lbd.batis.session.defaults.DefaultSqlSessionFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class MainTest extends BaseTest {
    @Test
    public void testSelect() throws ClassNotFoundException {
        this.init();

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build();
        SqlSession session = factory.openSession();

        UserMapper userMapper = session.getMapper(UserMapper.class);
        User user = userMapper.getUser("1");

        Assertions.assertEquals(user.getId(), 1);
        Assertions.assertEquals(user.getName(), "hanzejl");
        Assertions.assertEquals(user.getPassword(), "12345678");
        Assertions.assertEquals(user.getAge(), 20);

        // test selectAll
        List<User> users = userMapper.getAll();
        Assertions.assertEquals(1, users.size());
        Assertions.assertEquals(user, users.get(0));

        this.clear();
    }

    @Test
    public void testUpdate() throws ClassNotFoundException, SQLException {
        this.init();

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build();
        SqlSession session = factory.openSession();

        UserMapper userMapper = session.getMapper(UserMapper.class);

        int deleteCount = userMapper.delete("1");
        Assertions.assertEquals(1, deleteCount);

        int insertCount = userMapper.insert("1", "zhangrong", "world", 10);
        Assertions.assertEquals(1, insertCount);

        int updateCount = userMapper.update("1", "hanzejl");
        Assertions.assertEquals(1, updateCount);

        User user = userMapper.getUser("1");

        Assertions.assertEquals(user.getId(), 1);
        Assertions.assertEquals(user.getName(), "hanzejl");
        Assertions.assertEquals(user.getPassword(), "world");
        Assertions.assertEquals(user.getAge(), 10);

        this.clear();
    }
}
