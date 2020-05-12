package com.lbd.batis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lbd.batis.cache.CacheManage;
import com.lbd.batis.constants.SqlType;
import com.lbd.batis.mapping.MappedStatement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ExtractTableTest {

    @Test
    public void testExtractTableName() {
        MappedStatement statement = new MappedStatement();
        Map<String, Object> params = new HashMap<>();

        params.put("id", 1);

        statement.setSql("select * from user where id = #{id}");
        statement.setSqlCommandType(SqlType.SELECT);

        List<String> tables = CacheManage.extractTableName(statement, params);
        String table = tables.get(0);
        Assertions.assertEquals(table, "user");

        statement.setSql("delete from user where id = #{id}");
        statement.setSqlCommandType(SqlType.DELETE);
        tables = CacheManage.extractTableName(statement, params);
        Assertions.assertEquals("user", tables.get(0));

        params.put("name", "hanzejl");
        statement.setSql("update user set name = #{name} where id = #{id}");
        statement.setSqlCommandType(SqlType.UPDATE);
        Assertions.assertEquals("user", tables.get(0));

        params.put("age", 20);
        params.put("password", "12345678");
        statement.setSql("insert into user (id, name, age, password) values (#{id}, #{name}, #{age}, #{password})");
        statement.setSqlCommandType(SqlType.INSERT);
        Assertions.assertEquals("user", tables.get(0));
    }
}
