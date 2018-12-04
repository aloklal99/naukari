rows = None
cols = None


class Solution:
    def __init__(self):
        self.rows = None
        self.rows = None

    def reverseRow(self, row):
        start = 0
        end = self.cols - 1
        while start < end:
            row[start], row[end] = row[end], row[start]
            start += 1
            end -= 1

    def invertRow(self, row):
        for i in range(self.cols):
            if row[i]:
                row[i] = 0
            else:
                row[i] = 1

    def flipAndInvertImage(self, A):
        """
        :type A: List[List[int]]
        :rtype: List[List[int]]
        """
        self.rows = len(A)
        self.cols = len(A[0])
        for row in A:
            self.reverseRow(row)
            self.invertRow(row)
        return A