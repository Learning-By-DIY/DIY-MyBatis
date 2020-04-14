package com.lbd.batis.session;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public interface SqlSession  {
    <T> T selectOne(String statementId, Map<String, Object> parameter)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException;

    <E> List<E> selectList(String statementId, Map<String, Object> parameter)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException;

    boolean insert(String statementId, Map<String, Object> parameter) throws SQLException;

    boolean delete(String statementId, Map<String, Object> parameter) throws SQLException;

    int update(String statementId, Map<String, Object> parameter) throws SQLException;

    <T> T getMapper(Class<T> paramClass);

    Configuration getConfiguration();
}
