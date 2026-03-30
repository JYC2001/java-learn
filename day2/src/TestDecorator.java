// 抽象组件
interface Phone {
    void show();
}

// 具体组件
class MyPhone implements Phone {
    @Override
    public void show() {
        System.out.print("我的手机");
    }
}

// 装饰器
abstract class Decorator implements Phone {
    protected Phone phone;
    public Decorator(Phone phone) { this.phone = phone; }
}

// 装饰：加壳
class ShellDecorator extends Decorator {
    public ShellDecorator(Phone phone) { super(phone); }
    @Override
    public void show() {
        phone.show();
        System.out.print(" + 手机壳");
    }
}

// 装饰：加膜
class FilmDecorator extends Decorator {
    public FilmDecorator(Phone phone) { super(phone); }
    @Override
    public void show() {
        phone.show();
        System.out.print(" + 钢化膜");
    }
}

// 测试
public class TestDecorator {
    public static void main(String[] args) {
        Phone phone = new MyPhone();
        phone = new ShellDecorator(phone);
        phone = new FilmDecorator(phone);
        phone.show();
    }
}