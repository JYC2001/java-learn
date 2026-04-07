package com.example.hello.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

// 配置绑定核心注解：prefix指定配置文件的前缀
@ConfigurationProperties(prefix = "com.example.hello")
public class HelloProperties {
    // 默认值：如果配置文件没写，使用该值
    private String prefix = "你好：";
    private String suffix = "！";

    // getter/setter 必须写
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