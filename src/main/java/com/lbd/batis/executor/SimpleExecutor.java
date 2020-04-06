package com.lbd.batis.executor;


import java.util.List;

import com.lbd.batis.executor.Executor;
import com.lbd.batis.mapping.MappedStatement;


public class SimpleExecutor implements Executor {

    @Override
    public <E> List<E> doQuery(MappedStatement ms, Object parameter) {
        return null;
    }
}
