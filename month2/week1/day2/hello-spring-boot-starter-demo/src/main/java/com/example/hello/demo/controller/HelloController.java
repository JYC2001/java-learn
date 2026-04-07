package com.example.hello.demo.controller;
import com.example.hello.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

// 测试接口
@RestController
public class HelloController {

    // 直接注入自动配置的HelloService Bean
    @Resource
    private HelloService helloService;

    // 测试接口：http://localhost:8080/hello/张三
    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable String name) {
        // 调用HelloService的方法
        return helloService.sayHello(name);
    }
}