package com.lbd.batis.plugin.plugins;

import java.util.HashMap;

import com.lbd.batis.annotation.AddOnes;
import com.lbd.batis.mapping.MappedStatement;
import com.lbd.batis.plugin.Interceptor;
import com.lbd.batis.plugin.Invocation;
import com.lbd.batis.plugin.Plugin;


@AddOnes("doQuery")
public class LogPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
        HashMap<String, Object> parameter = (HashMap<String, Object>) invocation.getArgs()[1];

        System.out.println("---------- log plugin ---------");
        System.out.println("execute query is: " + statement.getOriginSql());
        System.out.println("parameter is: " + parameter);
        System.out.println("entry is: " + statement.getResultType());
        System.out.println("---------- invocation finished ---------");

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}
