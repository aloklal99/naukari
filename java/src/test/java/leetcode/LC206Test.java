package leetcode;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;


public class Problem206Test {

    final Problem206.RecursiveSolution recursiveSolution = new Problem206.RecursiveSolution();
    final Problem206.IterativeSolution iterativeSolution = new Problem206.IterativeSolution();
    final Random random = new Random();

    @Test
    public void testNull() {
        Problem206.ListNode reversed = recursiveSolution.reverseList(null);
        assertNull(reversed);

        reversed = iterativeSolution.reverseList(null);
        assertNull(reversed);
    }

    @Test
    public void testSingleNode() {
        Problem206.ListNode list = new Problem206.ListNode(10);
        String asString = asString(list);

        Problem206.ListNode reversed = recursiveSolution.reverseList(list);
        assertEquals(reversed.val, 10);

        reversed = iterativeSolution.reverseList(list);
        assertEquals(asString, asString(reversed));
    }

    @Test
    public void test1() {
        // do 5 runs
        for (int j = 0; j < 5; j++) {
            Problem206.ListNode head = null;
            Problem206.ListNode tail = null;
            final int upto = random.nextInt(20);     // could be 0 for a null list
            final int step = random.nextInt(5) + 1;  // step could be same as upto or more for null list
            for (int i = 0; i < upto; i = i + step) {
                Problem206.ListNode node = new Problem206.ListNode(i);
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

            final Problem206.ListNode reversed = recursiveSolution.reverseList(head);
            System.out.println("Recursive:  " + asString(reversed));

            final Problem206.ListNode reReversed = iterativeSolution.reverseList(reversed);
            System.out.println("Iterative:  " + asString(reReversed));

            assertEquals(stringRep, asString(reReversed));
        }
    }

    private void printList(Problem206.ListNode head) {
        while (head != null) {
            System.out.print(head.val);
            if (head.next != null) {
                System.out.print("->");
            }
            head = head.next;
        }
        System.out.println();
    }

    private String asString(Problem206.ListNode head) {
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