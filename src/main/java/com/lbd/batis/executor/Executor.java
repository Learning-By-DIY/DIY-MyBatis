package com.lbd.batis.executor;

import java.sql.SQLException;
import java.util.Map;
import java.util.List;

import com.lbd.batis.mapping.MappedStatement;


public interface Executor {
    <E> List<E> doQuery(MappedStatement ms, Map<String, Object> parameter)
            throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException;
}
