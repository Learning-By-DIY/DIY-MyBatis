package com.lbd.batis.session;

import java.util.Map;

import com.lbd.batis.constants.Constant;
import com.lbd.batis.session.defaults.DefaultSqlSessionFactory;


public class SqlSessionFactoryBuilder  {

    public SqlSessionFactory build() throws ClassNotFoundException {
        return new DefaultSqlSessionFactory(new Configuration());
    }

    public SqlSessionFactory build(Map<String, String> config) throws ClassNotFoundException {
        Boolean cacheEnable = false;

        if (config.containsKey(Constant.CACHE_ENABLE)) {
            String value = config.get(Constant.CACHE_ENABLE).toLowerCase();
            if (value == "true" || value == "Y") {
                cacheEnable = true;
            }
        }

        return new DefaultSqlSessionFactory(new Configuration(cacheEnable));
    }
}
