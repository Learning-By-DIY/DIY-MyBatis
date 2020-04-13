package com.lbd.batis.executor.parameter;

import java.sql.PreparedStatement;


public interface ParameterHandler {
    void setParameters(PreparedStatement paramPreparedStatement);
}
