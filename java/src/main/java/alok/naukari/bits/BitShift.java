package alok.naukari.bits;


public class BitShift {

	public static void main(String[] args) {
		for (int i = 0; i <= Long.SIZE; i++) {
			long j = 1L << i;
			System.out.format("%2d: %20d %s\n", i, j, getBits(j));
		}
	}
	
	private static String getBits(long l) {

		StringBuilder builder = new StringBuilder(Long.SIZE);
		for (int i = 0; i < Long.SIZE; i++) {
			builder.append(((1L << i) & l) == 0 ? '.' : '1');
		}
		
		return builder.reverse().toString();
	}
}
