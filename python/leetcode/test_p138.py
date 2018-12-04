from unittest import TestCase
from p138 import RandomListNode, Solution

class TestSolution(TestCase):
    def setUp(self):
        self.s = Solution()

    def test_copyRandomList(self):
        head = RandomListNode(1)
        head.next = RandomListNode(2)
        head.random = head.next
        head.next.random = head.next
        print()
        print(head)
        self.s = Solution()
        copy = self.s.copyRandomList(head)
        print(copy)

    def test_copy2(self):
        one = RandomListNode(1)
        two = RandomListNode(2)
        three = RandomListNode(3)
        four = RandomListNode(4)
        one.next = two
        two.next = three
        three.next = four
        one.random = three
        two.random = three
        head = one
        print()
        print(head)
        copy = self.s.copyRandomList(head)
        print(copy)