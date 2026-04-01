public class SleepDemo {
    // 锁对象
    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        Runnable task = () -> {
            synchronized (LOCK) {
                System.out.println(Thread.currentThread().getName() + " 获得了锁");
                try {
                    // 线程睡1秒
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 睡醒了，释放锁");
            }
        };

        // 创建两个线程
        Thread t1 = new Thread(task, "线程1");
        Thread t2 = new Thread(task, "线程2");

        t1.start();
        t2.start();
    }
}