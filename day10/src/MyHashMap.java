import java.util.LinkedHashMap;
// 链表节点：解决哈希冲突
//class Node<K,V> implements Map.Entry<K,V> {
//    final int hash;    // key的哈希值（提前计算，避免重复计算）
//    final K key;       // 键（不可变，final修饰）
//    V value;           // 值
//    Node<K,V> next;    // 链表后继指针
//}
//
//// 红黑树节点：继承链表节点，优化长链表
//final class TreeNode<K,V> extends LinkedHashMap.Entry<K,V> {
//    TreeNode<K,V> parent; // 父节点
//    TreeNode<K,V> left;   // 左子节点
//    TreeNode<K,V> right;  // 右子节点
//    TreeNode<K,V> prev;   // 前驱节点（双向链表）
//    boolean red;          // 节点颜色（红/黑）
//}

public class MyHashMap<K, V> {
    //哈希桶数组
    private Node<K, V>[] table;
    //默认容量
    private static final int DEFAULT_CAPACITY = 16;
    //元素个数
    private int size;

    //初始化数组
    public MyHashMap(){
        table = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    //哈希函数：算出key存在哪个下标
    private int hash(K key){
        int h = key.hashCode();
        return h ^ (h >>> 16);
    }

    //put存数据
    public void put(K key, V value){
        //算出数组下标
        int index = hash(key) & (table.length - 1);

        //拿到当前桶的头节点
        Node<K, V> head = table[index];

        //如果这个桶是空的   直接放
        if(head == null){
            table[index] = new Node<>(hash(key), key, value, null);
            size++;
            return;
        }

        //如果不为空    遍历链表
        Node<K, V> current = head;
        while (current != null){
            //key相同   覆盖value
            if(current.key.equals(key)){
                current.value = value;
                return;
            }
            //到最后一个节点
            if(current.next == null){
                current.next = new Node<>(hash(key), key, value, null);
                size++;
                return;
            }
            current = current.next;
        }
    }

    // ==========================================
    // 4. get 取数据
    // ==========================================
    public V get(K key) {
        int index = hash(key) & (table.length - 1);
        Node<K, V> head = table[index];

        // 链表遍历找 key
        while (head != null) {
            if (head.key.equals(key)) {
                return head.value;
            }
            head = head.next;
        }
        // 没找到
        return null;
    }

    // ==========================================
    // 5. 链表节点（就是快递叠起来放）
    // ==========================================
    static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    // 测试
    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("张三", 99);
        map.put("李四", 88);
        map.put("张三", 100); // 覆盖

        System.out.println(map.get("张三"));  // 100
        System.out.println(map.get("李四"));  // 88
        System.out.println(map.get("王五"));  // null
    }
}

