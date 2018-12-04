from unittest import TestCase
from p20 import Solution

class TestSolution(TestCase):
    def setUp(self):
        self.s = Solution()

    def test_isValid(self):
        for input in ["[}", ")(", "({)}"]:
            result = self.s.isValid(input)
            print(f"{input} => {result}")

        for input in [
            "()",
            "()[]",
        ]:
            result = self.s.isValid(input)
            print(f"{input} => {result}")
