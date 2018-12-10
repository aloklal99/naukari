package mahendra;

public class NthFromEnd {
    public static void main(String[] args) {
        final Node head = Node.makeList(new int[] {10, 20, 30, 40, 50, 60, 70, 80, 90});
        System.out.println("Input list:\n" + head);

        // 3rd from the last node is "80"
        Node nth = Node.nthFromEnd(head, 2);
        System.out.println("2nd from end:\n" + nth);
        assert nth.val_ == 80;

        // now let's pick some node in the middle of the list, say, we pick the node "60"
        Node start = head;
        while (start.val_ != 60) {
            start = start.next;
        }
        assert start.val_ == 60;
        System.out.println("A node in the middle: (@ 60):\n" + start);

        // now again let's find the 2nd from the last.  Since 70 is after it that should work as if we were
        // doing this from start
        nth = Node.nthFromEnd(start, 2);
        assert nth.val_ == 80;
        System.out.println("2nd from end starting from a node in the middle:\n" + nth);

        // now let's find 5th element from end, first we start from the head
        nth = Node.nthFromEnd(head, 5);
        System.out.println("5th from end while start from head:\n" + nth);
        assert nth.val_ == 50;
        // i.e. 50, starting at node 60.
        nth = Node.nthFromEndWithMiddle(head, start, 5);
        System.out.println("5th from end while given a middle node (@60):\n" + nth);
        assert nth.val_ == 50;
    }

    static class Node {
        final int val_;
        Node next;
        Node(final int val) {
            val_ = val;
        }
        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            boolean first = true;

            Node head = this;
            while (head != null) {
                if (first) {
                    builder.append(head.val_);
                    first = false;
                } else {
                    builder.append("->")
                            .append(head.val_);
                }
                head = head.next;
            }
            return builder.toString();
        }

        static Node nthFromEnd(final Node head, final int n) {
            Node first = head;
            int i = 0;
            while (i < n && first != null) {
                first = first.next;
                i++;
            }
            if (i != n) {
                throw new IllegalArgumentException("there aren't enough nodes in the list!");
            }
            Node nth = head;
            while (first != null) {
                first = first.next;
                nth = nth.next;
            }
            return nth;
        }

        static Node nthFromEndWithMiddle(final Node head, final Node middle, final int n) {
            Node first = middle;
            int i = 0;
            while (i < n && first != null) {
                first = first.next;
                i++;
            }
            Node nth;
            if (i < n) {  // we reached head before finding n nodes!
                first = head;
                final int m = n - i;
                i = 0;
                while (i < m && first != middle) {
                    first = first.next;
                    i++;
                }
                if (i < m) {
                    throw new IllegalArgumentException("There aren't eough nodes in the list!");
                }
                nth = head;
                while (first != middle) {
                    first = first.next;
                    nth = nth.next;
                }
            } else {
                nth = head;
                while (first != null) {
                    first = first.next;
                    nth = nth.next;
                }
            }
            return nth;
        }

        static Node makeList(int[] values) {
            Node head = null;
            Node node = null;
            for (int value : values) {
                final Node newNode = new Node(value);
                if (head == null) {
                    head = node = newNode;
                } else {
                    node = node.next = newNode;
                }
            }
            return head;
        }

    }
}
