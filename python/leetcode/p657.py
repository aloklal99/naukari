import functools

m = {'L': 1, 'R': -1, 'U': 1, 'D': -1}
class Solution:
    def f(self, tpl, move):
        if move == 'L':
            tpl[0] += 1
        elif move == 'R':
            tpl[0] -= 1
        elif move == 'U':
            tpl[1] += 1
        else:
            tpl[1] -= 1
        return tpl

    def judgeCircle(self, moves):
        """
        :type moves: str
        :rtype: bool
        """
        a = functools.reduce(self.f, moves, [0, 0])
        return a[0] == 0 and a[1] == 0