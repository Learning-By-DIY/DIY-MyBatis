package com.lbd.batis.cache;

import java.util.ArrayList;
import java.util.List;

import com.lbd.batis.utils.ArrayUtil;


public class CacheKey  {
    private static final int DEFAULT_HASHCODE = 17;
    private static final int DEFAULT_MULTIPLYER = 37;

    private int hashCode;

    private int count;

    private int multiplyer;

    private List<Object> updateList;

    public CacheKey() {
        this.count = 0;
        this.hashCode = DEFAULT_HASHCODE;
        this.multiplyer = DEFAULT_MULTIPLYER;
        updateList = new ArrayList<>();
    }

    public void update(Object object) {
        int baseHashCode = object == null ? 1 : ArrayUtil.hashCode(object);

        count++;
        baseHashCode *= count;

        hashCode = multiplyer * hashCode + baseHashCode;

        updateList.add(object);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CacheKey)) {
            return false;
        }

        final CacheKey cacheKey = (CacheKey) object;

        if (hashCode != cacheKey.hashCode) {
            return false;
        }
        if (count != cacheKey.count) {
            return false;
        }

        for (int i = 0; i < updateList.size(); i++) {
            Object thisObject = updateList.get(i);
            Object thatObject = cacheKey.updateList.get(i);
            if (!ArrayUtil.equals(thisObject, thatObject)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}
