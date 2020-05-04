package com.lbd.batis.plugin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.lbd.batis.annotation.AddOnes;


public class Plugin implements InvocationHandler {
    private Object target;

    private Interceptor interceptor;

    public Plugin(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    public static Object wrap(Object obj, Interceptor interceptor) {
        Class<?> clazz = obj.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(),
                                      new Plugin(obj, interceptor));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (interceptor.getClass().isAnnotationPresent(AddOnes.class)) {
            if (method.getName().equals(interceptor.getClass().getAnnotation(AddOnes.class).value())) {
                return interceptor.intercept(new Invocation(target, method, args));
            }
        }
        return method.invoke(target, args);
    }
}
