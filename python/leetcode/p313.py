class Solution:
    def nthSuperUglyNumber(self, n, primes):
        """
        :type n: int
        :type primes: List[int]
        :rtype: int
        """
        u = [1]
        indices = [0] * len(primes)
        values = list(primes)
        while len(u) < n:
            ugly = min(values)
            u.append(ugly)
            for i, prime in enumerate(primes):
                if ugly == values[i]:
                    indices[i] += 1
                    values[i] = u[indices[i]] * prime
        return u[~0]