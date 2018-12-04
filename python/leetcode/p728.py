import operator
import functools

class Solution:
    def selfDividingNumbers(self, left, right):
        """
        :type left: int
        :type right: int
        :rtype: List[int]
        """
        def checkNumber(number):
            for d in str(number):
                if d == '0' or number % int(d) != 0:
                    return False
            return True

        result = []
        for number in range(left, right + 1):
            if checkNumber(number):
                result.append(number)
        return result
