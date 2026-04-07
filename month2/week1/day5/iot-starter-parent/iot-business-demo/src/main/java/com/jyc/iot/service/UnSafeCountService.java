package com.jyc.iot.service;

import org.springframework.stereotype.Service;

@Service
public class UnSafeCountService {

    // 共享成员变量 → 不安全根源
    private int count = 0;

    public int add() {
        count++;
        try {
            Thread.sleep(10); // 模拟业务耗时
        } catch (InterruptedException e) {}
        return count;
    }
}