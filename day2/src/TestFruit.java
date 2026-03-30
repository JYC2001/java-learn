// 抽象产品
interface Fruit { void eat(); }

// 具体产品
class Apple implements Fruit { @Override public void eat() { System.out.println("吃苹果"); } }
class Banana implements Fruit { @Override public void eat() { System.out.println("吃香蕉"); } }

// 抽象工厂
interface FruitFactory { Fruit getFruit(); }

// 苹果工厂
class AppleFactory implements FruitFactory { @Override public Fruit getFruit() { return new Apple(); } }

// 香蕉工厂
class BananaFactory implements FruitFactory { @Override public Fruit getFruit() { return new Banana(); } }

// 测试
public class TestFruit {
    public static void main(String[] args) {
        FruitFactory factory = new AppleFactory();
        Fruit fruit = factory.getFruit();
        fruit.eat();
    }
}