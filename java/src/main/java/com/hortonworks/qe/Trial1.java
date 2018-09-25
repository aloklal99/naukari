package com.hortonworks.qe;

public class Trial1 {

	public int multiply(int a, int b) {
		return (int) a * b;
	}
	
	public int add(int...input) {
		int total = 0; 
		for (int value : input) {
			total += value;
		}
		return total;
	}
}
