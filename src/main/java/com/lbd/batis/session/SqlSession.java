package com.lbd.batis.session;

import java.sql.SQLException;
import java.util.List;


public interface SqlSession  {
    <T> T selectOne(String statementId, Object parameter)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException;

    <E> List<E> selectList(String statementId, Object parameter)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException;

    <T> T getMapper(Class<T> paramClass);

    Configuration getConfiguration();
}
