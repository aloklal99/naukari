package alok.naukari;

import java.util.Arrays;

public class MultiplyAllInAnArrayWithoutDividing {

	public static void main(String[] args) {
		int[] input = new int[] { 7, 1, 3, 4};
		System.out.println("Input");
		print_array(input);
		int result[] = v1(input);
		result = v2(input);
	}

	static int[] v1(int[] input) {
		int[] result = new int[input.length];
		result[0] = 1;
		System.out.println("---- V1");
		System.out.println("Before 1st loop");
		print_array(result);
		for (int i = 0; i < input.length - 1; i++) {
			result[i + 1] = result[i] * input[i]; 
		}
		System.out.println("After 1st loop");
		print_array(result);
		int scratch = 1;
		for (int i = input.length - 1; i > 0; i--) {
			scratch *= input[i];
			result[i - 1] *= scratch;
		}
		System.out.println("After 2nd loop");
		print_array(result);
		return result;
	}
	
	static int[] v2(int[] input) {
		int result[] = new int[input.length];
		System.out.println("---- V2");
		System.out.println("Before 1st loop");
		print_array(result);
		int product = 1;
		for (int i = 0; i < input.length; i++) {
			result[i] = product;
			product *= input[i];
		}
		System.out.println("After 1st loop");
		print_array(result);
		product = 1;
		for (int i = input.length - 1; i >= 0; i--) {
			result[i] *= product;
			product *= input[i];
		}
		System.out.println("After 2nd loop");
		print_array(result);
		return result;
	}
	static void print_array(int[] array) {
		System.out.println(Arrays.toString(array));
		for (int i=0; i < array.length; i++) {
			System.out.print(String.format("%d ", array[i]));
		}
		System.out.println();
	}
}
