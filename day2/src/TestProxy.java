// 抽象主题
interface BuyHouse {
    void buy();
}

// 真实主题
class LandLord implements BuyHouse {
    @Override
    public void buy() {
        System.out.println("房东卖房");
    }
}

// 代理：中介
class Proxy implements BuyHouse {
    private LandLord landLord;
    public Proxy() { landLord = new LandLord(); }

    @Override
    public void buy() {
        System.out.println("中介带看、签合同");
        landLord.buy();
        System.out.println("中介交房");
    }
}

// 测试
public class TestProxy {
    public static void main(String[] args) {
        BuyHouse proxy = new Proxy();
        proxy.buy();
    }
}