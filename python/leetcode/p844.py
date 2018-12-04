class Solution(object):
    def reduce(self, s):
        a = []
        for c in s:
            if c == '#':
                if a:
                    a.pop()
                else:
                    pass  # we never add # into the stack
            else:
                a.append(c)
        return ''.join(a)

    def backspaceCompare(self, S, T):
        """
        :type S: str
        :type T: str
        :rtype: bool
        """
        return self.reduce(S) == self.reduce(T)