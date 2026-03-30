// 产品：电脑
class Computer {
    private String cpu;
    private String gpu;
    private String memory;

    public void setCpu(String cpu) { this.cpu = cpu; }
    public void setGpu(String gpu) { this.gpu = gpu; }
    public void setMemory(String memory) { this.memory = memory; }

    @Override
    public String toString() {
        return "Computer{" + "cpu='" + cpu + '\'' + ", gpu='" + gpu + '\'' + ", memory='" + memory + '\'' + '}';
    }
}

// 抽象建造者
abstract class ComputerBuilder {
    protected Computer computer = new Computer();
    public abstract void buildCpu();
    public abstract void buildGpu();
    public abstract void buildMemory();
    public Computer getComputer() { return computer; }
}

// 具体建造者：游戏电脑
class GameComputerBuilder extends ComputerBuilder {
    @Override
    public void buildCpu() { computer.setCpu("i9 14900K"); }
    @Override
    public void buildGpu() { computer.setGpu("RTX 5090"); }
    @Override
    public void buildMemory() { computer.setMemory("64G DDR5"); }
}

// 指挥者：指挥怎么造
class Director {
    private ComputerBuilder builder;
    public Director(ComputerBuilder builder) { this.builder = builder; }

    public Computer construct() {
        builder.buildCpu();
        builder.buildGpu();
        builder.buildMemory();
        return builder.getComputer();
    }
}

// 测试
public class TestBuilder {
    public static void main(String[] args) {
        ComputerBuilder builder = new GameComputerBuilder();
        Director director = new Director(builder);
        Computer computer = director.construct();
        System.out.println(computer);
    }
}