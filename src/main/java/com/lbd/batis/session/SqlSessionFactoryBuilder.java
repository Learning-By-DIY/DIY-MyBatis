package com.lbd.batis.session;

import com.lbd.batis.session.defaults.DefaultSqlSessionFactory;


public class SqlSessionFactoryBuilder  {

    public SqlSessionFactory build() throws ClassNotFoundException {
        return new DefaultSqlSessionFactory(new Configuration());
    }
}
