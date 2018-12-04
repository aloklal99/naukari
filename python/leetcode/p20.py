class Solution(object):
    def isValid(self, s):
        """
        :type s: str
        :rtype: bool
        """
        stk = []
        opening = "([{"
        closing = ")]}"
        close2open = {
            ")" : "(",
            "}" : "{",
            "]" : "[",
        }
        for c in s:
            if c in opening:
                stk.append(c)
            elif c in closing:
                if stk:
                    if close2open[c] == stk.pop():
                        pass  # this one was matched
                    else:
                        return False
                else:
                    return False
            else:
                pass  # this is neither open nor close bracket so we ignore it
        return True