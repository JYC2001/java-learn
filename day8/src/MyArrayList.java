import java.util.Arrays;

public class MyArrayList {
    //底层存储容器：Object数组（兼容所有数据类型）
    private Object[] elementData;
    //实际元素个数（区别与数组容量）
    private int size;
    //默认初始容量
    private static final int DEFAULT_CAPACITY = 10;

    //无参构造：初始化空数组，第一次add时再扩容位10
    public MyArrayList(){
        this.elementData = new Object[0];
    }

    public MyArrayList(int initialCapacity){
        if(initialCapacity < 0){
            throw new IllegalArgumentException("容量不能为负数");
        }
        this.elementData = new Object[initialCapacity];
    }

    public void add(Object obj){
        ensureCapacity();
        elementData[size++] = obj;
    }

    public Object get(int index){
        checkIndex(index);
        return elementData[index];
    }

    public Object remove(int index){
        checkIndex(index);
        Object removeObj = elementData[index];
        int moveNum = size - index - 1;
        if(moveNum > 0){
            System.arraycopy(elementData, index+1, elementData, index, moveNum);
        }
        //置空末尾元素（让GC回收，避免内存泄漏），size自减
        elementData[--size] = null;
        return removeObj;

    }

    private void ensureCapacity(){
        if(elementData.length == 0){
            elementData = new Object[DEFAULT_CAPACITY];
            return;
        }
        if(size >= elementData.length){
            int oldCapacity = elementData.length;
            int newCapacity = oldCapacity + (oldCapacity>>1);
            elementData = Arrays.copyOf(elementData, newCapacity);
            System.out.println("触发扩容：原容量" + oldCapacity + " → 新容量" + newCapacity);
        }
    }

    private void checkIndex(int index){
        if(index<0 || index>=size){
            throw new IndexOutOfBoundsException("索引越界：index=" + index + "，size=" + size);        }
    }

    public int size(){
        return this.size;
    }

    public static void main(String[] args){
        MyArrayList list = new MyArrayList();
        for(int i = 0; i < 15; i++){
            list.add("元素" + i);
        }
        // 测试get：根据索引获取
        System.out.println("索引5的元素：" + list.get(5)); // 元素5
        // 测试remove：删除索引3的元素
        System.out.println("删除的元素：" + list.remove(3)); // 元素3
        // 验证删除后后续元素前移
        System.out.println("删除后索引3的元素：" + list.get(3)); // 元素4
        // 实际元素个数
        System.out.println("实际元素个数：" + list.size()); // 14
    }
}
