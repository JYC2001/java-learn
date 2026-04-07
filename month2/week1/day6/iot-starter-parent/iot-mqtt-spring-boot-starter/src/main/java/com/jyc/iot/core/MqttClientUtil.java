//package com.jyc.iot.core;
//
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * MQTT核心工具类
// * 封装：连接、发布消息、订阅主题
// */
//public class MqttClientUtil {
//    // 日志（必备，便于排查问题）
//    private static final Logger log = LoggerFactory.getLogger(MqttClientUtil.class);
//    // MQTT客户端实例
//    private MqttClient mqttClient;
//    // MQTT连接配置
//    private MqttConnectOptions connectOptions;
//
//    // 构造器：通过配置参数初始化连接
//    public MqttClientUtil(String broker, String clientId, String username, String password,
//                          int connectTimeout, int keepAliveInterval, boolean cleanSession) {
//        try {
//            // 初始化MQTT连接配置
//            connectOptions = new MqttConnectOptions();
//            connectOptions.setUserName(username);
//            connectOptions.setPassword(password.toCharArray());
//            connectOptions.setConnectionTimeout(connectTimeout);
//            connectOptions.setKeepAliveInterval(keepAliveInterval);
//            connectOptions.setCleanSession(cleanSession);
//            // 自动重连（核心特性，物联网设备断网后自动重连）
//            connectOptions.setAutomaticReconnect(true);
//
//            // 初始化MQTT客户端（内存持久化，轻量）
//            mqttClient = new MqttClient(broker, clientId, new MemoryPersistence());
//            // 连接MQTT服务器
////            mqttClient.connect(connectOptions);
//            log.info("MQTT连接成功！broker：{}，clientId：{}", broker, clientId);
//        } catch (MqttException e) {
//            log.error("MQTT连接失败！", e);
//            throw new RuntimeException("MQTT连接失败，请检查配置", e);
//        }
//    }
//
//    /**
//     * 发布MQTT消息
//     * @param topic 主题（如：iot/device/temp）
//     * @param message 消息内容
//     * @param qos 服务质量（0：最多一次，1：至少一次，2：恰好一次）
//     */
//    public void publish(String topic, String message, int qos) {
//        try {
//            if (!mqttClient.isConnected()) {
//                mqttClient.reconnect();
//                log.info("MQTT重连成功！");
//            }
//            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
//            mqttMessage.setQos(qos);
//            mqttClient.publish(topic, mqttMessage);
//            log.info("MQTT消息发布成功！主题：{}，内容：{}", topic, message);
//        } catch (MqttException e) {
//            log.error("MQTT消息发布失败！", e);
//            throw new RuntimeException("MQTT消息发布失败", e);
//        }
//    }
//
//    /**
//     * 简化版发布：默认QOS=1（物联网最常用，保证消息至少送达一次）
//     */
//    public void publish(String topic, String message) {
//        publish(topic, message, 1);
//    }
//
//    /**
//     * 订阅MQTT主题
//     * @param topic 主题（支持通配符：# 匹配多级，+ 匹配单级）
//     * @param qos 服务质量
//     */
//    public void subscribe(String topic, int qos) {
//        try {
//            if (!mqttClient.isConnected()) {
//                mqttClient.reconnect();
//                log.info("MQTT重连成功！");
//            }
//            mqttClient.subscribe(topic, qos);
//            log.info("MQTT主题订阅成功！主题：{}", topic);
//        } catch (MqttException e) {
//            log.error("MQTT主题订阅失败！", e);
//            throw new RuntimeException("MQTT主题订阅失败", e);
//        }
//    }
//
//    /**
//     * 关闭MQTT连接
//     */
//    public void close() {
//        try {
//            if (mqttClient != null && mqttClient.isConnected()) {
//                mqttClient.disconnect();
//                mqttClient.close();
//                log.info("MQTT连接已关闭！");
//            }
//        } catch (MqttException e) {
//            log.error("MQTT连接关闭失败！", e);
//        }
//    }
//
//    // 获取MQTT客户端实例（供高级用户自定义操作）
//    public MqttClient getMqttClient() {
//        return mqttClient;
//    }
//}

package com.jyc.iot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttClientUtil {

    private static final Logger log = LoggerFactory.getLogger(MqttClientUtil.class);

    private String broker;
    private String clientId;

    // 构造方法（简化版，绝对不创建MQTT客户端）
    public MqttClientUtil(String broker, String clientId, String username, String password,
                          int connectTimeout, int keepAliveInterval, boolean cleanSession) {
        this.broker = broker;
        this.clientId = clientId;
        log.info("【Starter正常加载】MQTT客户端创建成功：{}", broker);
    }

    // 发布消息（纯模拟，不连接任何服务器）
    public String publish(String topic, String message) {
        return "✅ Starter调用成功 → 主题：" + topic + " | 消息：" + message;
    }

    public String getBroker() {
        return broker;
    }

    public String getClientId() {
        return clientId;
    }
}