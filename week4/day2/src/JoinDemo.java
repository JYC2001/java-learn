public class JoinDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("子线程执行：" + i);
                try { Thread.sleep(200); } catch (InterruptedException e) {}
            }
        });

        t.start();
        // 主线程调用 t.join()
        System.out.println("主线程等待子线程...");
        t.join(); // 主线程阻塞，直到t执行完
        System.out.println("子线程执行完毕，主线程继续执行");
    }
}