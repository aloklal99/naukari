package alok.naukari;

public class MostMoneyFromStockInADay {

	public static void main(String[] args) {
		int[] price = new int[] { 10, 8, 15, 28, 7, 27, 28, 5, 2, 23, 24, 1};
		System.out.println(String.format("v1=%d", v1(price)));
		System.out.println(String.format("v2=%d", v2(price)));
	}
	
	public static int v2(int[] price) {
		int min_price = price[0];
		int profit = price[0] - price[1];
		for (int idx = 2; idx < price.length; idx++) {
			int quote = price[idx];
			int potential_profit = quote - min_price;
			if (potential_profit > profit) {
				profit = potential_profit;
			}
			if (quote < min_price) {
				min_price = quote;
			}
		}
		return profit;
	}
	
	public static int v1(int[] price) {
		int low_idx = -1;
		int high_idx = -1;
		int low_contender_idx = -1;
		for (int idx = 0; idx < price.length; idx++) {
			System.out.println(String.format("idx=%2d, quote=%2d, low_idx=%2d, high_idx=%2d, low_contender_idx=%2d", idx, price[idx], low_idx, high_idx, low_contender_idx));
			if (low_idx == -1) {
				low_idx = idx;
			} else {
				int quote = price[idx];
				int low = price[low_idx];
				if (high_idx == -1) {
					if (quote < low) {
						low_idx = idx;   // high isn't set yet, so safely update the low 
					} else {
						high_idx = idx;  // we found our 1st valid high >= low
					}
				} else {
					int high = price[high_idx];
					if (quote > high) { // we can always bump up the high
						high_idx = idx;
					} else if (quote < low) {
						if (low_contender_idx == -1) { // we found a low contender
							low_contender_idx = idx;
						} else if (quote < price[low_contender_idx]) {
							low_contender_idx = idx; // update low contender we have a better one!
						}
					} else { // price[low_idx] > quote >= price[high_idx]
						int current_gain = high - low;
						int potential_gain = quote - price[low_contender_idx];
						if (potential_gain > current_gain) {
							low_idx = low_contender_idx;
							high_idx = idx;
						}
					}
				}
			}
			System.out.println(String.format("\tlow=%2d, high=%2d, low_contender_idx=%2d", low_idx, high_idx, low_contender_idx));
		}
		System.out.println(String.format("low=%2d, high=%2d", low_idx, high_idx));
		return price[high_idx] - price[low_idx];
	}
}
