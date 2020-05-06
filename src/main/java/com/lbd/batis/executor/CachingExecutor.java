package com.lbd.batis.executor;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.lbd.batis.mapping.MappedStatement;


public class CachingExecutor implements Executor {

    private Executor delegate;

    @Override
    public <E> List<E> doQuery(MappedStatement ms, Map<String, Object> parameter)
            throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return null;
    }

    @Override
    public int doUpdate(MappedStatement ms, Map<String, Object> parameter) throws SQLException {
        return 0;
    }
}
