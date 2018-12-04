class Node(object):
    def __init__(self, key):
        self.key = key
        self.next = None
        self.prev = None

    def __repr__(self):
        return "[{prev} <- [{key}] -> {next}".format(key=self.key,
                                             next=self.next.key if self.next else None,
                                             prev=self.prev.key if self.prev else None)


class LinkedList(object):
    def __init__(self, node = None):
        self.head = node
        self.tail = node

    def append(self, node):
        node.next = None
        if self.head:
            node.prev = self.tail
            self.tail.next = node
            self.tail = node
        else:
            self.head = self.tail = node

    def pop(self):
        if not self.head:
            raise IndexError("Head is None!")
        oldHead = self.head
        self.head = oldHead.next
        if self.head:
            self.head.prev = None
        else:
            # if we ended up removing the only node in the list!
            self.tail = None

        oldHead.next = None
        return oldHead

    def remove(self, node):
        if not self.head:
            raise IndexError("Head is None!")
        # also works if the only node is being removed
        # It'll set both head and tail to empty
        if node.prev:
            node.prev.next = node.next
        else:
            # we're removing head! It's the same as pop
            self.head = self.head.next
            if self.head:
                self.head.prev = None

        if node.next:
            node.next.prev = node.prev
        else:
            # we're removing tail!
            self.tail = self.tail.prev
            if self.tail:
                self.tail.next = None

    def __repr__(self):
        keys = []
        node = self.head
        while node:
            keys.append(str(node.key))
            node = node.next

        reversed_keys = []
        node = self.tail
        while node:
            reversed_keys.append(str(node.key))
            node = node.prev
        return "{fwd}\n{back}\n{match}".format(fwd=' -> '.join(keys),
                                               back=' -> '.join(reversed_keys),
                                               match=list(reversed(keys)) == reversed_keys)


class LRU:
    def __init__(self):
        self.dict = {}
        self.linkedList = LinkedList()

    def accessed(self, key):
        node = self.dict.get(key)
        if node:
            self.linkedList.remove(node)
        else:
            node = Node(key)
            self.dict[key] = node
        self.linkedList.append(node)

    def expireOldest(self):
        node = self.linkedList.pop()
        if node.key in self.dict:
            del self.dict[node.key]
        return node.key


class LRUCache:

    def __init__(self, capacity):
        """
        :type capacity: int
        """
        self._capacity = capacity
        self.main = {}
        self.lru = LRU()

    def get(self, key):
        """
        :type key: int
        :rtype: int
        """
        if key in self.main:
            self.lru.accessed(key)
            return self.main[key]
        else:
            return -1

    def put(self, key, value):
        """
        :type key: int
        :type value: int
        :rtype: void
        """
        if key in self.main:
            self.main[key] = value
        else:
            if len(self.main) < self._capacity:
                self.main[key] = value
            else:
                # make room
                poppedKey = self.lru.expireOldest()
                del self.main[poppedKey]
                # add new entry
                self.main[key] = value
        self.lru.accessed(key)

# Your LRUCache object will be instantiated and called as such:
# obj = LRUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)