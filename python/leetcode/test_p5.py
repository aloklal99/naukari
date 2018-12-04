import unittest
from unittest import TestCase

from p5 import Solution


class TestP5(unittest.TestCase):
    def test(self):
        data = [
            ("a", "a"),
            ("aa", "aa"),
            ("aab", "aa"),
            ("baa", "aa"),
            ("aaba", "aba"),
            ("aabaa", "aabaa"),
            ("caabaa", "aabaa"),
            ("aabaac", "aabaa"),
            ("babba", "abba"),
        ]
        sol = Solution()
        for i, (s, expected) in enumerate(data):
            lp = sol.longestPalindrome(s)
            self.assertEqual(lp, expected, f"input: {s}, expected: {expected}")