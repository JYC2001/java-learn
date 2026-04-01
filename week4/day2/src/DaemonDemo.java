public class DaemonDemo {
    public static void main(String[] args){
        Thread daemonThread = new Thread(() ->{
            while(true){
                System.out.println("我是守护线程，我一直工作...");
                try{Thread.sleep(500);} catch(InterruptedException e){}
            }
        });

        //设置为守护线程
        daemonThread.setDaemon(true);

        daemonThread.start();
        //主线程睡2秒
        try{ Thread.sleep(2000);} catch (InterruptedException e){}
        System.out.println("主线程结束了！");
    }
}
