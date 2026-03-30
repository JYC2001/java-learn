public class Test {
    public static void main(String[] args){
        // ====== 测试 单例模式 ======
        System.out.println("===== 饿汉式单例 =====");
        SingletonHungry s1 = SingletonHungry.getInstance();
        SingletonHungry s2 = SingletonHungry.getInstance();
        System.out.println("s1 和 s2 是同一个对象吗？" + (s1 == s2));
        s1.showMessage();

        System.out.println("\n===== 懒汉式单例 =====");
        SingletonLazy l1 = SingletonLazy.getInstance();
        SingletonLazy l2 = SingletonLazy.getInstance();
        System.out.println("s1 和 s2 是同一个对象吗？" + (l1 == l2));
        l1.showMessage();


        // ====== 测试 简单工厂模式 ======
        System.out.println("\n===== 简单工厂模式 =====");

        Person student = PersonFactory.getPerson("student");
        student.work();

        Person teacher = PersonFactory.getPerson("teacher");
        teacher.work();
    }
}
