package com.lbd.batis.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lbd.batis.constants.SqlType;
import com.lbd.batis.mapping.MappedStatement;


public class CacheManage {
    private Map<String, List<CacheKey>> relations = new HashMap<>();

    private static String SELECT_EXPRESSION = "FROM\\s+(?:\\w+\\.)*(\\w+)($|\\s+[WHERE,JOIN,START\\s+WITH,ORDER\\s+BY,GROUP\\s+BY])";

    private static String UPDATE_EXPRESSION = "UPDATE\\s+?(\\w+)[\\s`]+?SET";

    private static String INSERT_EXPRESSION = "INSERT\\s+?INTO[\\s`]+?(\\w+)[\\s`]+?";

    private static String DELETE_EXPRESSION = "DELETE\\s+?FROM[\\s`]+?(\\w+)[\\s`]+?";

    private static Pattern selectPattern = Pattern.compile(SELECT_EXPRESSION, Pattern.CASE_INSENSITIVE);

    private static Pattern insertPattern = Pattern.compile(INSERT_EXPRESSION, Pattern.CASE_INSENSITIVE);

    private static Pattern updatePattern = Pattern.compile(UPDATE_EXPRESSION, Pattern.CASE_INSENSITIVE);

    private static Pattern deletePattern = Pattern.compile(DELETE_EXPRESSION, Pattern.CASE_INSENSITIVE);

    public CacheManage() {}

    public void putCacheKey(List<String> tables, CacheKey cacheKey) {
        for (String table: tables) {
            putCacheKey(table, cacheKey);
        }
    }

    public void putCacheKey(String table, CacheKey cacheKey) {
        List<CacheKey> keys = relations.getOrDefault(table, new ArrayList<>());
        keys.add(cacheKey);
    }

    public void clearCacheByTableName(String table, Cache cache) {
        List<CacheKey> keys = relations.getOrDefault(table, new ArrayList<>());

        for (CacheKey key : keys) {
            cache.removeObject(key);
        }
    }

    public void clearCacheByTableName(List<String> tables, Cache cache) {
        for(String table: tables) {
            clearCacheByTableName(table, cache);
        }
    }

    private static Pattern getPatternBySqlType(SqlType sqlType) {
        switch(sqlType) {
        case INSERT:
            return insertPattern;
        case UPDATE:
            return updatePattern;
        case SELECT:
            return selectPattern;
        case DELETE:
            return deletePattern;
        default:
            return null;
        }
    }

    public static List<String> extractTableName(MappedStatement ms, Map<String, Object> parameter) {
        Pattern pattern = getPatternBySqlType(ms.getSqlCommandType());

        String sql = ms.getOriginSql();
        for (Map.Entry<String, Object> entry: parameter.entrySet()) {
            sql = sql.replaceAll("#\\{" + entry.getKey() + "\\}", entry.getValue().toString());
        }

        Matcher matcher = pattern.matcher(sql);
        List<String> tables = new ArrayList<>();
        while(matcher.find()) {
            tables.add(matcher.group(1).toLowerCase());
        }

        return tables;
    }
}
