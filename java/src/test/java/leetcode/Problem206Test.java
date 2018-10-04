package leetcode;

import org.junit.Test;

public class Problem206Test {

    Problem206.RecursiveSolution recursiveSolution = new Problem206.RecursiveSolution();

    @Test
    public void test1() {
        Problem206.ListNode head = null;
        Problem206.ListNode tail = null;
        for (int i = 0; i < 50; i = i + 10) {
            Problem206.ListNode node = new Problem206.ListNode(i);
            if (head == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                tail = node;
            }
        }
        printList(head);
        Problem206.ListNode reversed = recursiveSolution.reverseList(head);
        printList(reversed);
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

}