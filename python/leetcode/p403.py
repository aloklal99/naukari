import collections

class Solution:
    def __init__(self):
        self.reset()
        
    def reset(self):
        self.stones = None
        self.set = None
        self.last = None
        self.memo = collections.defaultdict(dict)
        
    def canJumpTo(self, to):
        # print(f"canJumpTo(to={to})")
        if not self.set:
            self.set = set(self.stones)
            self.last = self.stones[~0]
        if to > self.last:
            result = False
        else:
            result = to in self.set
        # print(f"canJumpTo: {result}")
        return result
    
    def canReachLast(self, i, k):
        # print(f"canReachLast(i={i}, k={k})")
        if k not in self.memo[i]:
            if i == 0:
                steps = [1]
            else:
                steps = [k + step for step in [-1, 0, 1] if k + step > 0]
            if any(i+step == self.last for step in steps):
                result = True
            else:
                result = any(self.canJumpTo(i+step) and self.canReachLast(i+step, step) for step in steps)
            self.memo[i][k] = result
        result = self.memo[i][k]
        # print(f"canReachLast: {result}")
        return result
        
    def canCross(self, stones):
        """
        :type stones: List[int]
        :rtype: bool
        """
        self.reset()
        self.stones = stones
        assert stones[0] == 0
        self.last = self.stones[~0]
        return self.canReachLast(i=0, k=1)