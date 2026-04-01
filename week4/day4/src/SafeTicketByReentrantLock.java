import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SafeTicketByReentrantLock {
    private static int ticket = 100;
    //创建锁对象（默认是非公平锁）,传入参数true就是公平锁
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args){
        Runnable sellTask = () -> {
            while(true){
                //手动枷锁
                lock.lock();
                try {
                    if (ticket <= 0) {
                        break; // 票卖完了，退出循环
                    }
                    // 模拟卖票耗时
                    try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
                    System.out.println(Thread.currentThread().getName() + " 卖出1张，剩余：" + --ticket);
                } finally {
                    // 3. 必须在 finally 中手动释放锁！！！
                    lock.unlock();
                }
            }
        };

        new Thread(sellTask, "窗口1").start();
        new Thread(sellTask, "窗口2").start();
        new Thread(sellTask, "窗口3").start();
    }
}

