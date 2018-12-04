class Solution:
    def sortArrayByParity(self, A):
        """
        :type A: List[int]
        :rtype: List[int]
        """
        firstOdd = 0          # 1st _possible_ odd
        lastEven = len(A) - 1 # last _possible_ even
        while True:
            while firstOdd < len(A) and A[firstOdd] % 2 == 0:
                firstOdd += 1
            while lastEven >=0 and A[lastEven] % 2 == 1:
                lastEven -= 1
            if firstOdd < lastEven:
                A[firstOdd], A[lastEven] = A[lastEven], A[firstOdd]
            else:
                break
        return A
