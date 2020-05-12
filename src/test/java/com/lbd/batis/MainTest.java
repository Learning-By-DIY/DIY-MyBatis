package com.lbd.batis;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lbd.batis.bean.User;
import com.lbd.batis.mapper.UserMapper;
import com.lbd.batis.mapper.UserMapper2;
import com.lbd.batis.session.SqlSession;
import com.lbd.batis.session.SqlSessionFactory;
import com.lbd.batis.session.SqlSessionFactoryBuilder;

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
    public void testCacheExecutor () throws ClassNotFoundException {
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

    @Test
    public void testSelect2() throws ClassNotFoundException {
        this.init();

        Map<String, String> config = new HashMap<>();
        config.put("cache.enable", "true");

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(config);
        SqlSession session = factory.openSession();

        UserMapper2 userMapper = session.getMapper(UserMapper2.class);
        User user = userMapper.getUser("1");
        Assertions.assertEquals(user.getId(), 1);

        User user2 = userMapper.getUser("1");
        Assertions.assertEquals(user, user2);

        this.clear();
    }

    @Test
    public void testUpdate2() throws ClassNotFoundException, SQLException {
        this.init();

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build();
        SqlSession session = factory.openSession();

        UserMapper2 userMapper = session.getMapper(UserMapper2.class);

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
