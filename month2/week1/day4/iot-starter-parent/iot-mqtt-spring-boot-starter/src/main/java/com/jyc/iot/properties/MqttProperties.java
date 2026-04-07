package com.jyc.iot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MQTT配置绑定类
 * 前缀：iot.mqtt → 配置文件中用该前缀定义参数
 */
@ConfigurationProperties(prefix = "iot.mqtt")
public class MqttProperties {
    // MQTT服务地址，默认：tcp://127.0.0.1:1883（本地MQTT服务）
    private String broker = "tcp://127.0.0.1:1883";
    // 客户端ID，默认：iot-client-xxx（避免重复）
    private String clientId = "iot-client-" + System.currentTimeMillis();
    // 用户名（MQTT认证，默认空）
    private String username = "";
    // 密码（MQTT认证，默认空）
    private String password = "";
    // 连接超时时间，默认：30秒
    private int connectTimeout = 30;
    // 心跳间隔，默认：60秒
    private int keepAliveInterval = 60;
    // 是否清空会话，默认：false（重启后保留订阅关系）
    private boolean cleanSession = false;

    // 以下为getter/setter（必须写，Spring通过反射绑定配置）
    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getKeepAliveInterval() {
        return keepAliveInterval;
    }

    public void setKeepAliveInterval(int keepAliveInterval) {
        this.keepAliveInterval = keepAliveInterval;
    }

    public boolean isCleanSession() {
        return cleanSession;
    }

    public void setCleanSession(boolean cleanSession) {
        this.cleanSession = cleanSession;
    }
}