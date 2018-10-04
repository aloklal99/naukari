package leetcode;

import org.junit.Test;

import java.util.Random;

public class Problem92Test {
    final Random random = new Random(0);
    Problem92.Solution solution = new Problem92.Solution();

    static class Result {
        Problem92.ListNode head;
        int length;
        Result(final Problem92.ListNode head, final int length) {
            this.head = head;
            this.length = length;
        }
    }

    @Test
    public void test1() {
        for (int i = 0; i < 10; i++) {
            Result result = createList();
            System.out.println(asString(result.head));

            final int m;
            if (result.length == 0) {
                m = 0;
            } else {
                m = random.nextInt(result.length) + 1;
            }
            final int n;
            if (m == result.length) {
                n = m;
            } else {
                n = random.nextInt(result.length - m) + m;
            }
            System.out.println("m = " + m + ", n = " + n);

            Problem92.ListNode reversed = solution.reverseBetween(result.head, m, n);
            System.out.println(asString(reversed));
            System.out.println("----");
        }
    }

    Result createList() {
        Problem92.ListNode head = null;
        Problem92.ListNode tail = null;
        // could be 0 for a null list
        final int upto = random.nextInt(10);
        int length = 0;
        for (int i = 1; i <= upto; i++) {
            Problem92.ListNode node = new Problem92.ListNode(i);
            if (head == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                tail = node;
            }
            length++;
        }
        return new Result(head, length);
    }

    String asString(Problem92.ListNode head) {
        StringBuilder builder = new StringBuilder();
        while (head != null) {
            builder.append(head.val);
            if (head.next != null) {
                builder.append(" -> ");
            }
            head = head.next;
        }
        return builder.toString();
    }
}