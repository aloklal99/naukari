class Solution(object):
    def moveZeroes(self, nums):
        """
        :type nums: List[int]
        :rtype: void Do not return anything, modify nums in-place instead.
        """
        numZeros = 0
        i = 0
        while i < len(nums):
            if nums[i] == 0:
                # find sequence of zeros
                j = i + 1
                while j < len(nums) and nums[j] == 0:
                    j += 1
                numZeros += j - i
                # move everything left by j-i steps till hitting next zero
                k = j
                while k < len(nums) and nums[k] != 0:
                    nums[k-numZeros] = nums[k]
                    k += 1
                i = k
            else:
                i += 1
        for i in range(numZeros):
            nums[~i] = 0

    def moveZeroes_slow(self, nums):
        numZeros = 0
        for i in range(len(nums)):
            if nums[~i] == 0:
                for j in range(i, numZeros, -1):
                    nums[~j] = nums[~(j-1)]
                nums[~numZeros] = 0
                numZeros += 1
