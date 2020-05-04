package com.lbd.batis.constants;


public enum SqlType {
    SELECT("select"),
    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete"),
    DEFAULT("default");

    private String value;

    private SqlType(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    public static SqlType getByValue(String value) {
        return SqlType.valueOf(value.toUpperCase());
    }
}
