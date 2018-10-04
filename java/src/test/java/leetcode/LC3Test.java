package leetcode;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class LC3Test {
    LC3.Solution p = new LC3.Solution();

    @Test
    public void emptyString() {
        assertEquals(0, p.lengthOfLongestSubstring(""));
    }

    @Test
    public void nullString() {
        assertEquals(0, p.lengthOfLongestSubstring(null));
    }

    @Test
    public void test1() {
        assertEquals(1, p.lengthOfLongestSubstring("a"));
    }

    @Test
    public void test2() {
        assertEquals(1, p.lengthOfLongestSubstring("aaa"));
    }

    @Test
    public void test3() {
        assertEquals(2, p.lengthOfLongestSubstring("aba"));
    }

    @Test
    public void test4() {
        assertEquals(3, p.lengthOfLongestSubstring("abcabb"));
    }

    @Test
    public void test5() {
        Map<String, Integer> testCases = ImmutableMap.of(
                "bbbbb", 1,
                "abcabcbb", 3,
                "pwwkew", 3
        );
        for (Map.Entry<String, Integer> entry : testCases.entrySet()) {
            assertEquals(entry.getKey(), (int)entry.getValue(), p.lengthOfLongestSubstring(entry.getKey()));
        }
    }
}