public class YieldDemo {
    public static void main(String[] args) {
        Runnable task = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
                // 偶数时礼让一下
                if (i % 2 == 0) {
                    Thread.yield();
                }
            }
        };

        new Thread(task, "A").start();
        new Thread(task, "B").start();
    }
}