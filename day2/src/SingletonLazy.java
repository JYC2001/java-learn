public class SingletonLazy {
    private static volatile SingletonLazy instance;

    private SingletonLazy(){
        System.out.println("懒汉式单例：构造方法执行");
    }

    public static SingletonLazy getInstance(){
        if(instance == null){
            synchronized (SingletonLazy.class){
                if(instance == null){
                    instance = new SingletonLazy();
                }
            }
        }
        return instance;
    }

    public void showMessage(){
        System.out.println("我是懒汉式单例对象");
    }
}
