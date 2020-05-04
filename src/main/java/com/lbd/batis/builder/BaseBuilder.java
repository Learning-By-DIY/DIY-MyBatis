package com.lbd.batis.builder;

import java.util.ArrayList;
import java.util.List;

import com.lbd.batis.session.Configuration;

import lombok.Getter;


public abstract class BaseBuilder {
    @Getter
    protected final Configuration configuration;

    private List<String> classPaths = new ArrayList<>();

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public abstract void parse() throws ClassNotFoundException;
}
