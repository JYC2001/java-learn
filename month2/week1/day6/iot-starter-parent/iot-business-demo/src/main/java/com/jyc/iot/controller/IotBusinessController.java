package com.jyc.iot.controller;


import com.jyc.iot.core.MqttClientUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 业务项目测试自定义Starter
 * 模拟物联网业务中使用MQTT Starter发送设备消息
 */
@RestController
public class IotBusinessController {

    // 直接注入Starter的Bean，Spring自动装配，无需手动创建
    private final MqttClientUtil mqttClientUtil;

    // 构造器注入（推荐，无爆红，更优雅）
    public IotBusinessController(MqttClientUtil mqttClientUtil) {
        this.mqttClientUtil = mqttClientUtil;
    }

    /**
     * 测试接口：模拟设备消息发送
     * 访问：http://localhost:8080/iot/sendMsg?topic=iot/device/001&msg=设备在线
     */
    @GetMapping("/iot/sendMsg")
    public String sendDeviceMsg(
            @RequestParam String topic,  // 设备主题
            @RequestParam String msg     // 设备消息
    ) {
        // 直接调用Starter封装的方法，无需关心底层MQTT实现
        return mqttClientUtil.publish(topic, msg);
    }
}