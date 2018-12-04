import unittest
import sys
sys.path.append(".")

from py146 import LinkedList, Node, LRU, LRUCache


class TestPy146(unittest.TestCase):
    def testAppend(self):
        dbl = LinkedList()
        dbl.append(Node(1))
        dbl.append(Node(2))
        print(dbl)

        dbl.pop()
        print(dbl)

        dbl.pop()
        print(dbl)

        first = Node(3)
        second = Node(4)
        dbl.append(second)
        dbl.append(first)
        print(dbl)

        dbl.remove(second)
        print(dbl)
        dbl.append(second)
        print(dbl)

    def testLRU(self):
        lru = LRU()
        self.assertIsNone(lru.linkedList.head)
        self.assertIsNone(lru.linkedList.tail)

        key1 = "foo"
        lru.accessed(key1)
        self.assertIn(key1, lru.dict)
        self.assertIsNotNone(lru.linkedList.head)
        self.assertEqual(key1, lru.linkedList.head.key)

        self.assertIsNotNone(lru.linkedList.tail)
        self.assertTrue(lru.linkedList.head is lru.linkedList.tail)

        key2 = "bar"
        lru.accessed(key2)
        self.assertIn(key2, lru.dict)
        self.assertEqual(key1, lru.linkedList.head.key)
        self.assertEqual(key2, lru.linkedList.tail.key)

        poppedKey = lru.expireOldest()
        self.assertEqual(poppedKey, key1)
        self.assertNotIn(key1, lru.dict)
        self.assertIn(key2, lru.dict)
        self.assertTrue(lru.linkedList.head is lru.linkedList.tail)

        key3 = "baz"
        lru.accessed(key3)
        lru.accessed(key2)
        lru.accessed(key1)
        self.assertIn(key1, lru.dict)
        self.assertIn(key2, lru.dict)
        self.assertIn(key3, lru.dict)
        self.assertEqual(key3, lru.linkedList.head.key)
        self.assertEqual(key2, lru.linkedList.head.next.key)
        self.assertEqual(key1, lru.linkedList.tail.key)
        self.assertEqual(key2, lru.linkedList.tail.prev.key)

    def testFailing(self):
        actions = ["LRUCache", "put", "put", "put", "put", "put", "get", "put", "get", "get", "put", "get", "put", "put", "put",
         "get", "put", "get", "get", "get", "get", "put", "put", "get", "get", "get", "put", "put", "get", "put", "get",
         "put", "get", "get", "get", "put", "put", "put", "get", "put", "get", "get", "put", "put", "get", "put", "put",
         "put", "put", "get", "put", "put", "get", "put", "put", "get", "put", "put", "put", "put", "put", "get", "put",
         "put", "get", "put", "get", "get", "get", "put", "get", "get", "put", "put", "put", "put", "get", "put", "put",
         "put", "put", "get", "get", "get", "put", "put", "put", "get", "put", "put", "put", "get", "put", "put", "put",
         "get", "get", "get", "put", "put", "put", "put", "get", "put", "put", "put", "put", "put", "put", "put"]
        arguments = [[10], [10, 13], [3, 17], [6, 11], [10, 5], [9, 10], [13], [2, 19], [2], [3], [5, 25], [8], [9, 22], [5, 5],
         [1, 30], [11], [9, 12], [7], [5], [8], [9], [4, 30], [9, 3], [9], [10], [10], [6, 14], [3, 1], [3], [10, 11],
         [8], [2, 14], [1], [5], [4], [11, 4], [12, 24], [5, 18], [13], [7, 23], [8], [12], [3, 27], [2, 12], [5],
         [2, 9], [13, 4], [8, 18], [1, 7], [6], [9, 29], [8, 21], [5], [6, 30], [1, 12], [10], [4, 15], [7, 22],
         [11, 26], [8, 17], [9, 29], [5], [3, 4], [11, 30], [12], [4, 29], [3], [9], [6], [3, 4], [1], [10], [3, 29],
         [10, 28], [1, 20], [11, 13], [3], [3, 12], [3, 8], [10, 9], [3, 26], [8], [7], [5], [13, 17], [2, 27],
         [11, 15], [12], [9, 19], [2, 15], [3, 16], [1], [12, 17], [9, 1], [6, 19], [4], [5], [5], [8, 1], [11, 7],
         [5, 2], [9, 28], [1], [2, 2], [7, 4], [4, 22], [7, 24], [9, 26], [13, 28], [11, 26]]

        self.assertEqual(len(actions), len(arguments))
        print("\n")
        for i in range(len(actions)):
            action = actions[i]
            args = arguments[i]
            if action == "LRUCache":
                cache = LRUCache(*args)
                result = None
            elif action == "put":
                result = cache.put(*args)
            elif action == "get":
                result = cache.get(*args)
            print(f"\n{i}:{action}({args}) = {result}")
            print(cache.lru.linkedList)


if __name__ == '__main__':
    unittest.main()