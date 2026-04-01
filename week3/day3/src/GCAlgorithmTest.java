// Day9 标记-清除算法内存碎片模拟
public class MarkSweepFragmentTest {
    public static void main(String[] args) {
        // 1. 创建大量小对象，然后释放，模拟标记-清除回收
       <Object> list = new<>();
        for (int i = 0; i < 1000; i++) {
            list.add(new byte[1024]); // 1KB小对象
        }
        // 释放引用，让对象成为垃圾
        list.clear();
        System.gc(); // 触发GC，执行标记-清除，产生碎片

        // 2. 尝试分配大对象，因碎片无法分配，触发OOM
        try {
            byte[] bigArr = new byte[20 * 1024 * 1024]; // 20MB大对象
        } catch (OutOfMemoryError e) {
            System.out.println("标记-清除产生内存碎片，大对象无法分配，触发OOM");
            e.printStackTrace();
        }
    }
}