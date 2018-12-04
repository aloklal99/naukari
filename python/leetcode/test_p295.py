import unittest
import p295, p295_2

class TestLC295(unittest.TestCase):
    def setUp(self):
        self.minHeap = p295.MinHeap()
        self.maxHeap = p295.MaxHeap()
        self.medianFinder0 = p295.MedianFinder0()
        self.medianFinder = p295_2.MedianFinder()

    def testMinHeap(self):
        self.minHeap.add(10)
        self.assertEqual(self.minHeap.peekRoot(), 10)
        self.minHeap.add(11)
        print(self.minHeap._store)
        val = self.minHeap.removeTop()
        print(self.minHeap._store)
        self.assertEqual(val, 10)
        self.assertEqual(self.minHeap.removeTop(), 11)

    def testMinHeap2(self):
        values = [10, 3, 8, 11, 0, 18, -5]
        for val in values:
            self.minHeap.add(val)
            print(self.minHeap._store)
        heap = [self.minHeap.removeTop() for _ in range(len(values))]
        print(heap)
        self.assertEqual(sorted(values), heap)

        for val in values:
            self.maxHeap.add(val)
            print(self.maxHeap._store)
        heap = [self.maxHeap.removeTop() for _ in range(len(values))]
        print(heap)
        self.assertEqual(list(reversed(sorted(values))), heap)

    
# ["MedianFinder","addNum","addNum","findMedian","addNum","findMedian"]
# [[],[1],[2],[],[3],[]]
    
    def testMinHeap3(self):
        self.minHeap.add(1)
        self.minHeap.add(2)
        print(f"store: {self.minHeap._store}")
        result = self.minHeap.removeTop()
        print(f"popped item: {result}")

        self.minHeap.add(3)
        result = self.minHeap.removeTop()
        print(f"popped item: {result}")

    def testStreamMean(self):
        self.medianFinder0.addNum(1)
        self.medianFinder0.addNum(2)
        result = self.medianFinder0.findMedian()
        print(f"result: {result}")
        self.medianFinder0.addNum(3)
        print(f"result: {result}")

    def testStreamMean2(self):
        self.medianFinder.addNum(1)
        self.medianFinder.addNum(2)
        result = self.medianFinder.findMedian()
        print(f"result: {result}")
        self.assertEqual(1.5, result, f"expected 1.5, result {result}")
        self.medianFinder.addNum(3)
        print(f"result: {result}")
        self.assertEqual(1.5, result, f"expected 1.5, result {result}")


if __name__ == '__main__':
    unittest.main()