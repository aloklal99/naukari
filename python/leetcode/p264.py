class Solution:
    def nthUglyNumber(self, n):
        """
        :type n: int
        :rtype: int
        """
        u = [1]
        i2, i3, i5 = 0, 0, 0
        u2, u3, u5 = u[i2]*2, u[i3]*3, u[i5]*5
        u2, u3, u5 = 2, 3, 5
        while len(u) < n:
            ugly = min(u2, u3, u5)
            u.append(ugly)
            if ugly == u2:
                i2 += 1
                u2 = u[i2] * 2
            if ugly == u3:
                i3 += 1
                u3 = u[i3] * 3
            if ugly == u5:
                i5 += 1
                u5 = u[i5] * 5
        return u[~0]