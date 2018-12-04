# Definition for singly-linked list.
# class ListNode(object):
#     def __init__(self, x):
#         self.val = x
#         self.next = None

class Solution(object):
    def removeNthFromEnd(self, head, n):
        """
        :type head: ListNode
        :type n: int
        :rtype: ListNode
        """
        if head:
            i = 1
            target = None
            node = head
            while node:
                node = node.next
                if target:
                    target = target.next
                if i == n + 1:
                    target = head
                i += 1
            if target:
                target.next = target.next.next
            else:
                head = head.next
        return head
