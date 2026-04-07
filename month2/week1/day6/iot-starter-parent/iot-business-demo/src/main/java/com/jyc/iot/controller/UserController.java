package com.jyc.iot.controller;

import com.jyc.iot.pojo.User;
import com.jyc.iot.service.UserService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user") // 接口统一前缀
public class UserController {

    @Resource // 自动注入Service，无需手动配置
    private UserService userService;

    // 查询所有用户：GET http://localhost:8080/user/list
    @GetMapping("/list")
    public List<User> list() {
        return userService.listUser();
    }

    // 添加用户：POST http://localhost:8080/user/add + JSON参数
    @PostMapping("/add")
    public String add(@RequestBody User user) {
        boolean result = userService.addUser(user);
        return result ? "用户添加成功" : "用户添加失败";
    }
}