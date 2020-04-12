package com.lbd.batis.mapper;

import java.util.List;

import com.lbd.batis.bean.User;
import com.lbd.batis.annotation.Param;


public interface UserMapper {
    User getUser(@Param("id") String id);

    List<User> getAll();
}
