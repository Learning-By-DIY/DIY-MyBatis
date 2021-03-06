package com.lbd.batis.executor.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lbd.batis.mapping.MappedStatement;
import com.lbd.batis.utils.CommonUtils;


public class SimpleStatementHandler implements StatementHandler {
    private MappedStatement mappedStatement;

    public SimpleStatementHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    @Override
    public PreparedStatement prepare(Connection connection) throws SQLException {
        String sql = mappedStatement.getSql();

        if (CommonUtils.isNotEmpty(sql)) {
            return connection.prepareStatement(sql);
        } else {
            throw new SQLException("original sql is: " + sql);
        }
    }

    @Override
    public ResultSet query(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeQuery();
    }

    @Override
    public int update(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeUpdate();
    }
}
