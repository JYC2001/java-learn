import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 方式三：实现 Callable 接口
 * 特点：有返回值，可以抛出异常，适合需要获取线程执行结果的场景
 * 返回值：有 (call() 有返回值)
 */
public class CallableCreateDemo implements Callable<Integer> {

    private int num;

    // 构造方法传参，方便演示
    public CallableCreateDemo(int num) {
        this.num = num;
    }

    // 1. 重写 call 方法，有返回值，也能抛异常
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= num; i++) {
            sum += i;
            System.out.println("线程【" + Thread.currentThread().getName() + "】计算中：" + i);
            Thread.sleep(100);
        }
        // 有返回值
        return sum;
    }

    public static void main(String[] args) {
        // 1. 创建 Callable 任务
        CallableCreateDemo task1 = new CallableCreateDemo(5);
        CallableCreateDemo task2 = new CallableCreateDemo(10);

        // 2. 用 FutureTask 包装 Callable (因为 Thread 只能接受 Runnable)
        FutureTask<Integer> futureTask1 = new FutureTask<>(task1);
        FutureTask<Integer> futureTask2 = new FutureTask<>(task2);

        // 3. 创建 Thread，传入 FutureTask (FutureTask 实现了 Runnable)
        Thread t1 = new Thread(futureTask1, "计算线程1");
        Thread t2 = new Thread(futureTask2, "计算线程2");

        // 4. 启动线程
        t1.start();
        t2.start();

        // 5. 获取线程返回值 (get() 方法会阻塞，直到线程执行完并返回结果)
        try {
            Integer result1 = futureTask1.get();
            Integer result2 = futureTask2.get();
            System.out.println("线程1计算结果：" + result1); // 应该是 1+2+3+4+5 = 15
            System.out.println("线程2计算结果：" + result2); // 应该是 1+2+...+10 = 55
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}