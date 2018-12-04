class Solution(object):
    def isPalindrome(self, n):
        nStr = str(n)
        for i in range(int(len(nStr) / 2)):
            if nStr[i] != nStr[~i]:
                return False
        return True

    def getNextHigherPalindrome(self, a, b, current):
        """
        The minute we find a palindrome (a*b) composed of two digits a and b, we now know that the only
        compititor to it, i.e. larger in value than a*b must be a number such that it's a product of
        two numbers x and y where: a < x < b AND a < y < b, if at all.
        So we should start our loops to count down from one number short of a and be respectively
        """
        for x in range(a - 1, b, -1):
            for y in range(x, b, -1):
                product = x * y
                #                 print(f"{x} x {y} = {product}")
                if self.isPalindrome(product) and product > current:
                    return (x, y, product)
        return None

    def largestPalindrome(self, n):
        a = 10 ** n
        b = 1
        current = 1
        while True:
            better = self.getNextHigherPalindrome(a, b, current)
            if not better:
                break
            else:
                (a, b, current) = better
        return current % 1337