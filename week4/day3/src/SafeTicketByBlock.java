public class SafeTicketByBlock {
    private static int ticket = 100; //共享资源：100张票
    public static void main(String[] args){
        //3个线程同时卖票
        Runnable sell = () -> {
            // 锁对象：当前类的class对象，爆证3个线程用同一个锁
            synchronized (SafeTicketByBlock.class){
                while(ticket > 0){
                    try{ Thread.sleep(10);} catch (InterruptedException e){}
                    System.out.println(Thread.currentThread().getName() + "卖出1张，剩余：" + --ticket);
                }
            }
        };
        new Thread(sell, "窗口1").start();
        new Thread(sell, "窗口2").start();
        new Thread(sell, "窗口3").start();
    }
}
