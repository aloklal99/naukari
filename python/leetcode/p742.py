class Solution:
    def pivotIndex_(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        left = None
        right = None
        for pivot in range(0, len(nums)):
            if left:
                left += nums[pivot-1]
            else:
                left = sum(nums[:pivot])
            if right:
                right -= nums[pivot]
            else:
                right = sum(nums[pivot+1:])
            if left == right:
                return pivot
        return -1
    def pivotIndex(self, nums):
        left = 0
        s = sum(nums)
        for i, num in enumerate(nums):
            if s - num == left:
                return i
            left += num
        return -1