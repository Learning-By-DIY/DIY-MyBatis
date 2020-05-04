package com.lbd.batis.constants;

import com.lbd.batis.annotation.Delete;
import com.lbd.batis.annotation.Insert;
import com.lbd.batis.annotation.Select;
import com.lbd.batis.annotation.Update;


public interface SqlAnnotations {
    Class<?> [] SUPPORT_SQL_TYPES = {
        Select.class, Update.class, Insert.class, Delete.class
    };
}
