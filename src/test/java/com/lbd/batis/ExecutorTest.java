package com.lbd.batis;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.lbd.batis.dao.User;
import com.lbd.batis.executor.Executor;
import com.lbd.batis.executor.SimpleExecutor;
import com.lbd.batis.mapping.MappedStatement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ExecutorTest extends BaseTest {
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
    public void testQuery()
        throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        MappedStatement ms = new MappedStatement();
        ms.setSql("select id, name, age, password from user where id = #{id}");
        ms.setResultType("com.lbd.batis.dao.User");

        HashMap<String, Object> params = new HashMap<>();
        params.put("id", "1");

        Executor executor = new SimpleExecutor();
        List<User> users = executor.doQuery(ms, params);

        Assertions.assertEquals(1, users.size());

        User user = users.get(0);
        Assertions.assertEquals(1, user.getId());
        Assertions.assertEquals("hanzejl", user.getName());
        Assertions.assertEquals(20, user.getAge());
        Assertions.assertEquals("12345678", user.getPassword());
    }
}
