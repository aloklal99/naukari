import queue


class MedianFinder:
    def __init__(self):
        """
        initialize your data structure here.
        """
        self.left = queue.PriorityQueue()
        self.right = queue.PriorityQueue()

    def addNum(self, num):
        """
        :type num: int
        :rtype: void
        """
        # because we want a max heap we need to -ve the numbers before adding
        self.left.put(item=-num)
        # Since the number comes from max heap, we have to restore its sign with a -ve
        self.right.put(item=-self.left.get())
        # Same reason to invert the sign again
        if self.right.qsize() > self.left.qsize():
            self.left.put(item=-self.right.get())

    def findMedian(self):
        """
        :rtype: float
        """
        if self.left.qsize() > self.right.qsize():
            return -self.left.queue[0]
        else: # equal size
            if self.left.qsize():
                return (-self.left.queue[0] + self.right.queue[0])/2 
            else:
                None