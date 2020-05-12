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
    }
}
