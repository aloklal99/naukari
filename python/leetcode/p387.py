class Solution:
    def firstUniqChar(self, s):
        """
        :type s: str
        :rtype: int
        """
        m = {}
        for idx, c in enumerate(s):
            if c in m:
                m[c] = None
            else:
                m[c] = (1, idx)
        singles = [val[1] for val in m.values() if val]
        return -1 if not singles else min(singles)
