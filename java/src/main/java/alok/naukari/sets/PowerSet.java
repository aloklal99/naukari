package alok.naukari.sets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class PowerSet {
	static public void main(String[] args) {
		long seed = System.currentTimeMillis();
//		long seed = 1402215865040L;  /* 1402216091735L 1402215865040L 1402215931218L 1402216062370L */
		Random random = new Random(seed);
		_LOGGER.info("Seeding random number generator with " + seed);
		
		int size = 4;
		SetGenerator setGenerator = new SetGenerator(random);
		Set<Character> aset = setGenerator.generate(size);
		_LOGGER.info(aset.toString());
		
		Set<Set<Character>> a = recursive(aset);
		_LOGGER.info(a.toString());

		Set<Set<Character>> b = iterative(aset);
		_LOGGER.info(a.toString());
		
		_LOGGER.info("Result = {}", a.equals(b));
	}


	private static Set<Set<Character>> iterative(Set<Character> input) {
		List<Set<Character>> list = new ArrayList<>();
		list.add(new HashSet<Character>());
		Iterator<Set<Character>> iterator = list.iterator();
		boolean done = false;
		while (!done) {
			Set<Character> set = iterator.next();
			if (set.equals(input)) {
				// We stop when we create a powerset element that is the same as the set!
				done = true;
			}
			
		}
		return new HashSet<>(list);
	}


	private static Set<Set<Character>> recursive(Set<Character> aset) {
		
		// powerset of empty set is a set with one element which is an empty set. 
		Set<Set<Character>> result = new HashSet<>();
		if (aset.size() == 0) {
			Set<Character> emptySet = new HashSet<Character>();
			result.add(emptySet);
			return result;
		}
		// remove one character and store it off
		Character c = aset.iterator().next();
		aset.remove(c);
		
		// find its powerset
		Set<Set<Character>> butOne = recursive(aset);
		result.addAll(butOne);
		for (Set<Character> set : butOne) {
			Set<Character> bSet = new HashSet<>(set);
			bSet.add(c);
			result.add(bSet);
		}
		
		return result;
	}

	private final static Logger _LOGGER = LoggerFactory.getLogger(PowerSet.class);
}
