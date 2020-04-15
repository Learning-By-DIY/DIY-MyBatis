package com.lbd.batis.binding;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.lbd.batis.annotation.Param;
import com.lbd.batis.mapping.MappedStatement;
import com.lbd.batis.session.SqlSession;
import com.lbd.batis.utils.CommonUtils;


public class MapperProxy<T> implements InvocationHandler, Serializable {

    private static final long serialVersionUID = 1L;

    private final SqlSession sqlSession;
    private final Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        return this.execute(method, args);
    }

    private Map<String, Object> generateParams(Method method, Object[] args) {
        if (!CommonUtils.isNotEmpty(args)) {
            return null;
        }

        Map<String, Object> params = new HashMap<>();

        Annotation[][] annotations = method.getParameterAnnotations();

        for (int i = 0; i < args.length; ++i) {
            Annotation[] annos = annotations[i];
            for (Annotation anno : annos) {
                if (anno.annotationType().equals(Param.class)) {
                    String name = ((Param) anno).value();
                    params.put(name, args[i]);
                }
            }
        }

        return params;
    }

    private Object execute(Method method, Object[] args)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        String statementId = this.mapperInterface.getName() + "." + method.getName();
        MappedStatement ms = this.sqlSession.getConfiguration().getMappedStatement(statementId);

        Object result = null;

        Map<String, Object> params = generateParams(method, args);

        switch(ms.getSqlCommandType()) {
        case SELECT:
            Class<?> returnType = method.getReturnType();

            if(Collection.class.isAssignableFrom(returnType)) {
                result = sqlSession.selectList(statementId, params);
            } else {
                result = sqlSession.selectOne(statementId, params);
            }

            break;

        case UPDATE:
        case INSERT:
        case DELETE:
            result = sqlSession.update(statementId, params);
            break;

        default:
            break;
        }

        return result;
    }
}
