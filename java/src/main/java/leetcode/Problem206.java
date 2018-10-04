package leetcode;

public class Problem206 {
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode(int x) { val = x; }
     * }
     */
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static class List {
        ListNode head = null;
        ListNode tail = null;

        List(ListNode head, ListNode tail) {
            this.head = head;
            this.tail = tail;
        }
    }

    public static class RecursiveSolution {
        public ListNode reverseList(ListNode head) {
            if (head == null) {
                return head;
            } else {
                List reversed = reverse(head);
                return reversed.head;
            }
        }

        List reverse(ListNode head) {
            // single node list is reverse of itself!
            if (head.next == null) {
                return new List(head, head);
            } else {
                // first reverse all but the head
                List restReversed = reverse(head.next);
                // now add head to the back of that reversed list!
                ListNode tailOfRestReversed = restReversed.tail;
                tailOfRestReversed.next = head;
                head.next = null;
                return new List(restReversed.head, head);
            }
        }
    }
}
