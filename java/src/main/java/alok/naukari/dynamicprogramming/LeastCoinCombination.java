package alok.naukari.dynamicprogramming;

import com.google.common.collect.Maps;

import java.util.*;

/**
 * Created by alal on 11/10/15.
 */
public class LeastCoinCombination {
    public static void main(String[] args) {

        int[] coins = { 1, 5, 10, 25, 100 };

        Map<Integer, List<Integer>> results = getCoinCombinations(coins, 60);
        int i = 1;
        for (Map.Entry<Integer, List<Integer>> entry : results.entrySet()) {
            int total = entry.getKey();
            List<Integer> coinage = entry.getValue();
            Map<Integer, Integer> map = mappify(coinage);
            System.out.println(String.format("%d: %s", total, map));
        }
    }

    private static Map<Integer, Integer> mappify(List<Integer> coinage) {
        Map<Integer, Integer> result = new HashMap<>();
        for (Integer denomination : coinage) {
            if (result.containsKey(denomination)) {
                result.put(denomination, result.get(denomination) + 1);
            } else {
                result.put(denomination, 1);
            }
        }
        return result;
    }

    private static Map<Integer, List<Integer>> getCoinCombinations(final int[] denominations, final int max) {
        Map<Integer, List<Integer>> result = new HashMap<>(max);
        for (int total = 0; total <= max; total++) {
            List<Integer> best = null;
            if (total == 0) {
                best = new ArrayList<>();
            }
            else {
                for (int j = 0; j < denominations.length; j++) {
                    int denomination = denominations[j];
                    if (total - denomination >= 0) {
                        List<Integer> current = new ArrayList<>();
                        current.addAll(result.get(total - denomination));
                        current.add(denomination);
                        if (best == null || best.size() > current.size()) {
                            best = current;
                        }
                    }
                }
            }
            result.put(total, best);
        }

        return result;
    }
}
