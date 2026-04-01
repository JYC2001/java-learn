public class UnsafeTicket {
    private static int ticket = 100; // 共享资源

    public static void main(String[] args) {
        Runnable sellTask = () -> {
            while (ticket > 0) {
                try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName() + " 卖出1张，剩余：" + --ticket);
            }
        };

        new Thread(sellTask, "窗口1").start();
        new Thread(sellTask, "窗口2").start();
        new Thread(sellTask, "窗口3").start();
    }
}