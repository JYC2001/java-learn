package com.example.hello.demo.config;

import com.example.hello.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary; // 加这个
// 用户自定义配置类
@Configuration
public class CustomHelloConfig {

    // 手动注册HelloService Bean，覆盖自动配置
    @Bean
    @Primary   // ✅ 加上这个注解，强制优先使用你的Bean！
    public HelloService helloService() {
        // 自定义参数
        return new HelloService("自定义前缀：", "自定义后缀！");
    }
}