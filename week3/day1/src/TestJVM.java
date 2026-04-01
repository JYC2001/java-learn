
class Parent {
    public static int pNum = 10; // 先执行
    static {
        System.out.println("父类静态代码块"); // 后执行
    }
}

class Son extends Parent {
    public static int sNum = 20; // 父类初始化完成后执行
    static {
        System.out.println("子类静态代码块"); // 最后执行
    }
}

// 调用：主动使用Son，触发父类→子类的初始化
public class TestJVM {
    public static void main(String[] args) {
        System.out.println(Son.sNum);
        // 输出：父类静态代码块 → 子类静态代码块 → 20
    }
}

