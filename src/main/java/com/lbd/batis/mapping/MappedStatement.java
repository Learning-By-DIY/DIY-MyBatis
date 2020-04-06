package com.lbd.batis.mapping;

import com.lbd.batis.constants.SqlType;

import lombok.Data;


@Data
public class MappedStatement {
    private String namespace;

    private String sqlId;

    private String sql;

    private String resultType;

    private SqlType sqlCommandType;
}
