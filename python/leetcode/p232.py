class MyQueue:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.s1 = []
        self.s2 = []
    
    def _transfer(self, s1, s2):
        """
        Transfer all ements from s1 to s2
        """
        while s1:
            s2.append(s1.pop())

    def push(self, x):
        """
        Push element x to the back of queue.
        :type x: int
        :rtype: void
        """
        if self.s2:
            self._transfer(self.s2, self.s1)
        self.s1.append(x)

    def pop(self):
        """
        Removes the element from in front of queue and returns that element.
        :rtype: int
        """
        if self.s1:
            self._transfer(self.s1, self.s2)
        return self.s2.pop()

    def peek(self):
        """
        Get the front element.
        :rtype: int
        """
        if self.s1:
            self._transfer(self.s1, self.s2)
        return self.s2[~0]

    def empty(self):
        """
        Returns whether the queue is empty.
        :rtype: bool
        """
        return len(self.s1) == 0 and len(self.s2) == 0


# Your MyQueue object will be instantiated and called as such:
# obj = MyQueue()
# obj.push(x)
# param_2 = obj.pop()
# param_3 = obj.peek()
# param_4 = obj.empty()