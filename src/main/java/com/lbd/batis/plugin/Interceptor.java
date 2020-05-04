package com.lbd.batis.plugin;


public interface Interceptor {

    Object intercept(Invocation invocation) throws Throwable;

    Object plugin(Object target);
}
