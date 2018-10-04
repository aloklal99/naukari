package leetcode;

import java.util.HashMap;
import java.util.Map;

public class LC1 {

    class Solution1 {
        public int[] twoSum(int[] nums, int target) {
            for (int i = 0; i < nums.length; i++) {
                int remaining = target - nums[i];
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[j] == remaining) {
                        return new int[] { i, j };
                    }
                }
            }
            throw new IllegalArgumentException("No solution found!");
        }
    }

    class Solution2 {
        public int[] twoSum(int[] nums, int target) {
            Map<Integer, Integer> complements = new HashMap<>(nums.length);
            for (int i = 0; i < nums.length; i++) {
                complements.put(nums[i], i);
            }
            for (int i = 0; i < nums.length; i++) {
                int complement = target - nums[i];
                if (complements.containsKey(complement)) {
                    final int j = complements.get(complement);
                    if (j != i) {
                        return new int[] {i, j};
                    }
                }
            }
            throw new IllegalArgumentException("No solution found!");
        }
    }

    class Solution {
        public int[] twoSum(final int[] nums, final int target) {
            Map<Integer, Integer> complements = new HashMap<>(nums.length);

            for (int i = 0; i < nums.length; i++) {
                final int complement = target - nums[i];
                if (complements.containsKey(complement)) {
                    final int j = complements.get(complement);
                    if (j != i) {
                        return new int[]{i, j};
                    }
                }
                complements.put(nums[i], i);
            }
            throw new IllegalArgumentException("No solution!");
        }
    }
}
