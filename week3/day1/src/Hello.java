
//被加载的类
public class Hello {
    public void sayHello(){
        System.out.println("===== 自定义类加载器加载成功 =====");
        //获取加载当前类的类加载器名称
        ClassLoader classLoader = this.getClass().getClassLoader();
        System.out.println("加载Hello类的类加载器：" + classLoader.getClass().getName());
        //获取父类加载器（应用程序类加载器）
        System.out.println("父类加载器：" + classLoader.getParent().getClass().getName());
    }
}
