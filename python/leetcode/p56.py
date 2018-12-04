# Definition for an interval.
# class Interval:
#     def __init__(self, s=0, e=0):
#         self.start = s
#         self.end = e
import queue

class Solution:
    def merge(self, intervals):
        """
        This version does not materialize the sorted value but instead directly uses it.
        Also it avoid creating new intervals instead it alters the current Interval object in place.
        :type intervals: List[Interval]
        :rtype: List[Interval]
        """
        r = []
        if intervals:
            first = None
            for second in sorted(intervals, key=lambda i: i.start):
                if not first:
                    first = second
                elif first.end >= second.start:
                    first.end = max(first.end, second.end)
                else:
                    r.append(first)
                    first = second
            r.append(first)
        return r