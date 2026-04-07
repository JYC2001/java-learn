package com.example.hello.demo;

import com.example.hello.service.HelloService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

// 扫描自定义控制器包 + 自动配置包（缺一不可）
//@ComponentScan({"com.example.hello.demo.controller", "com.example.hello.config"})
@SpringBootApplication
public class HelloSpringBootStarterDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloSpringBootStarterDemoApplication.class, args);
    }

}
