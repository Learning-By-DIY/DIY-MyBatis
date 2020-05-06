package com.lbd.batis.cache.impl;

import java.util.HashMap;
import java.util.Map;

import com.lbd.batis.cache.Cache;


public class PerpetualCache implements Cache {

    private final Map<Object, Object> cache = new HashMap<>();

    @Override
    public int getSize() {
        return cache.size();
    }

    @Override
    public void putObject(Object key, Object value) {
        cache.put(key, value);
    }

    @Override
    public Object getObject(Object key) {
        return cache.get(key);
    }

    @Override
    public Object removeObject(Object key) {
        return cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

}
