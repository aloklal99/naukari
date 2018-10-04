package leetcode;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;


public class LC206Test {

    final LC206.RecursiveSolution recursiveSolution = new LC206.RecursiveSolution();
    final LC206.IterativeSolution iterativeSolution = new LC206.IterativeSolution();
    final Random random = new Random();

    @Test
    public void testNull() {
        LC206.ListNode reversed = recursiveSolution.reverseList(null);
        assertNull(reversed);

        reversed = iterativeSolution.reverseList(null);
        assertNull(reversed);
    }

    @Test
    public void testSingleNode() {
        LC206.ListNode list = new LC206.ListNode(10);
        String asString = asString(list);

        LC206.ListNode reversed = recursiveSolution.reverseList(list);
        assertEquals(reversed.val, 10);

        reversed = iterativeSolution.reverseList(list);
        assertEquals(asString, asString(reversed));
    }

    @Test
    public void test1() {
        // do 5 runs
        for (int j = 0; j < 5; j++) {
            LC206.ListNode head = null;
            LC206.ListNode tail = null;
            final int upto = random.nextInt(20);     // could be 0 for a null list
            final int step = random.nextInt(5) + 1;  // step could be same as upto or more for null list
            for (int i = 0; i < upto; i = i + step) {
                LC206.ListNode node = new LC206.ListNode(i);
                if (head == null) {
                    head = node;
                    tail = node;
                } else {
                    tail.next = node;
                    tail = node;
                }
            }
            final String stringRep = asString(head);
            System.out.println("Input list: " + stringRep);

            final LC206.ListNode reversed = recursiveSolution.reverseList(head);
            System.out.println("Recursive:  " + asString(reversed));

            final LC206.ListNode reReversed = iterativeSolution.reverseList(reversed);
            System.out.println("Iterative:  " + asString(reReversed));

            assertEquals(stringRep, asString(reReversed));
        }
    }

    private void printList(LC206.ListNode head) {
        while (head != null) {
            System.out.print(head.val);
            if (head.next != null) {
                System.out.print("->");
            }
            head = head.next;
        }
        System.out.println();
    }

    private String asString(LC206.ListNode head) {
        StringBuilder builder = new StringBuilder();
        while (head != null) {
            builder.append(head.val);
            if (head.next != null) {
                builder.append("->");
            }
            head = head.next;
        }
        return builder.toString();
    }
}