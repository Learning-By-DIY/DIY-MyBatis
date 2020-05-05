package com.lbd.batis.plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.Getter;

public class Invocation {
    @Getter
    private Object target;

    @Getter
    private Method method;

    @Getter
    private Object[] args;

    public Invocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    public Object proceed() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return method.invoke(target, args);
    }
}
