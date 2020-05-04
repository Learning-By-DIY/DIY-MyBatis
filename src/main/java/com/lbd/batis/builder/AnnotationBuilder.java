package com.lbd.batis.builder;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.lbd.batis.annotation.Delete;
import com.lbd.batis.annotation.Insert;
import com.lbd.batis.annotation.Pojo;
import com.lbd.batis.annotation.Select;
import com.lbd.batis.annotation.Update;
import com.lbd.batis.constants.SqlAnnotations;
import com.lbd.batis.constants.SqlType;
import com.lbd.batis.mapping.MappedStatement;
import com.lbd.batis.session.Configuration;


public class AnnotationBuilder extends BaseBuilder {

    private List<String> classPaths = new ArrayList<>();

    private List<Class<?>> mapperList = new ArrayList<>();

    public AnnotationBuilder(Configuration configuration) {
        super(configuration);
    }

    public void parse() throws ClassNotFoundException {
        List<String> mapperPaths = configuration.getAnnotationMapperPaths();

        for (String path: mapperPaths) {
            scanPackage(path);
        }

        for(Class<?> mapper : mapperList) {
            if (mapper.isInterface()) {
                parsingClass(mapper);
            }
        }
    }

    private void fullStatement(Method method, MappedStatement statement,
                               Class<?> clazz, String resultType) {
        statement.setSqlCommandType(SqlType.getByValue(clazz.getSimpleName()));
        statement.setResultType(resultType);

        for(Annotation annotation: method.getDeclaredAnnotations()) {
            if(annotation.annotationType().equals(clazz)) {
                if (clazz.equals(Select.class)) {
                    statement.setSql(((Select) annotation).value());
                } else if (clazz.equals(Delete.class)) {
                    statement.setSql(((Delete) annotation).value());
                } else if (clazz.equals(Insert.class)) {
                    statement.setSql(((Insert) annotation).value());
                } else if (clazz.equals(Update.class)) {
                    statement.setSql(((Update) annotation).value());
                }
            }
        }
    }

    @SuppressWarnings(value="unchecked")
    private void parsingClass(Class<?> mapper) {
        String namespace = mapper.getName();
        String resultType = null;
       
        if (mapper.isAnnotationPresent(Pojo.class)) {
            for (Annotation annotation : mapper.getAnnotations()) {
                if(annotation.annotationType().equals(Pojo.class)) {
                    resultType = ((Pojo) annotation).value().getName();
                }
            }
        }

        Method[] methods = mapper.getMethods();

        for(Method method: methods) {
            String sqlId = namespace + "." + method.getName();
            MappedStatement statement = new MappedStatement();

            statement.setSqlId(sqlId);
            statement.setNamespace(namespace);

            for (Class<?> clazz: SqlAnnotations.SUPPORT_SQL_TYPES) {
                Class<? extends Annotation> clazz0 = (Class<? extends Annotation>) clazz;
                if (method.isAnnotationPresent(clazz0)) {
                    fullStatement(method, statement, clazz0,
                                  clazz.equals(Select.class) ? resultType : null);
                }
            }

            configuration.addMappedStatement(sqlId, statement);
        }

        configuration.addMapper(mapper);
    }

    private void scanPackage(String mapperPath) throws ClassNotFoundException {
        String classPath = AnnotationBuilder.class.getResource("/").getPath();
        String mainPath = classPath + mapperPath;
        doPath(new File(mainPath));
        for (String className: classPaths) {
            className = className.replace(classPath, "")
                .replace(File.separator, ".").replace(".class", "");
            Class<?> clazz = Class.forName(className);
            mapperList.add(clazz);
        }
    }

    private void doPath(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                doPath(f);
            }
        } else if (file.getName().endsWith(".class")) {
            classPaths.add(file.getPath());
        }
    }
}
