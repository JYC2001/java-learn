public class MyLinkedList<E> {
    private static class Node<E>{
        E item;
        Node<E> prev;
        Node<E> next;

        Node(Node<E> prev, E element, Node<E> next){
            this.item = element;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node<E> first;
    private Node<E> last;
    private int size;

    public MyLinkedList(){}

    public boolean add(E e){
        linkLast(e);
        return true;
    }

    public void addFirst(E e){
        linkFirst(e);
    }
    public void add(int index, E e) {
        checkPositionIndex(index); // 校验索引：0<=index<=size
        if (index == size) {
            linkLast(e); // 索引等于size，尾插
        } else {
            linkBefore(e, node(index)); // 插入到目标节点前面
        }
    }

    // ============== 核心删除方法 ==============
    // 方法1：头删（O(1)）
    public E removeFirst() {
        final Node<E> f = first;
        if (f == null) {
            throw new NoSuchElementException("链表为空，无法删除");
        }
        return unlinkFirst(f);
    }

    // 方法2：尾删（O(1)）
    public E removeLast() {
        final Node<E> l = last;
        if (l == null) {
            throw new NoSuchElementException("链表为空，无法删除");
        }
        return unlinkLast(l);
    }

    // 方法3：指定索引删除（O(n)）
    public E remove(int index) {
        checkElementIndex(index); // 校验索引：0<=index<size
        return unlink(node(index));
    }

    // ============== 核心查询方法 ==============
    // 方法1：获取首节点元素（O(1)）
    public E getFirst() {
        final Node<E> f = first;
        if (f == null) {
            throw new NoSuchElementException("链表为空");
        }
        return f.item;
    }

    // 方法2：获取尾节点元素（O(1)）
    public E getLast() {
        final Node<E> l = last;
        if (l == null) {
            throw new NoSuchElementException("链表为空");
        }
        return l.item;
    }

    // 方法3：指定索引查询（O(n)，优化遍历：近头从头找，近尾从尾找）
    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    // ============== 核心修改方法 ==============
    // 指定索引修改元素（O(n)）
    public E set(int index, E element) {
        checkElementIndex(index);
        Node<E> x = node(index);
        E oldVal = x.item;
        x.item = element; // 直接修改节点的元素值
        return oldVal; // 返回旧值
    }

    // ============== 辅助方法：链表核心指针操作（JDK源码核心） ==============
    // 尾插辅助：将元素e链接为新的尾节点（O(1)）
    private void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null); // 新节点前驱为原尾节点，后继为null
        last = newNode; // 更新尾节点为新节点
        if (l == null) {
            first = newNode; // 原链表为空，首节点也设为新节点
        } else {
            l.next = newNode; // 原尾节点的后继指向新节点
        }
        size++; // 元素个数+1
    }

    // 头插辅助：将元素e链接为新的首节点（O(1)）
    private void linkFirst(E e) {
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(null, e, f); // 新节点前驱为null，后继为原首节点
        first = newNode; // 更新首节点为新节点
        if (f == null) {
            last = newNode; // 原链表为空，尾节点也设为新节点
        } else {
            f.prev = newNode; // 原首节点的前驱指向新节点
        }
        size++;
    }

    // 插入辅助：将e插入到目标节点succ前面（O(1)）
    private void linkBefore(E e, Node<E> succ) {
        final Node<E> pred = succ.prev; // 目标节点的前驱节点
        final Node<E> newNode = new Node<>(pred, e, succ); // 新节点夹在pred和succ之间
        succ.prev = newNode; // 目标节点的前驱指向新节点
        if (pred == null) {
            first = newNode; // pred为null，说明插入到首节点前，更新首节点
        } else {
            pred.next = newNode; // pred的后继指向新节点
        }
        size++;
    }

    // 头删辅助：断开首节点f的链接（O(1)）
    private E unlinkFirst(Node<E> f) {
        final E element = f.item;
        final Node<E> next = f.next; // 首节点的后继节点
        // 置空首节点的元素和指针，方便GC回收
        f.item = null;
        f.next = null;
        first = next; // 更新首节点为原首节点的后继
        if (next == null) {
            last = null; // 后继为null，说明链表只剩一个节点，删完为空
        } else {
            next.prev = null; // 新首节点的前驱置空
        }
        size--;
        return element;
    }

    // 尾删辅助：断开尾节点l的链接（O(1)）
    private E unlinkLast(Node<E> l) {
        final E element = l.item;
        final Node<E> prev = l.prev; // 尾节点的前驱节点
        // 置空尾节点，方便GC
        l.item = null;
        l.prev = null;
        last = prev; // 更新尾节点为原尾节点的前驱
        if (prev == null) {
            first = null; // 前驱为null，删完链表为空
        } else {
            prev.next = null; // 新尾节点的后继置空
        }
        size--;
        return element;
    }

    // 通用删除辅助：断开任意节点x的链接（O(1)）
    private E unlink(Node<E> x) {
        final E element = x.item;
        final Node<E> prev = x.prev; // 目标节点的前驱
        final Node<E> next = x.next; // 目标节点的后继

        // 处理前驱：如果前驱为null，说明是首节点，否则前驱的后继指向next
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null; // 置空目标节点前驱，方便GC
        }

        // 处理后继：如果后继为null，说明是尾节点，否则后继的前驱指向prev
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null; // 置空目标节点后继，方便GC
        }

        x.item = null; // 置空目标节点元素，方便GC
        size--;
        return element;
    }

    // 核心优化：根据索引找节点（O(n)，双向链表优化遍历）
    Node<E> node(int index) {
        // 判断索引靠近首端还是尾端，减少遍历次数
        if (index < (size >> 1)) { // 索引 < 长度/2，从首节点向后找
            Node<E> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else { // 索引 >= 长度/2，从尾节点向前找
            Node<E> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    // ============== 辅助方法：索引校验 ==============
    // 校验查询/删除的索引（必须0<=index<size）
    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("索引越界：index=" + index + "，size=" + size);
        }
    }

    // 校验插入的索引（必须0<=index<=size，可插入到末尾）
    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException("插入索引越界：index=" + index + "，size=" + size);
        }
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    // ============== 辅助方法：获取长度/判空 ==============
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // ============== 测试方法：验证所有核心功能 ==============
    public static void main(String[] args) {
        MyLinkedList<String> list = new MyLinkedList<>();

        // 1. 测试添加：尾插+头插+指定索引插
        list.add("元素1"); // 尾插
        list.add("元素2");
        list.addFirst("元素0"); // 头插
        list.add(2, "元素1.5"); // 指定索引2插入
        System.out.println("添加后链表长度：" + list.size()); // 4
        System.out.println("索引0元素：" + list.get(0)); // 元素0
        System.out.println("索引2元素：" + list.get(2)); // 元素1.5
        System.out.println("尾节点元素：" + list.getLast()); // 元素2

        // 2. 测试修改：修改索引2的元素
        String oldVal = list.set(2, "元素1-修改");
        System.out.println("修改前索引2元素：" + oldVal); // 元素1.5
        System.out.println("修改后索引2元素：" + list.get(2)); // 元素1-修改

        // 3. 测试删除：头删+尾删+指定索引删
        System.out.println("头删元素：" + list.removeFirst()); // 元素0
        System.out.println("尾删元素：" + list.removeLast()); // 元素2
        System.out.println("删除索引1元素：" + list.remove(1)); // 元素1-修改
        System.out.println("删除后链表长度：" + list.size()); // 1
        System.out.println("剩余元素：" + list.get(0)); // 元素1

        // 4. 测试遍历（推荐方式：foreach，需实现Iterable接口，这里简化用for+get）
        MyLinkedList<Integer> numList = new MyLinkedList<>();
        numList.add(10);
        numList.add(20);
        numList.add(30);
        System.out.println("\n遍历链表：");
        for (int i = 0; i < numList.size(); i++) {
            System.out.print(numList.get(i) + " "); // 10 20 30
        }
    }
}
