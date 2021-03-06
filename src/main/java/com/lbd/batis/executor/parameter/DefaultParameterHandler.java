package com.lbd.batis.executor.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.List;

import com.lbd.batis.mapping.MappedStatement;


public class DefaultParameterHandler implements ParameterHandler {
    private Map<String, Object> parameter;
    private MappedStatement mappedStatement;

    public DefaultParameterHandler(MappedStatement mappedStatement,
                                   Map<String, Object> parameter) {
        this.parameter = parameter;
        this.mappedStatement = mappedStatement;
    }

    @Override
    public void setParameters(PreparedStatement paramPreparedStatement) {
        if (parameter == null) {
            return;
        }

        Map<String, List<Integer>> params = mappedStatement.getParams();
        for (String name : params.keySet()) {
            List<Integer> indexes = params.get(name);

            for (Integer i : indexes) {
                try {
                    paramPreparedStatement.setObject(i, parameter.get(name));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
