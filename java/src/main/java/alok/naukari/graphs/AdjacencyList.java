package alok.naukari.graphs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class AdjacencyList<Key> implements Graph<Key> {
	Set<Key> _nodes;
	Map<Key, Set<Key>> _edges;
	
	public AdjacencyList() {
		_nodes = new HashSet<Key>();
		_edges = new HashMap<Key, Set<Key>>();
	}
	
	public AdjacencyList(int numNodes, int numEdges) {
		_nodes = new HashSet<Key>(numNodes);
		_edges = new HashMap<Key, Set<Key>>(numEdges);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("Graph: # nodes=%d, # edges=%d\n", _nodes.size(), _edges.size()));
		for (Key key : _edges.keySet()) {
			builder.append(String.format("\t%s => ", key));
			for (Key edge : _edges.get(key)) {
				builder.append(String.format("%s ", edge));
			}
			builder.append("\n");
		}
		
		return builder.toString();
	}
	
	
	@Override
	public boolean hasNode(Key name) {
		return _nodes.contains(name);
	}

	@Override
	public void addNode(Key name) {
		if (_nodes.contains(name)) {
			_LOGGER.warn("Graph already contains node named: " + name + "! Skipped adding.");
			return;
		}
		_nodes.add(name);
		_edges.put(name, new HashSet<Key>()); // initialize the edges set
	}

	@Override
	public void removeNode(Key target) {
		if (!_nodes.contains(target)) {
			_LOGGER.warn("Graph doesn't contains a node named: " + target + "! Skipped removal.");
		}
		// first remove the node from node and edge list
		_nodes.remove(target);
		_edges.remove(target);
		// next remove it from adjacency list of all other nodes
		for (Key node : _nodes) {
			Set<Key> neighbours = _edges.get(node);
			for (Iterator<Key> iterator = neighbours.iterator(); iterator.hasNext(); ) {
				Key neighbour = iterator.next();
				if (neighbour.equals(target)) {
					iterator.remove();
				}
			}
		}
	}

	@Override
	public void removeNodes(List<Key> sourceNodes) {
		for (Key key : sourceNodes) {
			removeNode(key);
		}
	}

	@Override
	public boolean hasEdge(Key from, Key to) {
		if (!hasNode(from) || !hasNode(to)) {
			String msg = String.format("Nodes %s and/or %s don't exist in the graph!  Pretending that edge doesn't exist.", from, to);
			_LOGGER.warn(msg);
			return false;
		}
		return _edges.get(from).contains(to);
	}
	
	@Override
	public boolean hasNeighbours(Key node) {
		return !_edges.get(node).isEmpty();
	}
	
	@Override
	public void addEdge(Key from, Key to) {
		if (!hasNode(from) || !hasNode(to)) {
			String msg = String.format("Nodes %s and/or %s don't exist in the graph!  Skipping edge addition.", from, to);
			_LOGGER.warn(msg);
			return;
		}
		_edges.get(from).add(to);
	}

	@Override
	public void removeEdge(Key from, Key to) {
		if (!hasNode(from) || !hasNode(to)) {
			String msg = String.format("Nodes %s and/or %s don't exist in the graph!  Skipping edge removal.", from, to);
			_LOGGER.warn(msg);
			return;
		}
		Set<Key> edges = _edges.get(from); 
		if (edges.contains(to)) {
			edges.remove(to);
		}
		else {
			String msg = String.format("There isn't an edge from %s to %s in the graph!  Skipping edge removal.", from, to);
			_LOGGER.warn(msg);
		}
	}
	
	@Override
	public boolean hasSinkNodes() {
		for (Key key : _edges.keySet()) {
			Set<Key> neighbours = _edges.get(key); 
			if (neighbours.isEmpty()) {
				return true;
			}
		}
		
		return false;
	}
	

	@Override
	public Set<Key> getSinkNodes() {
		Set<Key> sinks = new HashSet<Key>();
		for (Key key : _edges.keySet()) {
			Set<Key> neighbours = _edges.get(key);
			if (neighbours.isEmpty()) {
				sinks.add(key);
			}
		}
		
		return sinks;
	}
	
	@Override
	public boolean hasSourceNodes() {
		return !getSinkNodes().isEmpty();
	}
	
	@Override
	public Set<Key> getSourceNodes() {
		Set<Key> nodes = _edges.keySet();
		// let's assume that all nodes are source nodes
		Set<Key> soureNodes = new HashSet<Key>(_edges.keySet());
		// check every node and any node it connect to can't be a source.  What remains much be set of source nodes
		for (Key node: nodes) {
			Set<Key> neighbours = _edges.get(node);
			soureNodes.removeAll(neighbours);
		}
		
		return soureNodes;
	}
	
	@Override
	public Graph<Key> deepCopy() {
		Graph<Key> clone = new AdjacencyList<Key>(_nodes.size(), _edges.size());
		for (Key node : _nodes) {
			clone.addNode(node);
		}
		for (Key node : _nodes) {
			Set<Key> neighbours = _edges.get(node);
			for (Key neighbour : neighbours) {
				clone.addEdge(node, neighbour);
			}
		}
		return clone;
	}
	
	private static final Logger _LOGGER = LoggerFactory.getLogger(AdjacencyList.class);

	@Override
	public Set<Key> findNeighboursWithInDegreeOfOne(Key theNode) {
		
		// in degree of all neighbours is at least 1 by definition.
		Set<Key> neighbours = new HashSet<Key>(_edges.get(theNode));
		
		// remove any neighbour to which any other node also connects as then its indegree is >1
		for (Key aNode : _nodes) {
			if (aNode != theNode) {
				for (Key aNeighbour : _edges.get(aNode)) {
					if (neighbours.contains(aNeighbour)) {
						neighbours.remove(aNeighbour);
					}
				}
			}
		}
		
		return neighbours;
	}

	@Override
	public void removeEdgesToAllButOne(Key node, Key theOne) {
		Set<Key> set = new HashSet<Key>(1);
		set.add(theOne);
		_edges.get(node).retainAll(set);
	}
}
