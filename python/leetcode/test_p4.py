from unittest import TestCase
from p4 import Solution

class TestSolution(TestCase):
    def setUp(self):
        self.s = Solution()

    def test_findMedianSortedArrays(self):
        nums1 = [1, 3]
        nums2 = [2]
        result = self.s.findMedianSortedArrays(nums1=nums1, nums2=nums2)
        print(f"result: {result}")
