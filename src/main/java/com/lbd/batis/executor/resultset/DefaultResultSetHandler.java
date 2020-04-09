package com.lbd.batis.executor.resultset;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lbd.batis.mapping.MappedStatement;


public class DefaultResultSetHandler implements ResultSetHandler {

    private final MappedStatement mappedStatement;

    public DefaultResultSetHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> handleResultSets(ResultSet resultSet) throws
        ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        List<E> result = new ArrayList<>();

        if (resultSet == null) {
            return null;
        }

        while(resultSet.next()) {
            Class<?> entityClass = Class.forName(mappedStatement.getResultType());
            E entity = (E) entityClass.newInstance();

            for (Field field : entity.getClass().getDeclaredFields()) {
                setValue(entity, field, resultSet);
            }

            result.add(entity);
        }

        return result;
    }

    private <E> void setValue(E entity, Field field, ResultSet resultSet) throws
        IllegalArgumentException, IllegalAccessException, SQLException {
        field.setAccessible(true);
        field.set(entity, getFieldValue(resultSet, field));
    }

    private Object getFieldValue(ResultSet resultSet, Field field) throws SQLException {
        Class<?> fieldType = field.getType();

        if (int.class.equals(fieldType) || Integer.class.equals(fieldType)) {
            return resultSet.getInt(field.getName());
        } else if (long.class.equals(fieldType) || Long.class.equals(fieldType)) {
            return resultSet.getLong(field.getName());
        } else if (float.class.equals(fieldType) || Float.class.equals(fieldType)) {
            return resultSet.getFloat(field.getName());
        } else if (double.class.equals(fieldType) || Double.class.equals(fieldType)) {
            return resultSet.getDouble(field.getName());
        } else if (boolean.class.equals(fieldType) || Boolean.class.equals(fieldType)) {
            return resultSet.getBoolean(field.getName());
        }

        return resultSet.getString(field.getName());
    }
}
