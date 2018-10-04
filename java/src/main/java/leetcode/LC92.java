package leetcode;

public class LC92 {
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode(int x) { val = x; }
     * }
     */
    static public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
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

    static class RecursiveSolution {
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

    static class IterativeSolution {
        public ListNode reverseBetween(final ListNode head, final int m, final int n) {
            // trivial cases
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
            ListNode prev = null;
            ListNode node = start;
            for (int i = 0; i <= n - m; i++) {
                ListNode next = node.next;
                node.next = prev;
                prev = node;
                node = next;
            }
            /*
             - perv is the head of flipped sublist
             - start is its tail and
             - node is the one just beyond the tail of flipped sublist
              */
            start.next = node; // tie the tail of flipped sub-list to remainder of original list
            if (beforeStart == null) {
                return prev;
            } else {
                beforeStart.next = prev; // tie the front part of list to new head of flipped sublist
                return head;
            }
        }

        private ListNode getNodeBeforeStart(final ListNode head, final int m) {
            ListNode beforeStart = null;
            for (int i = 1; i < m; i++) {
                if (beforeStart == null) {
                    beforeStart = head;
                } else {
                    beforeStart = beforeStart.next;
                }
            }
            return beforeStart;
        }
    }
}