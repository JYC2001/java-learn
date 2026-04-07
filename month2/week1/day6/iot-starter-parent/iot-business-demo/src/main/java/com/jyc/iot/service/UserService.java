package com.jyc.iot.service;

import com.jyc.iot.pojo.User;
import java.util.List;

public interface UserService {
    //查询所有用户
    List<User> listUser();
    //添加用户
    boolean addUser(User user);
}
