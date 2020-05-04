package com.lbd.batis.plugin.plugins;

import com.lbd.batis.annotation.AddOnes;
import com.lbd.batis.plugin.Interceptor;
import com.lbd.batis.plugin.Invocation;
import com.lbd.batis.plugin.Plugin;

@AddOnes("query")
public class LogPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("---------- log plugin ---------");
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}
