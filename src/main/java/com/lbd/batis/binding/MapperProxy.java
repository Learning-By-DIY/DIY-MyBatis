package com.lbd.batis.binding;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;



public class MapperProxy<T> implements InvocationHandler, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
