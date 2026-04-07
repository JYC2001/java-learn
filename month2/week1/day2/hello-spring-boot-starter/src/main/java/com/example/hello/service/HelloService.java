package com.example.hello.service;

// 核心业务类，后续由Spring自动创建Bean
public class HelloService {
    // 可通过配置文件自定义的参数
    private String prefix; // 前缀，默认"你好："
    private String suffix; // 后缀，默认"！"

    // 核心方法：根据前缀后缀拼接字符串
    public String sayHello(String name) {
        return prefix + name + suffix;
    }

    // 无参构造（Spring反射创建Bean用）
    public HelloService() {}

    // 有参构造（配置绑定后注入参数用）
    public HelloService(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    // getter/setter 必须写（配置绑定需要）
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}