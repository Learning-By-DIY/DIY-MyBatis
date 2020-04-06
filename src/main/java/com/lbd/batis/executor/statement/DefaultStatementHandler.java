package com.lbd.batis.executor.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DefaultStatementHandler implements StatementHandler {
    @Override
    public PreparedStatement prepare(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public ResultSet query(PreparedStatement preparedStatement) throws SQLException {
        return null;
    }
}
