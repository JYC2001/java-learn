public class SingletonHungry {

    private  static final SingletonHungry instance = new SingletonHungry();

    private SingletonHungry(){
        System.out.println("饿汉式单例：构造方法执行");
    }

    public static SingletonHungry getInstance(){
        return instance;
    }

    public void showMessage(){
        System.out.println("我是饿汉式单例对象");
    }
}
