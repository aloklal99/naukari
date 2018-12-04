class Solution(object):
    def __init__(self):
        self.c = {}

    def numTrees(self, n):
        """
        :type n: int
        :rtype: int
        """
        cache = {}

        def _calc(n):
            if n not in cache:
                if n == 0 or n == 1:
                    return 1
                result = 0
                for i in range(n):
                    j = n - 1 - i
                    result += _calc(i) * _calc(n - 1 - i)
                cache[n] = result
            return cache[n]

        return _calc(n)
