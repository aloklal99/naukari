# Definition for singly-linked list with a random pointer.

class RandomListNode(object):
    def __init__(self, x):
        self.label = x
        self.next = None
        self.random = None

    def __repr__(self):
        head = self
        s = []
        while head:
            s.append("({n}, r:{r})".format(n = head.label, r=head.random.label if head.random else None))
            head = head.next
        return "->".join(s)

class Solution1(object):
    def copyRandomList(self, head):
        """
        :type head: RandomListNode
        :rtype: RandomListNode
        """
        copyHead = copy = None
        i = 0
        original = {}
        new = {}
        while head:
            print("node: %s, random: %s" % (head.label, head.random.label if head.random else None))
            node = RandomListNode(head.label)
            if copyHead:
                copyHead.next = node
            else:
                copy = copyHead = node
            original[head] = (i, head.random)
            new[i] = node
            i += 1
            head = head.next
            copyHead = node

        i2randi = {idx: original[randNode][0] for idx, randNode in original.values() if randNode}
        print(i2randi)
        newRandom = {idx: new[randIdx] for idx, randIdx in i2randi.items()}
        print({idx: newRandom[idx].label for idx in newRandom.keys()})

        print("---")
        head = copy
        i = 0
        while head:
            if i in newRandom:
                head.random = newRandom[i]
            print("node: %s, random: %s" % (head.label, head.random.label if head.random else None))
            i += 1
            head = head.next

        return copy

class Solution(object):
    def copyRandomList(self, head):
        nodes = {}
        copy = copyHead = None
        while head:
            node = nodes[id(head)] if id(head) in nodes else RandomListNode(head.label)
            nodes[id(head)] = node
            if head.random:
                rNode = nodes[id(head.random)] if id(head.random) in nodes else RandomListNode(head.random.label)
                node.random = rNode
                nodes[id(head.random)] = rNode
            if copyHead:
                copyHead.next = node
            else:
                copy = copyHead = node
            head = head.next
            copyHead = node
        return copy
