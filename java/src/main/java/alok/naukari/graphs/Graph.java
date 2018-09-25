package alok.naukari.graphs;

import java.util.List;
import java.util.Set;

public interface Graph<K> {

	public boolean hasNode(K key);
	public void addNode(K key);
	public void removeNode(K key);
	public void removeNodes(List<K> sourceNodes);

	public boolean hasEdge(K from, K to);
	public void addEdge(K first, K second);
	public void removeEdge(K first, K second);
	/**
	 * Does the specified node have any neighbours
	 * @param node
	 * @return
	 */
	public boolean hasNeighbours(K node);

	public Graph<K> deepCopy();

	// sink is a node whose adjacency list is empty, i.e. it does not connect further to any other node.
	public boolean hasSinkNodes();
	public Set<K> getSinkNodes();
	
	// source is a node that isn't in any other nodes adjacency list.
	public boolean hasSourceNodes();
	public Set<K> getSourceNodes();

	public Set<K> findNeighboursWithInDegreeOfOne(K node);
	
	public void removeEdgesToAllButOne(K node, K theOne);
}
