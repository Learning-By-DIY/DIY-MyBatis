package com.lbd.batis.session.defalts;

import java.util.List;

import com.lbd.batis.session.SqlSession;


public class DefaultSqlSession implements SqlSession {

    @Override
    public <T> T selectOne(String statementId, Object parameter) {
        return null;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object parameter) {
        return null;
    }
}
