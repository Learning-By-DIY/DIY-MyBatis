package com.lbd.batis.mapping;

import java.util.HashMap;
import java.util.List;

import com.lbd.batis.constants.SqlType;

import lombok.Data;


@Data
public class MappedStatement {
    private String namespace;

    private String sqlId;

    private String sql;

    private String resultType;

    private SqlType sqlCommandType;

    private HashMap<String, List<Integer>> params;

    public void setSql(String sql) {
        this.sql = sql;
    }
}
