package com.lbd.batis.executor;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.List;

import com.lbd.batis.executor.Executor;
import com.lbd.batis.executor.parameter.DefaultParameterHandler;
import com.lbd.batis.executor.parameter.ParameterHandler;
import com.lbd.batis.executor.resultset.DefaultResultSetHandler;
import com.lbd.batis.executor.resultset.ResultSetHandler;
import com.lbd.batis.executor.statement.SimpleStatementHandler;
import com.lbd.batis.executor.statement.StatementHandler;
import com.lbd.batis.mapping.MappedStatement;
import com.lbd.batis.utils.JdbcUtil;


public class SimpleExecutor implements Executor {

    @Override
    public <E> List<E> doQuery(MappedStatement ms,
                               Map<String, Object> parameter)
        throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Connection conn = null;

        try {
            conn = JdbcUtil.getConnection();

            StatementHandler statementHandler = new SimpleStatementHandler(ms);

            PreparedStatement preparedStatement = statementHandler.prepare(conn);

            ParameterHandler parameterHandler = new DefaultParameterHandler(ms, parameter);
            parameterHandler.setParameters(preparedStatement);

            ResultSet resultSet = statementHandler.query(preparedStatement);

            ResultSetHandler resuSetHandler = new DefaultResultSetHandler(ms);

            return resuSetHandler.handleResultSets(resultSet);
        } finally {
            JdbcUtil.release(conn);
        }
    }
}
