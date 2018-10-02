package leetcode;

import java.util.HashMap;
import java.util.Map;

public class Problem3 {
    public static class Solution {
        int start;
        int end;
        int max;

        public int lengthOfLongestSubstring(String s) {
            start = 0;
            end = -1; // to guard against zero length string init like this!
            max = 0;
            if (s != null) {
                Map<Character, Integer> chars = new HashMap<>(s.length());
                for (int i = 0; i < s.length(); i++) {
                    final char c = s.charAt(i);
                    if (!chars.containsKey(c)) {
                        chars.put(c, i);
                        end = i;  // simply advance the end pointer we grow the substring length by 1
                    } else {
                        final int priorPos = chars.get(c);
                        if (priorPos < start) {
                            // it is not part of the current substring, just update the location to new value
                            chars.put(c, i);
                            end = i; // advance the end pointer
                        } else {
                            updateMax();
                            // update the start to refer to position just after where this was found last time
                            start = priorPos + 1;
                            // include this character in the current substring starting at new updated start
                            chars.put(c, i);
                            end = i;
                        }
                    }
                }
                updateMax();
            }
            return max;
        }

        void updateMax() {
            final int newMax = end - start + 1;
            if (newMax > max) {
                max = newMax;
            }
        }
    }
}
