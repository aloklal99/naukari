package alok.naukari.graphs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class MainFindAlphabets {
	public static void main(String[] args) {
		
		// read words from a file
		List<String> dictionary = DictionaryGenerator.generate();
		_LOGGER.info(dictionary.toString());

		// process dictionary
		Graph<Character> graph = processDictionary(dictionary);
		_LOGGER.info(graph.toString());
		
		Graph<Character> clone = graph.deepCopy();
		_LOGGER.info(graph.toString());

		List<Character> alphabet = pruneGraph1(clone);
		_LOGGER.info("method1: " + alphabet);
	}
	
	/**
	 * In-degree method
	 * @param graph
	 * @return
	 */
	static List<Character> pruneGraph1(Graph<Character> graph) {
		
		List<Character> alphabet = new ArrayList<>();
		Set<Character> sourceNodes = graph.getSourceNodes();
		if (sourceNodes.size() != 1) {
			_LOGGER.warn("There are " + sourceNodes.size() + " source nodes in the graph!");
			_LOGGER.error("Aborting algorithm");
		}
		else {
			Character start = sourceNodes.iterator().next();
			alphabet.add(start);
			Character node = start;
			while (graph.hasNeighbours(node)) {
				Set<Character> neighbours = graph.findNeighboursWithInDegreeOfOne(node);
				_LOGGER.info("Neighbour(s) with indegree of 1 for node " + node + " are " + neighbours);
				if (neighbours.size() != 1) {
					_LOGGER.warn("There are " + neighbours.size() + " neighbours of " + node + " with an indegree of 1 in the graph!");
					_LOGGER.error("Aborting algorithm");
					return alphabet;
				}
				Character next = neighbours.iterator().next();
				graph.removeEdgesToAllButOne(node, next);
				alphabet.add(next);
				_LOGGER.info(graph.toString());
				node = next;
			}
		}
		return alphabet;
	}

	static Graph<Character> processDictionary(List<String> dictionary) {
		
		Graph<Character> graph = new AdjacencyList<Character>();
		Iterator<String> iterator = dictionary.iterator();
		String previous = null;
		if (iterator.hasNext()) {
			previous = iterator.next();
		}
		else {
			_LOGGER.error("Empty dictionary!  Exiting!");
			System.exit(1);
		}
		
		while (iterator.hasNext()) {
			String next = iterator.next();
			char[] chars = getDifference(previous, next);
			if (chars != null) {
				// we have useful information to process
				Character first = chars[0];
				Character second = chars[1];
				if (!graph.hasNode(first)) {
					graph.addNode(first);
				}
				if (!graph.hasNode(second)) {
					graph.addNode(second);
				}
				graph.addEdge(first, second);
			}
			previous = next;
		}
		
		return graph;
	}
	/**
	 * Compares first with second and returns the 1st character in first string that differs from second string.  Mismatched
	 * characters from both strings are returned in order. 
	 * @param first
	 * @param second
	 * @return My return and empty array if difference is not relevant, e.g. "abc" and "abcd" since there is nothing in 1st
	 * to compare d with no difference is meaningful.
	 */
	static char[] getDifference(String first, String second) {
		
		for (int i = 0; first.length() > i && second.length() > i; i++) {
			char a = first.charAt(i);
			char b = second.charAt(i);
			if (a != b) {
				return new char[] { a, b };
			}
		}
		
		return null;
	}
	
	private static final Logger _LOGGER = LoggerFactory.getLogger(MainFindAlphabets.class);
}
