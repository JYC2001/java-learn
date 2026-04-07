package com.jyc.iot.config;

import com.jyc.iot.core.MqttClientUtil;
import com.jyc.iot.properties.MqttProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MQTT自动配置类（核心）
 * 实现：引入Starter后，自动装配MqttClientUtil Bean
 */
@Configuration // 标记为Spring配置类，让Spring扫描解析
@EnableConfigurationProperties(MqttProperties.class) // 启用配置绑定，让MqttProperties生效
@ConditionalOnClass(MqttClientUtil.class) // 条件注解：类路径下有MqttClientUtil才生效（防依赖缺失）
public class MqttAutoConfiguration {

    // 构造器注入配置绑定类（Spring自动装配，比@Autowired更优雅，无爆红）
    private final MqttProperties mqttProperties;

    public MqttAutoConfiguration(MqttProperties mqttProperties) {
        this.mqttProperties = mqttProperties;
    }

    /**
     * 注册MqttClientUtil Bean到Spring容器
     * @ConditionalOnMissingBean：容器中无MqttClientUtil Bean时，才自动创建（用户自定义则覆盖）
     */
    @Bean
    @ConditionalOnMissingBean // 核心条件注解，保证用户自定义优先
    public MqttClientUtil mqttClientUtil() {
        // 将配置文件的参数注入到MQTT工具类，创建实例
        return new MqttClientUtil(
                mqttProperties.getBroker(),
                mqttProperties.getClientId(),
                mqttProperties.getUsername(),
                mqttProperties.getPassword(),
                mqttProperties.getConnectTimeout(),
                mqttProperties.getKeepAliveInterval(),
                mqttProperties.isCleanSession()
        );
    }
}