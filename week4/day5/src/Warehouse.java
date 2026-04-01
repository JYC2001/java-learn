import java.util.LinkedList;
import java.util.Queue;

public class Warehouse {
    private final Queue<Integer> queue = new LinkedList<>();
    private static final int CAPACITY = 5; // 仓库最大容量

    // 生产方法
    public synchronized void produce(int data) throws InterruptedException {
        // 仓库满了，生产者等待
        while (queue.size() == CAPACITY) {
            System.out.println("仓库满，生产者" + Thread.currentThread().getName() + "等待");
            this.wait();
        }
        queue.offer(data);
        System.out.println("生产者" + Thread.currentThread().getName() + "生产：" + data + "，库存：" + queue.size());
        // 生产完，唤醒消费者
        this.notifyAll();
    }

    // 消费方法
    public synchronized void consume() throws InterruptedException {
        // 仓库空了，消费者等待
        while (queue.isEmpty()) {
            System.out.println("仓库空，消费者" + Thread.currentThread().getName() + "等待");
            this.wait();
        }
        int data = queue.poll();
        System.out.println("消费者" + Thread.currentThread().getName() + "消费：" + data + "，库存：" + queue.size());
        // 消费完，唤醒生产者
        this.notifyAll();
    }
}