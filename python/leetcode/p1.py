class Solution1:
    def twoSum(self, nums, target):
        """
        :type nums: List[int]
        :type target: int
        :rtype: List[int]
        """
        for i, first in enumerate(nums):
            remainder = target - first
            for j, second in enumerate(nums[i+1:], i+1):
                if second == remainder:
                    return [i, j]


class Solution2:
    def twoSum(self, nums, target):
        """
        :type nums: List[int]
        :type target: int
        :rtype: List[int]
        """
        remainders = {}
        for i, value in enumerate(nums):
            remainders[value] = i

        for i, first in enumerate(nums):
            second = target - first
            if second in remainders:
                j = remainders[second]
                if j != i:
                    return [i, remainders[second]]
            remainders[first] = i


class Solution:
    def twoSum(self, nums, target):
        """
        :type nums: List[int]
        :type target: int
        :rtype: List[int]
        """
        remainders = {}
        for i, first in enumerate(nums):
            second = target - first
            if second in remainders:
                j = remainders[second]
                if j != i:
                    return [j, i]
            remainders[first] = i

s = Solution()
result = s.twoSum([3, 2, 4], 6)
print(result)