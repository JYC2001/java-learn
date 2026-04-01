// 用synchronized+wait/notify实现，保证交替执行
public class AlternatePrint {
    private int num = 1; // 初始值
    private static final int MAX = 100;

    // 线程A：打印奇数
    public synchronized void printOdd() throws InterruptedException {
        while (num <= MAX) {
            // 若当前是偶数，线程A等待
            if (num % 2 == 0) {
                this.wait();
            } else {
                System.out.println("线程A（奇数）：" + num);
                num++;
                this.notify(); // 唤醒线程B
            }
        }
    }

    // 线程B：打印偶数
    public synchronized void printEven() throws InterruptedException {
        while (num <= MAX) {
            // 若当前是奇数，线程B等待
            if (num % 2 != 0) {
                this.wait();
            } else {
                System.out.println("线程B（偶数）：" + num);
                num++;
                this.notify(); // 唤醒线程A
            }
        }
    }

    // 测试类
    public static void main(String[] args) {
        AlternatePrint print = new AlternatePrint();

        // 线程A打印奇数
        new Thread(() -> {
            try {
                print.printOdd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "线程A").start();

        // 线程B打印偶数
        new Thread(() -> {
            try {
                print.printEven();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "线程B").start();
    }
}
