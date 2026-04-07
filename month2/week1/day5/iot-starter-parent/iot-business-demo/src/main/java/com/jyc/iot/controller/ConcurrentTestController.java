package com.jyc.iot.controller;

import com.jyc.iot.service.UnSafeCountService;
import com.jyc.iot.service.SynchronizedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class ConcurrentTestController {

//    private final UnSafeCountService unSafeCountService;
//
//    public ConcurrentTestController(UnSafeCountService unSafeCountService) {
//        this.unSafeCountService = unSafeCountService;
    private final SynchronizedService synchronizedService;

    public ConcurrentTestController(SynchronizedService synchronizedService) {
        this.synchronizedService = synchronizedService;
    }

    @GetMapping("/test/unsafe")
    public String testUnsafe() {
        // 模拟 100 个线程并发访问
        ExecutorService pool = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 100; i++) {
            pool.submit(() -> {
                int num = synchronizedService.add();
                System.out.println("线程：" + Thread.currentThread().getName() + " 计数：" + num);
            });
        }
        return "多线程并发测试开始 → 看控制台数字会错乱！";
    }
}