import unittest
from p565 import Solution


class TestPy565(unittest.TestCase):
    def testAppend(self):
        s = Solution()
        nums = [5,4,0,3,1,6,2]
        s.arrayNesting(nums)

if __name__ == '__main__':
    unittest.main()

