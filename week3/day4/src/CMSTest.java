/**
 * Day10 常用GC收集器：CMS收集器（多线程，低停顿）实操
 * 运行参数：-Xmx100m -Xms100m -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails
 * 核心：CMS只负责老年代，多线程并发收集，停顿时间最短，适合高并发项目
 */
public class CMSTest {
    static class HighConcurrencyObj {
        private byte[] data = new byte[1024 * 60]; // 每个对象60KB
    }

    public static void main(String[] args) throws InterruptedException {
        // 模拟高并发场景下的对象创建，触发老年代GC（CMS负责）
        new Thread(() -> {
            java.util.List<HighConcurrencyObj> list = new java.util.ArrayList<>();
            for (int i = 0; i < 500; i++) {
                list.add(new HighConcurrencyObj());
                try {
                    Thread.sleep(10); // 模拟并发，缓慢创建对象
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 主线程也创建对象，增加内存压力
        for (int i = 0; i < 300; i++) {
            new HighConcurrencyObj();
            Thread.sleep(5);
        }

        // 手动触发GC，观察日志中"CMS GC"，重点看停顿时间（比Serial/Parallel短）
        System.gc();
        System.out.println("CMS收集器执行完成，低停顿特性适配高并发场景");
    }
}
