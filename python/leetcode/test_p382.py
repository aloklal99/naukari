from unittest import TestCase
from p382 import Solution, ListNode
import collections


class TestSolution(TestCase):
    def test_getRandom(self):
        arr1 = [10, 20, 20, 30, 30, 30]
        arr2 = [30, 20, 20, 30, 30, 10]
        for lst in [arr1, arr2]:
            head = self.makeList(lst)
            print(self.printList(head))
            s = Solution(head)
        m = collections.defaultdict(int)
        for i in range(5000):
            m[s.getRandom()] += 1
        print(m)

    def makeList(self, nums):
        head = node = None
        for num in nums:
            new = ListNode(num)
            if not head:
                head = new
                node = head
            else:
                node.next = new
                node = new
        return head

    def printList(self, head):
        nums = []
        while head:
            nums.append(head.val)
            head = head.next
        return '->'.join(map(str, nums))