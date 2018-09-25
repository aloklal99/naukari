package alok.naukari;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

public class LargestMultipleFrom3Numbers {

	public static void main(String[] args) {
		int inputs[][] = new int[][] {
				{10, 20, 30, 40, 50, 60, 7}, // 12000
				{60, 50, 40, 30, 20, 10, 5},  // 12000
				{60, 30, 20, 50, 40, 10, 5},  // 12000
				{60, 30, -20, 50, 40, -10, -5},  // 12000
				{10, 20, 30, 35, -50, -60, 40}, // 12000
				{10, 20, 30, 50, -40, 60, 40}, // 12000
				{-10, -20, -30, -50, -40, 60}, // 12000
		};
		for (int[] input: inputs) {
			System.out.println(String.format("Input :%s", Arrays.toString(input)));
			int result = v1(input);
			System.out.println(String.format("Output:%s", result));
		}
	}
	
	static int v1(int[] input) {
		Queue<Integer> largest = new PriorityQueue<Integer>(3); // smallest of 3 largest numbers at top
		Queue<Integer> smallest = new PriorityQueue<Integer>(3, Collections.reverseOrder()); // largest of 3 smallest number at top
		for (int number : input) {
			if (largest.size() < 3) {
				largest.add(number);
			} else if (number > largest.peek()) {
				Integer smallest_large = largest.remove();
				largest.add(number);
				// only way what we removed from largest collection can get into lowest is if lowest has less than 3 elements in it
				if (smallest.size() < 3) {
					smallest.add(smallest_large);
				}
			} else if (smallest.size() < 3) {
				smallest.add(number);
			} else if (number < smallest.peek()) {
				smallest.remove();
				smallest.add(number);
			}
		}
		// we have at least 3 elements
		Integer butLargestProduct = largest.remove() * largest.remove();
		int result = largest.remove();
		if (smallest.size() < 2) {
			result *= butLargestProduct;
		} else {
			if (smallest.size() == 3) {
				smallest.remove(); // discard the highest
			}
			Integer butSmallestProduct = smallest.remove() * smallest.remove();
			if (butSmallestProduct > butLargestProduct) {
				result *= butSmallestProduct;
			} else {
				result *= butLargestProduct;
			}
		}
		return result;
	}

}
