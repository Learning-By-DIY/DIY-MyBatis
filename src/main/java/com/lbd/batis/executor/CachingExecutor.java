package com.lbd.batis.executor;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.lbd.batis.cache.Cache;
import com.lbd.batis.cache.CacheKey;
import com.lbd.batis.cache.impl.PerpetualCache;
import com.lbd.batis.mapping.MappedStatement;


public class CachingExecutor implements Executor {

    private Executor delegate;

    private static Cache cache = new PerpetualCache();

    private CacheKey generateCacheKey(MappedStatement ms, Map<String, Object> parameter) {
        CacheKey cacheKey = new CacheKey();

        cacheKey.update(ms.getOriginSql());

        return cacheKey;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> doQuery(MappedStatement ms, Map<String, Object> parameter)
            throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        CacheKey cacheKey = generateCacheKey(ms, parameter);

        if (!cache.containsKey(cacheKey)) {
            Object obj = delegate.doQuery(ms, parameter);
            cache.putObject(cacheKey, obj);
            return (List<E>) obj;
        }

        return (List<E>) cache.getObject(cacheKey);
    }

    @Override
    public int doUpdate(MappedStatement ms, Map<String, Object> parameter) throws SQLException {
        return delegate.doUpdate(ms, parameter);
    }
}
