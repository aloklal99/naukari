package alok.naukari;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class CondenseRanges {

	public static void main(String[] args) {
		int[][][] ranges_data = new int[][][] {
				{ {0, 1}, {3, 5}, {4, 8}, {10, 12}, {9, 10} },		// { {0,1}, {4,8}, {9,12} }
				{ {0, 6}, {0, 5}, {4, 6}, {10, 13}, {11, 12} },		// { {0,6}, {10,13} }
				{ {0, 1}, {5, 6}, {3, 4}, {1, 2}, {4, 5}, {2, 3} },	// { {0,6} }	
		};
		for (int[][] ranges : ranges_data) {
			print(ranges);
			System.out.println("V1");
			int[][] coealaced_ranges = v1(ranges);
			print(coealaced_ranges);
			System.out.println("V2");
			coealaced_ranges = v2(ranges);
			print(coealaced_ranges);
			System.out.println("----");
		}
	}

	static class RangerComparator implements Comparator<int[]> {
		@Override
		public int compare(int[] first, int[] second) {
			if (first[0] < second[0]) {
				return -1;
			} else if (first[0] > second[0]) {
				return 1;
			} else { // (first[0] == second[0])
				if (first[1] < second[1]) {
					return -1;
				} else if (first[1] > second[1]) {
					return 1;
				} else {
					return 0;
				}
			}
		}
	}
	
	static int[][] v2(int[][] ranges) {
		Queue<int[]> input = new PriorityQueue<int[]>(ranges.length, new RangerComparator());
		for (int[] range : ranges) {
			input.add(range);
		}
		System.out.println("After sort");
		print(toArray(new ArrayList<int[]>(input)));
		
		List<int[]> result = new ArrayList<int[]>();
		Iterator<int[]> itr = input.iterator();
		int[] first = itr.next();
		while (itr.hasNext()) {
			int[] second = itr.next();
			if (second[0] <= first[1]) {
				first[1] = Math.max(first[1], second[1]);
			} else {
				result.add(second);
			}
		}
		result.add(first);
		return toArray(result);
	}
	
	static int[][] v1(int[][] ranges) {
		List<int[]> input = new ArrayList<int[]>(ranges.length);
		for (int[] range : ranges) {
			input.add(range);
		}
		List<int[]> result = new ArrayList<int[]>();

		Iterator<int[]> itr = input.iterator();
		while (!input.isEmpty()) {
			int[] first = itr.next(); itr.remove();
			while (itr.hasNext()) {
				int[] second = itr.next();
				if (canMerge(first, second)) {
					first = merge(first, second);
					itr.remove(); // remove the now merged range
					itr = input.iterator(); // reset the iterator
				}
			}
			result.add(first);
			itr = input.iterator();
		}
		
		return toArray(result);
	}
	
	static boolean canMerge(int[] first, int[] second) {
		return contains(first, second[0]) || contains(first, second[1]) || contains(second, first[0]) || contains(second, first[1]);
	}
	
	static boolean contains(int[] range, int point) {
		return range[0] <= point && point <= range[1];
	}
	
	static int[] merge(int[] first, int[] second) {
		int[] result = new int[2];
		result[0] = first[0] < second[0] ? first[0] : second[0];
		result[1] = first[1] > second[1] ? first[1] : second[1];
		
		return result;
	}
	
	static int[][] toArray(List<int[]> input) {
		int[][] result = new int[input.size()][];
		for (int i = 0; i < input.size(); i++) {
			result[i] = input.get(i);
		}
		return result;
	}
	static void print(int[][] ranges) {
		System.out.print("{ ");
		for (int[] range : ranges) {
			System.out.print(Arrays.toString(range));
			System.out.print(", ");
		}
		System.out.println(" }");
	}
}
