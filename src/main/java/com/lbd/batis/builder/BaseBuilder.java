package com.lbd.batis.builder;

import com.lbd.batis.session.Configuration;

import lombok.Getter;


public abstract class BaseBuilder {
    @Getter
    protected final Configuration configuration;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public abstract void parse();
}
