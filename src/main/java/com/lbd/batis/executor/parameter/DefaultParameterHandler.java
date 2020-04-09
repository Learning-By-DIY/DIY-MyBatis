package com.lbd.batis.executor.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DefaultParameterHandler implements ParameterHandler {

    private Object parameter;

    public DefaultParameterHandler(Object parameter) {
        this.parameter = parameter;
    }

    @Override
    public void setParameters(PreparedStatement paramPreparedStatement) {
        if (parameter == null) {
            return;
        }

        if (parameter.getClass().isArray()) {
            Object[] params = (Object[]) parameter;
            for (int i = 0; i < params.length; ++i) {
                try {
                    paramPreparedStatement.setObject(i + 1, params[i]);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
