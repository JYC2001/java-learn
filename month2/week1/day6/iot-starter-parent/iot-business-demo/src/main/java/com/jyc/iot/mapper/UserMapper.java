package com.jyc.iot.mapper;

import com.jyc.iot.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //标记为持久层Bean，SPring Boot自动扫描
public interface UserMapper {
    //查询所有用户
    @Select("select id, user_name, password, phone from user")
    List<User> listAll();

    //添加用户
    @Insert("insert into user(user_name, password, phone) values(#{userName}, #{password}, #{phone})")
    int addUser(User user);
}
