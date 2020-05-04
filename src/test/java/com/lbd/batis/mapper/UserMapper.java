package com.lbd.batis.mapper;

import java.util.List;

import com.lbd.batis.bean.User;
import com.lbd.batis.annotation.Delete;
import com.lbd.batis.annotation.Insert;
import com.lbd.batis.annotation.Param;
import com.lbd.batis.annotation.Pojo;
import com.lbd.batis.annotation.Select;
import com.lbd.batis.annotation.Update;


@Pojo(User.class)
public interface UserMapper {
    @Select("select * from user where id = #{id}")
    User getUser(@Param("id") String id);

    @Select("select * from user")
    List<User> getAll();

    @Delete("delete from user where id = #{id}")
    int delete(@Param("id") String id);

    @Update("update user set name = #{name} where id = #{id}")
    int update(@Param("id") String id, @Param("name") String name);

    @Insert("insert into user (id, name, age, password) values (#{id}, #{name}, #{age}, #{password})")
    int insert(@Param("id") String id, @Param("name") String name,
               @Param("password") String password, @Param("age") int age);
}
