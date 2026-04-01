/**
 * Day10 常用GC收集器：ParallelGC（JDK8默认，多线程）实操
 * 运行参数：-Xmx100m -Xms100m -XX:+UseParallelGC -XX:+PrintGCDetails
 * 核心：多线程收集，停顿时间短，新生代复制算法、老年代标记-整理算法
 */
public class ParallelGCTest {
    static class BigObj{
        private byte[] data = new byte[1024*80];
    }

    public static void main(String[] args) throws InterruptedException{
        //新生代对象
        for(int i = 0; i < 300; i++){
            BigObj temp = new BigObj();
            temp = null;
        }
        Thread.sleep(500);

        //老年代对象
        java.util.List<BigObj> list = new java.util.ArrayList<>();
        for(int i = 0; i < 400; i++){
            list.add(new BigObj());
        }

        //手动触发GC，观察日志中”Parallel GC"，以及新生代/老年代算法
        System.gc();
        System.out.println("ParallelGC（默认）执行完成，新生代复制、老年代标记-整理");
    }
}
