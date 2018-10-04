package leetcode;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;


public class LC92Test {
    final Random random = new Random(0);
    LC92.RecursiveSolution recursiveSolution = new LC92.RecursiveSolution();
    LC92.IterativeSolution iterativeSolution = new LC92.IterativeSolution();

    static class Result {
        LC92.ListNode head;
        int length;
        Result(final LC92.ListNode head, final int length) {
            this.head = head;
            this.length = length;
        }
    }

    @Test
    public void test1() {
        for (int i = 0; i < 10; i++) {
            Result result = createList();
            String stringRep = asString(result.head);
            System.out.println("Input list: " + stringRep);

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

            LC92.ListNode reversed = recursiveSolution.reverseBetween(result.head, m, n);
            System.out.println("Recursive:  " + asString(reversed));

            LC92.ListNode reReversed = iterativeSolution.reverseBetween(reversed, m, n);
            System.out.println("Iterative:  " + asString(reReversed));
            assertEquals(stringRep, asString(reReversed));
            System.out.println("----");
        }
    }

    @Test
    public void test2() {
        LC92.ListNode head = new LC92.ListNode(3);
        head.next = new LC92.ListNode(5);

        System.out.println(asString(head));
        System.out.println(asString(iterativeSolution.reverseBetween(head, 1, 2)));
    }

    Result createList() {
        LC92.ListNode head = null;
        LC92.ListNode tail = null;
        // could be 0 for a null list
        final int upto = random.nextInt(10);
        int length = 0;
        for (int i = 1; i <= upto; i++) {
            LC92.ListNode node = new LC92.ListNode(i);
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

    String asString(LC92.ListNode head) {
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