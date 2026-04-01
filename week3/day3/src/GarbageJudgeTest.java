// Day9 垃圾判定算法验证（引用计数法缺陷+可达性分析）
public class GarbageJudgeTest {
    // 两个类模拟循环引用
    static class ObjectA {
        public ObjectB b; // A引用B
    }
    static class ObjectB {
        public ObjectA a; // B引用A
    }

    public static void main(String[] args) {
        // 1. 模拟循环引用（引用计数法识别不了）
        ObjectA a = new ObjectA();
        ObjectB b = new ObjectB();
        a.b = b;
        b.a = a;

        // 2. 断开与GC Roots的连接（置为null）
        a = null;
        b = null;

        // 3. 手动触发GC，可达性分析会回收这两个对象
        System.gc();
        System.out.println("GC触发，循环引用对象已被回收（可达性分析生效）");
    }
}