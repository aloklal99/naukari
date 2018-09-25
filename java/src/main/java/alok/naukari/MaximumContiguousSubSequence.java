package alok.naukari;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Scanner;

public class MaximumContiguousSubSequence {

	static private final Logger LOG = LoggerFactory.getLogger(MaximumContiguousSubSequence.class);
	public static void main(String[] args) {
		int[][] testData = new int[][] {
				{ -6, -2, -1, -3, -4, -5}, {2, 3},  // all -ve
				{ -5, -5, 7, -1, -2}, {2, 3},        // discard starting and ending -ves
				{ -5, -5, 0, -1, -2}, {2, 3},        // max value is 0
				{ -10, 5, -6, 5, -7, 6}, {5, 6},     // no cell can grow
				{ -3, -1, 1, 2, -1, -2 }, {2, 4},   // single positive range
				{ -1, -2, 1, 2, -1, -1, 1, 2, -3, -4 },  { 2, 8 }, // two positive ranges that can merge
				{ -1, -2, 1, 2, -1, -2, 1, 2, -1, -2, 4 },  { 2, 11 }, // must merge a 0 link to grow to largeat
		};
		
		for (int i = 0; i < testData.length; i = i + 2) {
			
			int[] input = testData[i];
			LOG.debug("input: " + Arrays.toString(input));
			
			int[] expected = testData[i + 1];
			LOG.debug("expected: " + Arrays.toString(expected));
			
			int[] result = maxSubSequence(input);
			String msg = String.format("input: %s, expected: %s, result: %s", Arrays.toString(input), Arrays.toString(expected), Arrays.toString(result));
			boolean verdict = Arrays.equals(result, expected);
			System.out.println(String.format("%5s: %s\n", verdict ? "PASS" : "FAIL", msg));
		}
	}
	
	enum Type { POSITIVE, NEGATIVE};
	enum Direction { FORWARD, BACKWARD;

	public Direction flip() {
		if (this == FORWARD) {
			return BACKWARD;
		} else {
			return FORWARD;
		}
	}};
	
	static class Node {
		public Node(int start, int end, int maxVal) {
			LOG.debug("Node::Node()");
			_start = start;
			_end = end;
			_maxVal = maxVal;
			_maxIdx = _start;
			_sum = _maxVal;
			_next = _prev = null;
			if (maxVal < 0) {
				_type = Type.NEGATIVE;
			} else {
				_type = Type.POSITIVE;
			}
		}
		
		public boolean canGrowForward(int value) {
			LOG.debug("Node::canGrowForward()");
			if (_type == Type.POSITIVE && value >= 0) {
				return true;
			} else if (_type == Type.NEGATIVE && value < 0) {
				return true;
			} else {
				return false;
			}
		}
		
		public void growForward(int value) {
			LOG.debug("Node::growForward()");

			assert canGrowForward(value) : "Value [" + value +"} can't grow forward!  Call canGrowForward() first!";
			
			_end++;
			_sum += value;
			if (value > _maxVal) {
				_maxVal = value;
				_maxIdx = _end - 1;
			}
		}
		
		private Node[] getNeighbours(Direction direction) {
			LOG.debug("Node::getNeighbours()");
			
			Node neighbour = null;
			Node neighboursNeighbour = null;
			
			if (direction == Direction.FORWARD) {
				neighbour = _next;
				if (neighbour != null) {
					neighboursNeighbour = neighbour._next;
				}
			} else {
				neighbour = _prev;
				if (neighbour != null) {
					neighboursNeighbour = _prev._prev;
				}
			}

			return new Node[] { neighbour, neighboursNeighbour };
		}
		
		public boolean canMerge(Direction direction) {
			LOG.debug("Node::canMerge()");
			
			if (this._type == Type.NEGATIVE) {
				return false; // never makes sense to merge -ve node!
			}
			Node[] neighbours = getNeighbours(direction);
			Node neighbour = neighbours[0];
			Node neighboursNeighbour = neighbours[1];

			if (neighbour == null || neighboursNeighbour == null) {
				return false;
			}
			
			// it shouldn't harm to merge
			if (neighbour._sum + neighboursNeighbour._sum >= 0) {
				// if it is 0 we want to merge so that we have a chance to merge with other values beyond the neighbours, if any
				return false;
			}
			return true;
		}
		
		public int[] getMaxSubSequence() {
			LOG.debug("Node::getMaxSubSequence()");

			if (_type == Type.NEGATIVE) {
				return new int[] { _maxIdx, _maxIdx + 1 };
			} else {
				return new int[] { _start, _end };
			}
		}

		int _start;
		int _end;  // exclusive
		int _maxVal;
		int _maxIdx;
		int _sum;
		final Type _type;
		Node _next;
		Node _prev;
	};
	
	static class NodeList {
		
		Node _head = null;
		Node _tail = null;
		public void append(Node n) {
			LOG.info("NodeList.append");
			assert n != null;
			
			if (_head == null) {
				assert _tail == null : "_head was null but _tail wasn't!";
				_head = _tail = n;
			} else {
				assert n._next == null : "only single nodes can be inserted!";
				n._prev = _tail;
				_tail._next = n;
				_tail = n;
			}
		}
		
		public void merge(Node n, Direction direction) {
			LOG.info("NodeList.merge");

			assert n._type != Type.NEGATIVE : "attempting to merge a -ve cell!  Call canMerge() first!";  

			Node[] neighbours = n.getNeighbours(direction);
			Node neighbour = neighbours[0];
			Node neighboursNeighbour = neighbours[1];

			if (neighbour == null || neighboursNeighbour == null) {
				throw new RuntimeException("Does not have two neighbours!  Call canMerge() first!");
			}
			
			if (neighboursNeighbour._maxVal > n._maxVal) {
				n._maxVal = neighboursNeighbour._maxVal;
				n._maxIdx = neighboursNeighbour._maxIdx;
			}
			
			assert neighbour._sum + neighboursNeighbour._sum >= 0 : "Sum of nodes being merged is less than 0!";
			
			n._sum += neighbour._sum + neighboursNeighbour._sum;
			
			if (direction == Direction.FORWARD) {
				n._end = neighboursNeighbour._end;
				n._next = neighboursNeighbour._next;
				if (neighboursNeighbour == _tail) {
					_tail = n;
				}
			} else {
				n._start = neighboursNeighbour._start;
				n._prev = neighboursNeighbour._prev;
				if (neighboursNeighbour == _head) {
					_head = n;
				}
			}
		}

		public Node findBest() {
			LOG.info("NodeList.merge");

			Node best, n;
			best = n = _head;
			while (n != null) {
				if (best._type == Type.NEGATIVE) {
					if (n._type == Type.POSITIVE) {
						best = n;
					} else if (n._maxVal > best._maxVal) {
						best = n;
					}
				} else if (n._type == Type.POSITIVE) { // negative has no opens of besting a positive best-so-far node
					if (n._sum > best._sum) {
						best = n;
					}
				}
				n = n._next;
			}
			assert best != null;
			return best;
		}
	}
	
	static int[] maxSubSequence(int[] input) {

		LOG.info("NodeList.maxSubSequence");
		// we assume the result is sequence of length 1 starting at the 1st character. 
		NodeList nodeList = coealesce(input);
		assert nodeList != null;
		assert nodeList._head != null;
		assert nodeList._tail != null;
		
		nodeList = coealesce(nodeList);
		assert nodeList != null;
		assert nodeList._head != null;
		assert nodeList._tail != null;
		
		
		Node best = nodeList.findBest();
		assert best != null;
		
		int[] result = best.getMaxSubSequence();
		assert result != null;
		
		return result;
	}
	
	static NodeList coealesce(int[] input) {

		LOG.info("NodeList.coealesce(int[])");
		assert input != null && input.length > 0 : "input can't be empty!"; 

		NodeList result = new NodeList();
		Node current = null;
		for (int i = 0; i < input.length; i++) {
			int value = input[i];
			if (current == null) { // we need to start a new node!
				current = new Node(i, i+1, value);
			} else if (current.canGrowForward(value)) {
				current.growForward(value);
			} else { // can't grow we have a new node that needs to be added to our list
				result.append(current);
				current = new Node(i, i+1, value);
			}
		}
		
		assert current != null; // at worse last item should be a node that is yet to be added
		result.append(current); // Last node would need to added to the list!
		
		return result;
	}
	
	static NodeList coealesce(NodeList nodeList) {

		LOG.info("NodeList.coealesce(NodeList)");
		assert nodeList != null;
		
		Node n = nodeList._head;
		while (n != null) {
			boolean merged = false;
			do {
				merged = false;
				if (n.canMerge(Direction.FORWARD)) {
					nodeList.merge(n, Direction.FORWARD);
					merged = true;
				}
				if (n.canMerge(Direction.BACKWARD)) {
					nodeList.merge(n, Direction.BACKWARD);
					merged = true;
				}
				System.out.println("Hit any key to resume: ");
//				input.nextLine();
				
			} while (merged); // don't move pointer try to gobble up neighbors as much as possible
			n = n._next;
		}
		return nodeList;
	}
	
	static private final Scanner input = new Scanner(System.in);
}
