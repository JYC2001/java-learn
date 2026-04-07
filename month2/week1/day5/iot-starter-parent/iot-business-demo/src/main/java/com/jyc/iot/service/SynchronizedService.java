package com.jyc.iot.service;

import org.springframework.stereotype.Service;

@Service
public class SynchronizedService {

    private int count = 0;

    // 加锁：同一时间只有一个线程能进入
    public synchronized int add() {
        count++;
        try { Thread.sleep(10); } catch (Exception e) {}
        return count;
    }
}