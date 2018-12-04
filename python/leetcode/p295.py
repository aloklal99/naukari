import operator
import collections

class Heap:
    def __init__(self):
        self._store = [None]  # index 0 is not used

    def __len__(self):
        return len(self._store) - 1 # 0th element isn't used

    def _isCorrectlyOrdered(self, parent, child):
        raise Exception("Child class must override this method!")

    def _pickSwappableChild(self, childrenTs):
        raise Exception("Child class must override this method!")

    def peekRoot(self):
        return self._store[1]

    def _swap(self, idx1, idx2):
        self._store[idx1], self._store[idx2] = self._store[idx2], self._store[idx1]

    def _getParent(self, child):
        return int(child/2)

    def _bubbleUp(self, child):
        parent = self._getParent(child)
        # last child is at @2 so we want to stop looking for parent when child is at 1 (head)
        while child > 1 and (not self._isCorrectlyOrdered(parent, child)):
            self._swap(parent, child)
            child = parent
            parent = self._getParent(child)

    def _percolateDown(self, parent):
        print(f"_percolateDown(parent={parent})")
        done = False
        while not done:
            children = self._getSwappableChild(parent)
            child = self._pickSwappableChild(children)
            print(f"_percolateDown \t child: {child}")
            if child:
                self._swap(parent, child)
                parent = child
            else:
                done = True

    def add(self, val):
        self._store.append(val)
        self._bubbleUp(len(self._store) - 1)

    def removeTop(self):
        if len(self._store) == 2:
            return self._store.pop()
        else:
            top = self._store[1]
            last = self._store.pop()
            self._store[1] = last
            self._percolateDown(1)
            return top

    def _getChildren(self, parent):
        return [2*parent+i for i in [0, 1] if 2*parent+i < len(self._store)]
    
    
    def _getSwappableChild(self, parent):
        return (child for child in self._getChildren(parent) if not self._isCorrectlyOrdered(parent, child))


class MinHeap(Heap):
    def _isCorrectlyOrdered(self, parent, child):
        return False if self._store[parent] > self._store[child] else True

    def _pickSwappableChild(self, children):
        return min(children, key=lambda x: self._store[x], default=None)


class MaxHeap(Heap):
    def _isCorrectlyOrdered(self, parent, child):
        return False if self._store[parent] < self._store[child] else True

    def _pickSwappableChild(self, children):
        return max(children, key=lambda x: self._store[x], default=None)


class MedianFinder0:

    def __init__(self):
        """
        initialize your data structure here.
        """
        self.rightHeap = MinHeap()
        self.leftHeap = MaxHeap()

    def addNum(self, num):
        """
        :type num: int
        :rtype: void
        """
        if len(self.leftHeap) and len(self.rightHeap):
            if num >= self.rightHeap.peekRoot():
                if len(self.rightHeap) <= len(self.leftHeap):
                    self.rightHeap.add(num)
                else: # right heap is already larger
                    self.leftHeap.add(self.rightHeap.removeTop())
                    self.rightHeap.add(num)
            else:
                if len(self.leftHeap) <= len(self.rightHeap):
                    self.leftHeap.add(num)
                else: # left heap is already larger
                    if num > self.leftHeap.peekRoot():
                        self.rightHeap.add(num)
                    else:
                        self.rightHeap.add(self.leftHeap.removeTop())
                        self.leftHeap.add(num)
        elif len(self.leftHeap):
            if num > self.leftHeap.peekRoot():
                self.rightHeap.add(num)
            else:
                self.rightHeap.add(self.leftHeap.removeTop())
                self.leftHeap.add(num)
        elif len(self.rightHeap) > 0:
            if num <= self.rightHeap.peekRoot():
                self.leftHeap.add(num)
            else:
                self.leftHeap.add(self.rightHeap.removeTop())
                self.rightHeap.add(num)
        else: # both heaps are empty!
            self.leftHeap.add(num)

    def findMedian(self):
        """
        :rtype: float
        """
        leftL = len(self.leftHeap)
        rightL = len(self.rightHeap)
        if leftL == rightL:
            if leftL:
                return (self.rightHeap.peekRoot() + self.leftHeap.peekRoot())/2
            else: # both left and right heaps are empty!
                return None
        elif leftL > rightL:
            return self.leftHeap.peekRoot()
        else:
            return self.rightHeap.peekRoot()

# Your MedianFinder object will be instantiated and called as such:
# obj = MedianFinder()
# obj.addNum(num)
# param_2 = obj.findMedian()

