class Solution:
    def arrayNesting(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        loops = []
        visited = set()
        for start in nums:
            if start not in visited:
                i = start
                current = set()
                while (i not in current):
                    current.add(i)
                    visited.add(i)
                    i = nums[i]
                print("current", current)
                print("visited", visited)
                loops.append(current)
        return max(map(len, loops))