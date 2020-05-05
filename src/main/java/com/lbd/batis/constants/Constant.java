package com.lbd.batis.constants;


public interface Constant {
    String DEFAULT_CHARSET = "UTF-8";

    String CONFIG_FILE_LOCATION = "/conf.properties";

    // JDBC
    String JDBC_DRIVER = "jdbc.driver";

    String JDBC_URL = "jdbc.url";

    String JDBC_USERNAME = "jdbc.username";

    String JDBC_PASSWORD = "jdbc.password";

    // Mapper Path
    String MAPPER_ANNOTATION_PATHS = "mapper.annotation.paths";

    String MAPPER_XML_PATHS = "mapper.xml.paths";

    String XML_ROOT_LABEL = "mapper";

    String XML_ELEMENT_ID = "id";

    String XML_SELECT_NAMESPACE = "namespace";

    String XML_SELECT_RESULTTYPE = "resultType";

    // Plugin
    String PLUGIN_PATH = "plugin.paths";
}
