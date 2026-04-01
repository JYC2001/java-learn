/**
 * 方式一：继承 Thread 类
 * 特点：代码简单，但Java是单继承，继承了Thread就不能继承其他类了。
 * 返回值：无 (run() 是 void)
 */
public class ThreadCreateDemo extends Thread {

    // 1. 重写 run 方法，写线程任务
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            // Thread.currentThread().getName() 可以获取当前线程的名字
            System.out.println("线程【" + Thread.currentThread().getName() + "】执行：" + i);
            try {
                // 睡一下，让效果更明显
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // 2. 创建线程对象
        ThreadCreateDemo t1 = new ThreadCreateDemo();
        ThreadCreateDemo t2 = new ThreadCreateDemo();

        // 3. 启动线程
        t1.start();
        t2.start();
    }
}