package alok.naukari.dynamicprogramming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by alal on 11/11/15.
 */
public class LongestIncreasingSubsequence {

    static final long seed = 1234;
    static final Random random = new Random(seed);
    static private final Logger logger = LoggerFactory.getLogger(LongestIncreasingSubsequence.class);

    static public void main(String[] args) {
        final int iterations = 2;
        final int max_value = 100; // max value in sequence
        final int count = 10; // size of sequence

        for (int i = 0; i < iterations; i++) {
            int[] sequence = generateSequence(max_value, count);
            List<Integer> longest = getLongestIncreasingSubsequence_O_n_logn(sequence);
            logger.debug("longest: " + longest);
        }
    }

    private static List<Integer> getLongestIncreasingSubsequence_O_n2(int[] input) {
        List<List<Integer>> sequences = new ArrayList<>(input.length);
        for (int i = 0; i < input.length; i++) {
            List<Integer> sequence = new ArrayList<>();
            int largest = -1;
            for (int j = 0; j < i; j++) {
                if (input[i] > input[j]) {
                    if (largest == -1) {
                        largest = j;
                    } else if (sequences.get(largest).size() < sequences.get(j).size()) {
                        largest = j;
                    }
                }
            }
            if (largest == -1) {
                // could not find any value less than current cell so start a new sequence.
                sequence.add(input[i]);
            } else {
                sequence.addAll(sequences.get(largest));
                sequence.add(input[i]);
            }
            sequences.add(i, sequence);
        }
        int longest = 0;
        for (int i = 0; i < input.length; i++) {
            logger.debug(String.format("%2d: %s", i, sequences.get(i)));
            if (sequences.get(i).size() > sequences.get(longest).size()) {
                longest = i;
            }
        }
        return sequences.get(longest);
    }

    private static List<Integer> getLongestIncreasingSubsequence_O_n_logn(int[] imp_array) {
        logger.debug(String.format("input array:\n\t%s", Arrays.toString(imp_array)));
        List<Node> input = Node.nodify(imp_array);
        logger.debug(String.format("input:\n\t%s", input));
        Collections.sort(input, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                // sort in descending order by cell value
                return Integer.compare(o2._value, o1._value);
            }
        });
        logger.debug(String.format("input sorted by value:\n\t%s", input));
        List<List<Integer>> sequences = new ArrayList<>(input.size()); // sequences of maximal sub-sequence indexes (NOT values) for each position

        Node node = input.get(0);
        int prev_value = node._value;
        int prev_max_idx = node._index;
        int i = 1;
        while (i < input.size()) {
            node = input.get(i);
            if (node._value == prev_value) {
                if (node._index > prev_max_idx) {
                    prev_max_idx = node._index;
                }
            } else { // we have the next value in descending order.

            }
            int value = input.get(i)._value;
            int maxIndex = i;
            i++;
        }
        return null;
    }

    static class Node {
        int _value; // original value
        int _index; // index in the original array
        Node(int index, int value) {
            _index = index;
            _value = value;
        }

        @Override
        public String toString() {
            return String.format("{ index=%d, value=%d }", this._index, this._value);
        }

        static List<Node> nodify(int[] input) {
            List<Node> result = new ArrayList<>();
            for (int i = 0; i < input.length; i++) {
                result.add(i, new Node(i, input[i]));
            }
            return result;
        }
    }

    private static int[] generateSequence(int max_value, int count) {
        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = random.nextInt(max_value + 1);
        }
        logger.debug("generateSequence: " + Arrays.toString(result));
        return result;
    }
}
