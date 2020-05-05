package com.lbd.batis;

import org.junit.jupiter.api.Test;

public class ATest {

    @Test
    public void testPlugin() throws ClassNotFoundException {
        String n = "com.lbd.batis.plugin.plugins.LogPlugin";
        Class.forName(n);
    }
}
