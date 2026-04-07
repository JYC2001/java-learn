package com.jyc.iot;

import com.jyc.iot.core.MqttClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * MQTT Starter测试控制器
 * 验证：自动装配、配置绑定、消息发布功能
 */
@RestController
public class MqttTestController {

    // 直接注入Starter自动配置的MqttClientUtil Bean
    @Autowired
    private MqttClientUtil mqttClientUtil;

    /**
     * 测试MQTT消息发布接口
     * 访问：http://localhost:8080/mqtt/publish?topic=iot/device/temp&message=25.5
     * @param topic 主题
     * @param message 消息内容
     * @return 执行结果
     */
    @GetMapping("/mqtt/publish")
    public String publishMqttMessage(
            @RequestParam String topic,
            @RequestParam String message
    ) {
        try {
            // 调用Starter的MQTT工具类发布消息
            mqttClientUtil.publish(topic, message);
            return "MQTT消息发布成功！主题：" + topic + "，内容：" + message;
        } catch (Exception e) {
            return "MQTT消息发布失败：" + e.getMessage();
        }
    }
}