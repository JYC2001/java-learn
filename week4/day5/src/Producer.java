public class Producer implements Runnable {
    private final Warehouse warehouse;

    public Producer(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            try {
                warehouse.produce(i);
                Thread.sleep(300); // 模拟生产时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

