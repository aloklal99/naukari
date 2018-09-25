# Definition for singly-linked list.
class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None

    @staticmethod
    def makeList(*args):
        head, tail = None, None
        for val in args:
            head, tail = append(head, tail, val)
        return head

    def __str__(self):
        head = self
        elements = []
        while head:
            elements.append(head.val)
            head = head.next
        return "->".join(str(element) for element in elements)

def append(head, tail, val):
    """
    Append head to tail of provided list and return updated value of head and tail
    :type head: ListNode
    :type tail: ListNode
    :type node: int
    :rtype: tuple(ListNode, ListNode)
    """
    node = ListNode(val)
    if head:
        tail.next = node
        tail = node
    else:
        head = node
        tail = head
    return head, tail

class Solution1:
    def addTwoNumbers(self, l1, l2):
        """
        :type l1: ListNode
        :type l2: ListNode
        :rtype: ListNode
        """
        head, tail = None, None
        carry = 0
        while l1 and l2:
            val, carry = self.add_(l1.val, l2.val, carry)
            head, tail = append(head, tail, val)
            l1 = l1.next
            l2 = l2.next
        # either l1 is None or l2 or both
        remaining = l1 if l1 else l2
        # copy the remainder of list
        while remaining:
            val, carry = self.add_(first=remaining.val, carry=carry)
            head, tail = append(head, tail, val)
            remaining = remaining.next
        if carry:
            head, tail = append(head, tail, carry)
            carry = 0
        return head

    def add_(self, first, second=0, carry=0):
        sum_ = sum([first, second, carry])
        carry = int(sum_ / 10)
        val = sum_ % 10
        return val, carry


class Solution:
    @staticmethod
    def getNumber(lst):
        digits = []
        while lst:
            digits.append(lst.val)
            lst = lst.next
        return int(''.join(str(digit) for digit in reversed(digits)))

    @staticmethod
    def getList(val):
        head, tail = None, None
        valStr = str(val)
        for digit in reversed(valStr):
            head, tail = Solution.append(head, tail, digit)
        return head

    @staticmethod
    def append(head, tail, val):
        """
        Append head to tail of provided list and return updated value of head and tail
        :type head: ListNode
        :type tail: ListNode
        :type node: int
        :rtype: tuple(ListNode, ListNode)
        """
        node = ListNode(val)
        if head:
            tail.next = node
            tail = node
        else:
            head = node
            tail = head
        return head, tail

    def addTwoNumbers(self, l1, l2):
        first = Solution.getNumber(l1)
        second = Solution.getNumber(l2)
        sum_ = first + second
        return Solution.getList(sum_)


l1 = ListNode.makeList(2, 4, 3)
print(l1)
l2 = ListNode.makeList(5, 6, 4)
print(l2)
p2 = Solution()
lsum = p2.addTwoNumbers(l1, l2)
print(lsum)
