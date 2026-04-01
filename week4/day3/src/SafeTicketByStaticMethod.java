public class SafeTicketByStaticMethod {
    private static int ticket = 100;
    //synchronized修饰静态方法，锁是SafeTicketByStaticMethod.class
    public static synchronized void sell(){
        while (ticket > 0){
            try { Thread.sleep(10);} catch (InterruptedException e){}
            System.out.println(Thread.currentThread().getName() + " 卖出1张，剩余：" + --ticket);
        }
    }

    public static void main(String[] args){
        //直接调用静态方法，3个线程共用同一个锁
        new Thread(() -> sell(), "窗口1").start();
        new Thread(() -> sell(), "窗口2").start();
        new Thread(() -> sell(), "窗口3").start();
    }
}
