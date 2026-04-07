package com.jyc.iot.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

@Service
public class LockService {

    private int count = 0;
    private final ReentrantLock lock = new ReentrantLock();

    public int add() {
        lock.lock(); // 加锁
        try {
            count++;
            Thread.sleep(10);
            return count;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock(); // 必须释放锁
        }
    }
}