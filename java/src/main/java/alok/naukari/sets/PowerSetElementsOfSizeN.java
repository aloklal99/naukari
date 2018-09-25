package alok.naukari.sets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PowerSetElementsOfSizeN {

	public static void main(String[] args) {
		long seed = System.currentTimeMillis();
//		long seed = 1402215865040L;  /* 1402216091735L 1402215865040L 1402215931218L 1402216062370L */
		Random random = new Random(seed);
		_LOGGER.info("Seeding random number generator with " + seed);
		
		int n = 6;

		SetGenerator setGenerator = new SetGenerator(random);
		Set<Character> aSet = setGenerator.generate(n);
		_LOGGER.info(aSet.toString());
		
		int m = 5;
		Set<Set<Character>> a = iterative(aSet, m);
		_LOGGER.info(a.toString());
		
		Set<Set<Character>> b = recursive(aSet, m);
		_LOGGER.info(b.toString());
		
		_LOGGER.info("Result = {}", a.equals(b));
	}
	
	private static Set<Set<Character>> recursive(final Set<Character> input, final int m) {
		
		Set<Set<Character>> result = new HashSet<>();
		// base case
		if (m == 0) {
			result.add(new HashSet<Character>());
			return result;
		}
		Set<Set<Character>> intermediateSet = recursive(input, m - 1);
		for (Set<Character> aSet : intermediateSet) {
			Set<Character> difference = new HashSet<>(input);
			difference.removeAll(aSet);
			for (Character c : difference) {
				Set<Character> bSet = new HashSet<>(aSet);
				bSet.add(c);
				result.add(bSet);
			}
		}
		
		return result;
	}

	private static Set<Set<Character>> iterative(final Set<Character> aset, final int m) {
		
		List<Set<Character>> list = new ArrayList<>();
		Set<Set<Character>> result = new HashSet<>();
		
		list.add(new HashSet<Character>());
		int i = 0;
		while (i < list.size()) {
			Set<Character> set = list.get(i);
			Set<Character> difference = new HashSet<>(aset);
			difference.removeAll(set);
			for (Character c : difference) {
				Set<Character> s = new HashSet<>(set);
				s.add(c);
				if (s.size() == m) {
					result.add(s);
				}
				else {
					list.add(s);
				}
			}
			++i;
		}
		
		return result;
	}

	private final static Logger _LOGGER = LoggerFactory.getLogger(PowerSetElementsOfSizeN.class);
}
