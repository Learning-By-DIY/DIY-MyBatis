package com.lbd.batis;

import java.sql.SQLException;

import com.lbd.batis.executor.Executor;
import com.lbd.batis.executor.SimpleExecutor;
import com.lbd.batis.mapping.MappedStatement;

import org.junit.jupiter.api.Test;

public class ExecutorTest extends BaseTest {

  @Test
  public void testQuery()
    throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    MappedStatement ms = new MappedStatement();
    ms.setSql("select id, name, age, password from user where id = #{id}");

    String[] params = {"1"};

    Executor executor = new SimpleExecutor();
    executor.doQuery(ms, params);
  }
}
