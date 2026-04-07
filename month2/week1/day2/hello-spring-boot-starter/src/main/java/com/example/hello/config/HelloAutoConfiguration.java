package com.example.hello.config;

import com.example.hello.service.HelloService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 1. 标记为配置类，Spring会扫描并解析
@Configuration
// 2. 启用配置属性：让HelloProperties生效，能绑定配置文件参数
@EnableConfigurationProperties(HelloProperties.class)
// 3. 条件注解：类路径下存在HelloService时，该自动配置类才生效（防依赖缺失）
@ConditionalOnClass(HelloService.class)
//@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE) // 加这个

public class HelloAutoConfiguration {

    // 注入配置属性类（Spring已自动创建该Bean，因为@EnableConfigurationProperties）
    private final HelloProperties helloProperties;

    // 构造器注入（推荐，比@Autowired更优雅，Spring自动装配）
    public HelloAutoConfiguration(HelloProperties helloProperties) {
        this.helloProperties = helloProperties;
    }

    // 4. 注册HelloService Bean到Spring容器
    @Bean
    // 5. 核心条件注解：容器中没有HelloService Bean时，才自动创建（用户自定义则覆盖）
    @ConditionalOnMissingBean
    public HelloService helloService() {
        // 将配置文件的参数注入到HelloService
        return new HelloService(
                helloProperties.getPrefix(),
                helloProperties.getSuffix()
        );
    }
}