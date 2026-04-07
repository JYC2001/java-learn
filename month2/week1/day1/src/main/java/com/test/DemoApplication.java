package com.test; // 你的包名，和建的包一致

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication // Spring Boot启动开关，必须加
public class DemoApplication {
    // 主方法，和普通Java的main一样，是程序入口
    public static void main(String[] args) {
        // 启动Spring Boot，返回「容器」（管理所有自动创建的对象）
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

        // 打印所有自动创建的对象（Bean），看是否有web相关对象
        System.out.println("===== Spring自动创建的对象列表 =====");
        String[] beanNames = context.getBeanDefinitionNames();
        for (String name : beanNames) {
            System.out.println(name);
        }
    }
}