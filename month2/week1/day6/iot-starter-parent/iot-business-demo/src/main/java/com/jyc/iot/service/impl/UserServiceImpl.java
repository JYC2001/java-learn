package com.jyc.iot.service.impl;

import com.jyc.iot.mapper.UserMapper;
import com.jyc.iot.pojo.User;
import com.jyc.iot.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service // 标记为服务层Bean，Spring Boot自动扫描
public class UserServiceImpl implements UserService {

    @Resource //自动注入Mapper，无需手动配置
    private UserMapper userMapper;

    @Override
    public List<User> listUser() {
        return userMapper.listAll();
    }

    @Override
    public boolean addUser(User user){
        return userMapper.addUser(user) > 0;
    }
}
