/**
 * 模拟堆溢出（OutOfMemoryError: Java heap space）
 * 核心逻辑：循环创建对象并加入集合，集合持有对象引用，导致对象无法被GC回收，最终撑爆堆内存
 */
public class HeapOOMTest {
    //自定义一个对象，用于占用堆内存
    static class TestObject{}

    public static void main(String[] args){
        //用ArrayList持有对象引用，避免对象被GC回收
        java.util.List<TestObject> objectList = new java.util.ArrayList<>();

        //无限循环创建对象，加入基和
        while(true){
            objectList.add(new TestObject());
        }
    }
}
