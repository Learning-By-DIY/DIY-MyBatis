package com.lbd.batis.session.defaults;

import java.sql.SQLException;
import java.util.List;

import com.lbd.batis.executor.Executor;
import com.lbd.batis.executor.SimpleExecutor;
import com.lbd.batis.mapping.MappedStatement;
import com.lbd.batis.session.Configuration;
import com.lbd.batis.session.SqlSession;
import com.lbd.batis.utils.CommonUtils;


public class DefaultSqlSession implements SqlSession {
    private final Configuration configuration;

    private final Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.executor = new SimpleExecutor();
    }

    @Override
    public <T> T selectOne(String statementId, Object parameter)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        List<T> results = selectList(statementId, parameter);
        return CommonUtils.isNotEmpty(results) ? results.get(0) : null;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object parameter)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        MappedStatement mappedStatement = this.configuration.getMappedStatement(statementId);
        return executor.doQuery(mappedStatement, null);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
