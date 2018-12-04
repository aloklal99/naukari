class Solution:
    def threeSum(self, nums):
        """
        :type nums: List[int]
        :rtype: List[List[int]]
        """
        nums.sort()
        result = []
        for ai, a in enumerate(nums):
            if ai > 0 and nums[ai] == nums[ai-1]:
                continue
            bi, ci = ai+1, len(nums)-1
            while bi < ci:
                t = sum([nums[ai], nums[bi], nums[ci]])
                if t < 0:
                    bi += 1
                elif t > 0:
                    ci -= 1
                else:
                    # print(ai, bi, ci)
                    result.append([nums[ai], nums[bi], nums[ci]])
                    # print(result[~0])
                    while bi < ci and nums[bi] == nums[bi+1]:
                        bi += 1
                    bi += 1
                    while ci > bi and nums[ci] == nums[ci-1]:
                        ci -= 1
                    ci -= 1
        return result