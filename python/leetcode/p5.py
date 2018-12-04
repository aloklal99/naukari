class Solution(object):
    def longestPalindrome(self, s):
        """
        :type s: str
        :rtype: str
        """
        def isPalindrome(s):
            for i in range(int(len(s) / 2)):
                if s[i] != s[len(s) - 1 - i]:
                    return False
            return True

        if not s:
            return s
        # longest CURRENT palindrome ending on 0th character is 1 character long
        cs, ce = 0, 1
        # longest so far
        ls, le = cs, ce
        asm = True
        asmc = s[0]
        i = 1
        while i < len(s):
            # extend current on both sides
            if cs - 1 >= 0 and ce <= len(s) and s[cs - 1]==s[ce]:
                cs -= 1  # take start back by one
                ce += 1
                if s[cs] != asmc:
                    asm = False
            elif asm and s[cs] == s[ce]:
                ce += 1
            else:
                # ith character can't grow the current palindrome, so start new one
                if le - ls < ce - cs:
                    le, ls = ce, cs
                while cs < ce and not isPalindrome(s[cs:ce+1]):
                    cs += 1
                ce += 1
                asm = True
                asmc = s[cs]
            i += 1
        if le - ls < ce - cs:
            le, ls = ce, cs
        return s[ls:le]