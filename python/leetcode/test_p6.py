import unittest

from p5 import Solution

class TestP5(unittest.TestCase):
    def test(self):
        data = [
            ("a", 1),
            ("aa", 2),
            ("aab", 2),
            ("baa", 2),
            ("aaba", 3),
            ("aabaa", 5),
            ("caabaa", 5),
            ("aabaac", 5),
            ("babba", 4),
        ]
        sol = Solution()
        for i, (s, expected) in enumerate(data):
            print(f"s={s}, expected={expected}")
            actual = sol.longestPalindrome(s)
            print(f"\tactual={actual}")
            self.assertEqual(actual, expected)
            if i == 4:
                break