package com.jyc.iot.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

@Service
public class DeviceConnectService {

    private int onlineDeviceCount = 0;
    private final ReentrantLock lock = new ReentrantLock();

    // 设备上线
    public int deviceOnline() {
        lock.lock();
        try {
            onlineDeviceCount++;
            System.out.println("设备上线，当前在线：" + onlineDeviceCount);
            return onlineDeviceCount;
        } finally {
            lock.unlock();
        }
    }
}