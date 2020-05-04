package com.lbd.batis.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lbd.batis.binding.MapperRegistry;
import com.lbd.batis.builder.AnnotationBuilder;
import com.lbd.batis.builder.BaseBuilder;
import com.lbd.batis.constants.Constant;
import com.lbd.batis.mapping.MappedStatement;
import com.lbd.batis.utils.PropUtil;

import lombok.Getter;

public class Configuration {
    @Getter
    private List<String> annotationMapperPaths = new ArrayList<>();

    protected final MapperRegistry mapperRegistry = new MapperRegistry();

    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    public Configuration() throws ClassNotFoundException {
        initMapperPaths();

        initBuilder();
    }

    private void initBuilder() throws ClassNotFoundException {
        BaseBuilder annotaionBuilder = new  AnnotationBuilder(this);
        annotaionBuilder.parse();
    }

    private void initMapperPaths() {
        PropUtil prop = new PropUtil();

        if (prop.containsKey(Constant.MAPPER_ANNOTATION_PATHS)) {
            String annotationPaths = prop.getProperty(Constant.MAPPER_ANNOTATION_PATHS);
            for (String path : annotationPaths.split(",")) {
                annotationMapperPaths.add(path);
            }
        }
    }

    public <T> void addMapper(Class<T> type) {
        this.mapperRegistry.addMapper(type);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return this.mapperRegistry.getMapper(type, sqlSession);
    }

    public void addMappedStatement(String key, MappedStatement mappedStatement) {
        this.mappedStatements.put(key, mappedStatement);
    }

    public MappedStatement getMappedStatement(String id) {
        return this.mappedStatements.get(id);
    }
}
