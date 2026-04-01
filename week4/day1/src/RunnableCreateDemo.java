/**
 * 方式二：实现 Runnable 接口 (推荐)
 * 特点：灵活，避免单继承局限，适合共享任务
 * 返回值：无 (run() 是 void)
 */

public class RunnableCreateDemo implements Runnable{

    @Override
    public void run(){
        for(int i = 0; i < 5; i++){
            System.out.println("线程【" + Thread.currentThread().getName() + "】执行：" + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // 1. 创建任务对象
        RunnableCreateDemo task = new RunnableCreateDemo();

        // 2. 把任务传给 Thread，再启动
        Thread t1 = new Thread(task, "线程A"); // 第二个参数可以给线程起名字
        Thread t2 = new Thread(task, "线程B");

        t1.start();
        t2.start();
    }
}
