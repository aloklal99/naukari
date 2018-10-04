package leetcode;

import java.util.HashMap;
import java.util.Map;

public class Problem2 {

    public static void main(final String[] args) {
        Solution2 s = new Solution2();

        ListNode l1 = createList(1);
        System.out.println(l1);
        ListNode l2 = createList(9, 9);
        System.out.println(l2);
        ListNode l = s.addTwoNumbers(l1, l2);
        System.out.println(l);
        System.out.println();

        l1 = createList(2, 4, 3);
        System.out.println(l1);
        l2 = createList(5, 6, 4);
        System.out.println(l2);
        l = s.addTwoNumbers(l1, l2);
        System.out.println(l);
        System.out.println();
    }

    static ListNode createList(int ... vals) {
        ListNode head = null;
        ListNode tail = null;
        for (int val : vals) {
            final ListNode node = new ListNode(val);
            if (head == null) {
                head = node;
            } else {
                tail.next = node;
            }
            tail = node;
        }
        return head;
    }

    static public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            ListNode node = this;
            boolean empty = true;
            while (node != null) {
                if (empty) {
                    builder.append(node.val);
                    empty = false;
                } else {
                    builder.append(" -> ");
                    builder.append(node.val);
                }
                node = node.next;
            }
            return builder.toString();
        }
    }

    static class Solution1 {
        final static String HEAD = "head";
        final static String TAIL = "tail";
        final static String DIGIT = "digit";
        final static String CARRY = "carry";

        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            final Map<String, ListNode> result = new HashMap<>(2);
            Map<String, Integer> sumResult = new HashMap<>(2);
            sumResult.put(CARRY, 0);
            while (l1 != null && l2 != null) {
                sumResult = add(l1.val, l2.val, sumResult.get(CARRY));
                appendNode(result, sumResult.get(DIGIT));
                l1 = l1.next;
                l2 = l2.next;
            }
            ListNode l;
            if (l1 != null) {
                l = l1;
            } else {
                l = l2;
            }
            while (l != null) {
                sumResult = add(l.val, sumResult.get(CARRY));
                appendNode(result, sumResult.get(DIGIT));
                l = l.next;
            }
            if (sumResult.get(CARRY) > 0) {
                appendNode(result, sumResult.get(CARRY));
            }
            return result.get(HEAD);
        }

        private void appendNode(final Map<String, ListNode> list, final int val) {
            final ListNode node = new ListNode(val);
            final ListNode head = list.get(HEAD);
            if (head == null) {
                list.put(HEAD, node);
            } else {
                list.get(TAIL).next = node;
            }
            list.put(TAIL, node);
        }

        private Map<String, Integer> add(final int first, final int second, final int carry) {
            final Map<String, Integer> result = new HashMap<>(2);

            final int sum = first + second + carry;
            result.put(DIGIT, sum % 10);
            result.put(CARRY, sum / 10);

            return result;
        }

        private Map<String, Integer> add(final int first, final int carry) {
            return add(first, 0, carry);
        }
    }

    static class Solution2 {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            int carry = 0;
            ListNode head = null;
            ListNode tail = null;
            while (l1 != null || l2 != null) {
                final int first = l1 == null ? 0 : l1.val;
                final int second = l2 == null ? 0 : l2.val;
                final int sum = first + second + carry;
                final int digit = sum % 10;
                carry = sum / 10;
                final ListNode node = new ListNode(digit);
                if (head == null) {
                    head = node;
                } else {
                    tail.next = node;
                }
                tail = node;
                l1 = l1 == null ? null : l1.next;
                l2 = l2 == null ? null : l2.next;
            }
            if (carry > 0) {
                tail.next = new ListNode(carry);
            }
            return head;
        }
    }
}
