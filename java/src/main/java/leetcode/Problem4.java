package leetcode;

public class Problem4 {

    public static class Solution {
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int[] first, second;
            if (nums1[nums1.length - 1] <= nums2[0]) {
                // largest number in num1 is smaller than same or smallest number in num2 so they can be concatenated
                first = nums1;
                second = nums2;
                return simpleMedian(first, second);
            } else if (nums2[nums2.length - 1] <= nums1[0]) {
                // largest number in num2 is smaller than same or smallest number in num1 so they can be concatenated
                first = nums2;
                second = nums1;
            }
            throw new RuntimeException();
        }

        private double simpleMedian(int[] first, int[] second) {
            throw new RuntimeException();
        }
    }
}

