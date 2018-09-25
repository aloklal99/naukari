package alok.naukari.graphs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DictionaryGenerator {

	/**
	 * reads a file and returns words in it back as a list
	 * @return
	 * @throws IOException 
	 */
	public static List<String> generate() {
		List<String> list = new ArrayList<String>();
		String wordsFile = "words.txt";
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				DictionaryGenerator.class.getClassLoader().getResourceAsStream(wordsFile)));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				list.add(line);
			}
		} catch (IOException e) {
			_LOGGER.error("Encountered error while reading words from file!", e);
			throw new RuntimeException(e);
		}
		
		Collections.sort(list);
		return list;
	}
	
	private static final Logger _LOGGER = LoggerFactory.getLogger(DictionaryGenerator.class);
}
