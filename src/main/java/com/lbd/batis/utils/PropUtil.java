package com.lbd.batis.utils;

import java.io.InputStream;
import java.util.Properties;

import com.lbd.batis.constants.Constant;


public class PropUtil  {
    private Properties prop = new Properties();

    {
        readProp();
    }

    public PropUtil() {}

    public String getProperty(String key) {
        if (containsKey(key)) {
            return prop.getProperty(key);
        }
        return null;
    }

    public String getOrDefault(String key, String defaultValue) {
        return (String) prop.getOrDefault(key, defaultValue);
    }

     private Properties readProp() {
         try {
             InputStream inStream = PropUtil.class.getResourceAsStream(Constant.CONFIG_FILE_LOCATION);
             prop.load(inStream);
             return prop;
         } catch (Exception e) {
             throw new RuntimeException("read jdbc.properties exception", e);
         }
     }

    public boolean containsKey(String key) {
        return prop.containsKey(key);
    }

    public boolean containsValue(String value) {
        return prop.containsValue(value);
    }
}
