// 方式1：synchronized实现（简单易懂，适合基础场景）
public class SafeCounter {
    private int count = 0;

    // 加锁保证累加操作原子性，线程安全
    public synchronized void increment() {
        count++;
    }

    // 加锁保证读取操作线程安全
    public synchronized int getCount() {
        return count;
    }

    // 测试类
    public static void main(String[] args) throws InterruptedException {
        SafeCounter counter = new SafeCounter();
        // 3个线程同时累加1000次
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("最终计数：" + counter.getCount()); // 预期3000，无线程安全问题
    }
}
