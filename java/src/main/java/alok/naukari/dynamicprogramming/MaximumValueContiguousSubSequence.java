package alok.naukari.dynamicprogramming;

/**
 * Created by alal on 11/9/15.
 */
public class MaximumValueContiguousSubSequence {
    public static void main(String[] args) {
        int[][] data = new int[][] {
                { 1, 2, -4, 3, 4, -6, 1, 6, -1}
        };
        for (int[] row : data) {
            Result[] results = solve(row);
            for (Result result : results) {
                System.out.println(result);
            }
            Result best = solve2(row);
            System.out.println("###" + best);
        }



    }

    /**
     * Returns start/end/sum of the maximal sequence ending at each of the positions.
     * @param input
     * @return
     */
    private static Result[] solve(int[] input) {
        Result[] results = new Result[input.length];
        Result best = null;

        for (int i = 0; i < input.length; i++) {
            int value = input[i];
            int sum = value;
            int start = i, end = i;
            if (i > 0 && value + results[i - 1]._sum > value) { // extend the cell
                sum = results[i-1]._sum + value;
                start = results[i-1]._start;
            }
            results[i] = new Result(start, end, sum);
            if (best == null || best._sum < results[i]._sum) {
                best = results[i];
            }
        }


        System.out.println(String.format("\t" + best));
        return results;
    }

    /**
     * Returns the start, end, sum of the maximal sequence.
     * @param input
     * @return
     */
    private static Result solve2(int[] input) {
        Result best = null;
        Result current = null;

        for (int i = 0; i < input.length; i++) {
            int value = input[i];
            int sum = value;
            int start = i, end = i;
            if (i > 0 && value + current._sum > value) { // extend the cell
                sum = current._sum + value;
                start = current._start;
            }
            current = new Result(start, end, sum);
            if (best == null || best._sum < current._sum) {
                best = current;
            }
        }

        return best;
    }

    static class Result {
        int _start, _end, _sum;
        Result(int start, int end, int sum) {
            this._start = start;
            this._end = end;
            this._sum = sum;
        }
        @Override
        public String toString() {
            return String.format("start=%d, end=%d, sum=%d", this._start, this._end, this._sum);
        }
    }
}
