//父类
class Person{
    public void speak(){
        System.out.println("人在说话");
    }

    //================================
    //方法重载（Override）
    //同一个类中：方法名相同，参数列表不同
    //================================
    public void speak(String language){
        System.out.println("人说：" + language);
    }
}

//子类
class Student extends Person{
    //=================================
    //方法重写（Override）
    //子类中：方法名、参数、返回值完全一样
    //=================================
    @Override
    public void speak(){
        System.out.println("学生在说普通话");
    }
}

//测试类
public class TestOverride{
    public static void main(String[] args){
        Person p = new Person();
        p.speak();                //调用父类方法
        p.speak("英语");  //调用父类重载方法

        System.out.println("-----------------");

        Student s = new Student();
        s.speak();
        s.speak("方言");
    }
}
