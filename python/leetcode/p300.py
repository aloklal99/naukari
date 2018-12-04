import queue
class Node:
    def __init__(self, val, index):
        self._val = val
        self._index = index
        self._children = queue.PriorityQueue()

    def __lt__(self, other):
        return self._val < other._val

    def depth(self):
        depth = max((node.depth() for node in self._children.queue), default=0)
        if self._val is not None: # not root node
            depth += 1
        return depth
        
class Solution1:
    """
    This one does not work with an array that's 2500 long with no gaps!
    """
    def insert(self, node, num, i):
        # print(f"insert(num={num}, i={i}, node={node})")
        if node._children.empty() or num < node._children.queue[0]._val:
            node._children.put(Node(val=num, index=i))
        else:
            for child in [child for child in node._children.queue if child._val < num]:
                self.insert(child, num, i)
            
    def lengthOfLIS(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        root = Node(val=None, index=None)
        for i, num in enumerate(nums):
            self.insert(root, num, i)
        return root.depth()


class Solution:
    import functools

    global maxSoFar
    global nums

    @functools.lru_cache(3000)
    def _lis(self, i):
        """
        Length of longest increasing ending at i
        :param nums: input array
        :param i: index into the input array
        :return:
        """
        global maxSoFar
        global nums

        if i == 0:
            iLis = 1
        else:
            iLis = 0
            for j in range(i):
                jLis = self._lis(j)
                if nums[j] < nums[i]:
                    iLisAtJ = jLis + 1
                else:
                    iLisAtJ = 1
                if iLisAtJ > iLis:
                    iLis = iLisAtJ
        print(f"_list: i: {i}, num: {nums[i]}, iLis: {iLis}")
        if maxSoFar < iLis:
            maxSoFar = iLis
        return iLis


    def lengthOfLIS(self, nums_):
        """
        :type nums: List[int]
        :rtype: int
        """
        global maxSoFar
        global nums

        maxSoFar = 0
        nums = nums_
        print(f"nums: {nums}")
        self._lis(len(nums) - 1)
        return maxSoFar