package com.lbd.batis.session;

import java.util.List;


public interface SqlSession  {
    <T> T selectOne(String statementId, Object parameter);

    <E> List<E> selectList(String statementId, Object parameter);
}
