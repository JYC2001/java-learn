public class Test {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        new Thread(new Producer(warehouse), "P1").start();
        new Thread(new Consumer(warehouse), "C1").start();
        new Thread(new Consumer(warehouse), "C2").start();
    }
}