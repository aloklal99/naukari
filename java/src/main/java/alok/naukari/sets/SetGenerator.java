package alok.naukari.sets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SetGenerator {
	
	private static final int _SIZE = 26+10+26;
	private static Character[] alphabet = new Character[_SIZE];
	
	private Random _random;
	static {
		char c = 'a';
		List<Character> list = new ArrayList<>(_SIZE);
		for (int i = 0; i < 26; i++) {
			list.add((char) (c + i));
		}
		c = 'A';
		for (int i = 0; i < 26; i++) {
			list.add((char) (c + i));
		}
		c = '0';
		for (int i = 0; i < 10; i++) {
			list.add((char) (c + i));
		}
		alphabet = list.toArray(alphabet);
	}

	public SetGenerator(Random random) {
		_random = random;
	}
	
	public Set<Character> generate(int size) {
		Set<Character> aSet = new HashSet<>(size);
		// can't do a while as we may end up adding same character several times!
		while (aSet.size() != size) {
			int index = _random.nextInt(_SIZE);
			aSet.add(alphabet[index]);
		}
		return aSet;
	}
}
