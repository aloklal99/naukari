import collections

class Solution:
    def killProcess(self, pid, ppid, kill):
        """
        :type pid: List[int]
        :type ppid: List[int]
        :type kill: int
        :rtype: List[int]
        """
        m = collections.defaultdict(list)
        for idx, p in enumerate(ppid):
            m[p].append(pid[idx])
        a = [kill]
        r = []
        while a:
            p = a.pop()
            if p in m:
                a.extend(m[p])
            r.append(p)

        return r