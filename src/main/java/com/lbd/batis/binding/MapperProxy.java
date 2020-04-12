package com.lbd.batis.binding;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Collection;

import com.lbd.batis.mapping.MappedStatement;
import com.lbd.batis.session.SqlSession;



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

    private Object execute(Method method, Object[] args)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        String statementId = this.mapperInterface.getName() + "." + method.getName();
        MappedStatement ms = this.sqlSession.getConfiguration().getMappedStatement(statementId);

        Object result = null;

        switch(ms.getSqlCommandType()) {
        case UPDATE:
            Class<?> returnType = method.getReturnType();

            if(Collection.class.isAssignableFrom(returnType)) {
                result = sqlSession.selectList(statementId, args);
            } else {
                result = sqlSession.selectOne(statementId, args);
            }

            break;

        default:
            break;
        }

        return result;
    }
}
