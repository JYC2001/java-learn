import java.util.List;

class Solution {
    public ListNode ReverseList(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;

        while(cur != null){
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return  prev;
    }
}

class Solutiona{
    public ListNode ReverseList(ListNode head){
        // 递归种植：：空或最后一个节点
        if(head == null || head.next == null){
            return head;
        }

        //递归反转后面的链表
        ListNode newHead = ReverseList(head.next);

        //翻转当前节点指针
        head.next.next = head;
        head.next = null;
        return newHead;
    }
}