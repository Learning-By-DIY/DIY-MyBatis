package com.lbd.batis.session;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.lbd.batis.binding.MapperRegistry;
import com.lbd.batis.constants.Constant;
import com.lbd.batis.mapping.MappedStatement;


public class Configuration  {
    private List<String> annotationMapperPaths = new ArrayList<>();

    protected final MapperRegistry mapperRegistry = new MapperRegistry();

    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    public Configuration() {
        loadConfig();
    }

    private void loadConfig() {
        try {
            InputStream inStream = Configuration.class.getResourceAsStream(Constant.CONFIG_FILE_LOCATION);
            Properties prop = new Properties();
            prop.load(inStream);

            if (prop.contains(Constant.MAPPER_ANNOTATION_PATHS)) {
                String annotationPaths = prop.getProperty(Constant.MAPPER_ANNOTATION_PATHS);
                for (String path: annotationPaths.split(",")) {
                    annotationMapperPaths.add(path);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("read jdbc.properties exception", e);
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
