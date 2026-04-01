public class Consumer implements Runnable {
    private final Warehouse warehouse;

    public Consumer(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            try {
                warehouse.consume();
                Thread.sleep(500); // 模拟消费时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}