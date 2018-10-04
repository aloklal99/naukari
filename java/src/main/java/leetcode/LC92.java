package leetcode;

public class Problem92 {
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode(int x) { val = x; }
     * }
     */
    static public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    static class Result {
        ListNode head;
        ListNode tail;
        ListNode afterEnd;

        Result(final ListNode head, final ListNode tail, final ListNode afterEnd) {
            this.head = head;
            this.tail = tail;
            this.afterEnd = afterEnd;
        }
    }

    static class Solution {
        public ListNode reverseBetween(ListNode head, int m, int n) {
            /*
             some edge cases are trivially handled
             - null or single node list
             - only one element is to be reversed, which leaves the list unchanged.
              */
            if (head == null || head.next == null || m == n) {
                return head;
            }
            final ListNode beforeStart = getNodeBeforeStart(head, m);
            final ListNode start;
            if (beforeStart == null) {
                start = head;
            } else {
                start = beforeStart.next;
            }
            final Result result = reverse(start, n - m);
            final ListNode tailOfFlippedSubList = result.tail;
            tailOfFlippedSubList.next = result.afterEnd;
            if (beforeStart == null) {
                // we have to return new head since we would be moving head itself
                return result.head;
            } else {
                beforeStart.next = result.head;
                // return original head as is
                return head;
            }
        }

        private Result reverse(final ListNode start, final int i) {
            if (i == 0) {
                return new Result(start, start, start.next);
            } else {
                final Result result = reverse(start.next, i - 1);
                final ListNode tailOfRest = result.tail;
                tailOfRest.next = start;
                start.next = null;
                return new Result(result.head, start, result.afterEnd);
            }
        }

        static ListNode getNodeBeforeStart(final ListNode head, final int m) {
            int i = 1;
            ListNode beforeStart = null;
            while (i < m) {
                if (beforeStart == null) {
                    beforeStart = head;
                } else {
                    beforeStart = beforeStart.next;
                }
                i++;
            }
            return beforeStart;
        }
    }
}
