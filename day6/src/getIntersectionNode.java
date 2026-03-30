import java.util.HashSet;
import java.util.Set;

public class getIntersectionNode {
    public  ListNode getIntersectionNode(ListNode headA, ListNode headB){
        if(headA == null || headB == null){
            return null;
        }
        ListNode pA = headA, pB = headB;
        while(pA != pB){
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }
}




public class Solution{
    public ListNode getIntersectionNode(ListNode headA, ListNode headB){
        Set<ListNode> nodeSet = new HashSet<>();
        ListNode cur = headA;
        while(cur != null){
            nodeSet.add(cur);
            cur = cur.next;
        }
        cur = headB;
        while(cur != null){
            if(nodeSet.contains(cur)){
                return cur;
            }
            cur = cur.next;
        }
        return null;
    }
}