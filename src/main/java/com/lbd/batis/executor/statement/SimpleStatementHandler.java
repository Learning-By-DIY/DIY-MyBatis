package com.lbd.batis.executor.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lbd.batis.mapping.MappedStatement;
import com.lbd.batis.utils.CommonUtils;


public class SimpleStatementHandler implements StatementHandler {
    private MappedStatement mappedStatement;

    private static Pattern paramPattern = Pattern.compile("#\\{([^\\{\\}]*)\\}");

    public SimpleStatementHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    @Override
    public PreparedStatement prepare(Connection connection) throws SQLException {
        String originSql = mappedStatement.getSql();

        if (CommonUtils.isNotEmpty(originSql)) {
            return connection.prepareStatement(parseSymbol(originSql));
        } else {
            throw new SQLException("original sql is numm");
        }
    }

    private String parseSymbol(String source) {
        source = source.trim();
        Matcher matcher = paramPattern.matcher(source);
        return matcher.replaceAll("?");
    }

    @Override
    public ResultSet query(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeQuery();
    }
}
