public class SafeTicketByMethon {
    private static int ticket = 100;
    // synchronized修饰普通方法，锁是this
    public synchronized void sell(){
        while(ticket > 0){
            try{Thread.sleep(10);} catch (InterruptedException e) {}
            System.out.println(Thread.currentThread().getName() + "卖出1张，剩余：" + --ticket);
        }
    }
    public static void main(String[] args){
        SafeTicketByMethon obj = new SafeTicketByMethon();

        new Thread(() -> obj.sell(), "窗口1").start();
        new Thread(() -> obj.sell(), "窗口2").start();
        new Thread(() -> obj.sell(), "窗口3").start();
    }
}
