# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, x):
#         self.val = x
#         self.next = None

import random


class Solution1:

    def __init__(self, head):
        """
        @param head The linked list's head.
        Note that the head is guaranteed to be not null, so it contains at least one node.
        :type head: ListNode
        """
        self.head = head
        node, i = head, 0
        while node:
            i += 1
            node = node.next
        self.len = i

    def getRandom(self):
        """
        Returns a random node's value.
        :rtype: int
        """
        node = self.head
        i = random.randint(1, self.len)
        while i > 1:
            node = node.next
            i -= 1
        return node.val

# Your Solution object will be instantiated and called as such:
# obj = Solution(head)
# param_1 = obj.getRandom()


# Definition for singly-linked list.
class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None

import random

class Solution:

    def __init__(self, head):
        """
        @param head The linked list's head.
        Note that the head is guaranteed to be not null, so it contains at least one node.
        :type head: ListNode
        """
        self.head = head

    def getRandom(self):
        """
        Returns a random node's value.
        :rtype: int
        """
        node = self.head
        p = 1
        count = 1
        val = node.val
        while node:
            count += 1
            p = 1/count
            if random.uniform(0, 1) < p:
                val = node.val
            node = node.next
        return val

# Your Solution object will be instantiated and called as such:
# obj = Solution(head)
# param_1 = obj.getRandom()