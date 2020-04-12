package com.lbd.batis.dao;

import java.util.List;

import com.lbd.batis.annotation.Param;


public interface UserMapper {
    User getUser(@Param("id") String id);

    List<User> getAll();
}
