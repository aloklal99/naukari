import inspect


def _wrapper(func):
    def wrapper(*args, **kwargs):
        print("%s(%s)" % (func.__name__, list(arg for arg in zip(inspect.getfullargspec(func).args, map(str, args)))))
        result = func.__call__(*args, **kwargs)
        print("%s: %s" % (func.__name__, result))
        return result
    return wrapper


class Solution(object):


    # def _findWrapper(self, arr, start, end, val):
    #     print(f"_find(arr={arr}, start={start}, end={end}, val={val})")
    #     result = self._find(arr, start, end, val)
    #     print(f"_find: {result}")

    # def _balancedWrapper(self, nums1, idx1, nums2, idx2):
    #     print(f"_balanced(nums1={nums1}, idx1={idx1}, nums2={nums2}, idx2={idx2})")
    #     result = self._balanced(nums1, idx1, nums2, idx2)
    #     print(f"_balanced: {result}")

    # def _findMedianSortedArraysWrapper(self, nums1, nums2):
    #     print(f"findMedianSortedArrays(nums1={nums1}, nums2={nums2})")
    #     result = self.findMedianSortedArrays(nums1, nums2)
    #     print(f"findMedianSortedArrays: {result}")

    def _mid(self, start, end):
        """
        Return the middle point
        :param start: starting index
        :param end: one beyond the last index
        :return: midpoint == int((end - start)/2), for [0, 10) => 5 and [0, 11] => 5
        """
        print(f"_mid(start={start}, end={end})")
        result = start + int((end-start)/2)
        print(f"_mid: {result}")
        return result

    def _find(self, arr, start, end, val):
        """
        Find the element
        :param arr: Array in which to search
        :param start: starting index
        :param end: index one beyond the end
        :param val: The value to search for
        :return: the index where val is in the array or start-1 if its lesser than arr[start], i.e. the lowest value
                in the input array and end if its greater than arr[end-1], i.e. the largest value in the array
        """
        print(f"_find(arr={arr}, start={start}, end={end}, val={val})")
        mid = self._mid(start, end)
        while start <= mid < end:
            if val < arr[mid]:
                if start + 1 == end:
                    return mid -1 # i.e. start-1
                else:
                    # end is one beyond and we have already checked mid thus we set it to mid
                    end = mid
            elif val == arr[mid]:
                return mid
            else: # val > arr[mid]
                if start + 1 == end:
                    return end
                else:
                    # we have already compared mid, so we are going to start one beyond
                    start = mid + 1
        return self._find(arr, start, end, val)

    def _balanced(self, nums1, idx1, nums2, idx2):
        print(f"_balanced(nums1={nums1}, idx1={idx1}, nums2={nums2}, idx2={idx2})")
        assert (-1 <= idx1 <= len(nums1)) and (-1 <= idx2 <= len(nums2)), f"Unexpected values: idx1=={idx1} or idx2=={idx2}"
        assert not (idx1 == -1 and idx2 == -1), "Both nums1 and nums2 arrays can't be on the left side!"
        assert not (idx1 == len(nums1) and idx2 == len(nums2)), "Both nums1 and nums2 arrays can't be on the right side!"

        if -1 < idx1 < len(nums1):
            left1 = idx1 + 1
        elif idx1 == -1:
            left1 = 0
        else:
            left1 = len(nums1)
        if -1 < idx2 < len(nums2):
            left2 = idx2 + 1
        elif idx2 == -1:
            left2 = 0
        else:
            left2 = len(nums2)
        left = left1 + left2
        right = len(nums1) + len(nums2) - left
        result = abs(left-right) <= 1
        print(f"_balanced: {result}")
        return result

    def findMedianSortedArrays(self, nums1, nums2):
        """
        :type nums1: List[int]
        :type nums2: List[int]
        :rtype: float
        """
        found = False
        valIdx = self._mid(0, len(nums1)) # start by looking for median of nums1 in nums2
        start = 0
        end = len(nums2)
        i = 0
        while not found:
            idx2 = self._find(nums2, start, end, nums1[valIdx])
            print(f"_find: {idx2}")
            if self._balanced(nums1, valIdx, nums2, idx2):
                found = True
            else:
                delta = len(nums2)-2*idx2
                if delta > 0: # idx2 < len(nums2)/2
                    valIdx = idx2 + int(delta/2)
                    start = idx2
                else:
                    valIdx = idx2 + int(delta / 2)
                    end = idx2
            i += 1
            if i > 5:
                raise Exception("Too many iterations!")