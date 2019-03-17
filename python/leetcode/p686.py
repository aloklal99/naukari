class Solution1:
    def repeatedStringMatch(self, A, B):
        """
        :type A: str
        :type B: str
        :rtype: int
        """
        if len(B) < len(A):
            if A.find(B) != -1:
                return 1
            elif (A+A).find(B) != -1:
                return 2
            else:
                return -1
        else: # len(B) > len(A)
            times = int(len(B)/len(A))
            if times * len(A) < len(B): # len(B) is integral multiple of len(A)
                times += 1
            print("times: ", times)
            C = A*times
            if C.find(B) != -1:
                return times
            elif (C+A).find(B) != -1:
                return times+1
            else:
                return -1

class Solution2:
    def repeatedStringMatch(self, A, B):
        """
        :type A: str
        :type B: str
        :rtype: int: minimum number of times A has to be repeated so that B is a substring, 
                     or -1 if no such solution
        """
        if not A:
            return -1
        if not B:
            return 1
        if not set(B).issubset(A):
            return -1
        a = 0
        C = A
        n = 1
        while a < len(A):
            if B[0] == A[a]:
                b = 0
                c = a
                while b < len(B):
                    if not c < len(C):
                        C += A
                        n += 1
                    if B[b] != C[c]:
                        break
                    b += 1
                    c += 1
                if b == len(B):
                    return n
            a += 1        
        return -1