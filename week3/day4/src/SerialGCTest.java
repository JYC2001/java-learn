/**
 * Day10 常用GC收集器：SerialGC（单线程）实操
 * 运行参数：-Xmx50m -Xms50m -XX:+UseSerialGC -XX:+PrintGCDetails
 * 核心：SerialGC是单线程收集，GC时会暂停所有用户线程，停顿时间较长，适合小应用
 */
public class SerialGCTest {
    static class MyObject{
        //占用内存，触发GC
        private byte[] data = new byte[1024 * 50];  //每个对象50K
    }

    public static void main(String[] args){
        //循环创建对象，触发SerialGC回收
        for(int i = 0; i <200; i++){
            MyObject obj = new MyObject();
            //部分对象置为null，成为垃圾
            if(i%2 == 0){
                obj = null;
            }
        }

        //手动触发GC, 观察日志中"Serial GC"相关输出
        System.gc();
        System.out.println("SerialGC执行完成，日志中可看到单线程GC过程");
    }
}
