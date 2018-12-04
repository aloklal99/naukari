class Solution:
    def sortArrayByParityII(self, A):
        """
        :type A: List[int]
        :rtype: List[int]
        """

        def even():
            for i in range(0, len(A), 2):
                if A[i] % 2:
                    yield i

        def odd():
            for i in range(1, len(A), 2):
                if A[i] % 2 == 0:
                    yield i

        def swap(i, j):
            A[i], A[j] = A[j], A[i]

        # this list is needed to make sure that map get materialized!
        list(map(swap, even(), odd()))
        return A