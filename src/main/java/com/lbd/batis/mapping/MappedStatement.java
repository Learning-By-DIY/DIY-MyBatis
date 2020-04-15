package com.lbd.batis.mapping;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lbd.batis.constants.SqlType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
public class MappedStatement {
    private static Pattern paramPattern = Pattern.compile("#\\{([^\\{\\}]*)\\}");

    @Getter
    @Setter
    private String namespace;

    @Getter
    @Setter
    private String sqlId;

    @Getter
    private String sql;

    @Getter
    private String originSql;

    @Getter
    @Setter
    private String resultType;

    @Getter
    @Setter
    private SqlType sqlCommandType;

    @Getter
    private Map<String, List<Integer>> params = new HashMap<>();

    public void setSql(String sql) {
        this.originSql = sql;

        Matcher matcher = paramPattern.matcher(sql.trim());

        for(int i = 1; matcher.find(); ++i) {
            String name = matcher.group(1);
            List<Integer> indexes = params.getOrDefault(name, new ArrayList<>());
            indexes.add(i);
            params.put(name, indexes);
        }

        this.sql = matcher.replaceAll("?");
    }
}
