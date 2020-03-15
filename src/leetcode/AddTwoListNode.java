package leetcode;

/**
 * @Author: Johnny Zhang
 * @Date: 2020/2/24 22:39
 */
public class AddTwoListNode {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(0);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(3);
        ListNode l2 = new ListNode(0);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(9);
        l2.next.next.next = new ListNode(1);

        Solution solution = new Solution();
        ListNode result = solution.addTwoNumbers(l1, l2);
    }
}
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode  listNode= new ListNode(0);
        ListNode p = new ListNode(0);
        p = listNode;
        int sum = 0;

        while (l1 != null || l2 != null || sum != 0) {
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            p.next = new ListNode(sum % 10);
            sum = sum / 10;
            p = p.next;
        }
        return listNode.next;
    }
}