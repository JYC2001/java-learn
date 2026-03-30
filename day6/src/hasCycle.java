import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class hasCycle{
    public boolean hasCycle(ListNode head){
        //边界：空链表 或 只有一个节点，一定无环
        if(head == null || head.next == null){
            return false;
        }
        //定义快慢指针
        ListNode slow = head;
        ListNode fast = head;

        //快指针不能走到null
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast){
                return true;
            }
        }

        return false;
    }
}

class Solution {
    public boolean hasCycle(ListNode head){
        Set<ListNode> set = new HashSet<>();

        while (head != null){
            //节点重复出现=有环
            if(set.contains(head)){
                return true;
            }
            set.add(head);
            head = head.next;
        }
        return false;
    }
}
