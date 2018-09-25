package alok.naukari.trees;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Trees {
	
	/**
	 * Return a random node from the 
	 * @param root
	 * @param depth how deep you want to traverse the graph to find a node.  Higher the value between 0 and 1 
	 * the higher is the chance that node selected would be lower lower in the tree.
	 * @return
	 */
	public static TreeNode getRandomNode(TreeNode root, Random random, double depth) {
		/*
		 * Use the probability to decide if we want to return the current node or not.
		 * If probably isn't but if the current node is leaf node then return it.
		 * If we haven't found the node yet then randomly traverse the left or right subtree.
		 */
		TreeNode result = null;
		boolean done = false;
		do {
			if (random.nextDouble() >= depth) {
				// if we are above probablity then we are done
				done = true;
				result = root;
			}
			else if (root.isLeaf()){
				done = true;
				result = root;
			}
			else if (root._left == null) {
				// since it wasn't a leaf right, too, can't be null
				root = root._right;
			}
			else if (root._right == null) {
				root = root._left;
			}
			else if (random.nextBoolean()) {
				// randomly either traverse down the left or right subtree
				root = root._left;
			}
			else {
				root = root._right;
			}
		} while (!done);
		
		return result;
	}
	/**
	 * Returns the depth of a tree.  Null node has a depth of 0, a single node tree has a depth of 1, etc. 
	 * @param node
	 * @return
	 */
	public static int heightOf(TreeNode node) {
		if (node == null) {
			return 0;
		}
		else if (node._left == null && node._right == null) {
			return 1;
		}
		else {
			int leftDepth = heightOf(node._left);
			int rightDepth = heightOf(node._right);
			if (leftDepth > rightDepth) {
				return 1 + leftDepth;
			}
			else {
				return 1 + rightDepth;
			}
		}
	}
	
	/**
	 * If all values in the tree were printer out as decimal integers then 
	 * what would be the width of the widest value.  Usefull for pretty printing
	 * of the tree. 
	 * @param node
	 * @return
	 */
	public static int maxValueWidth(TreeNode node) {
		
		if (node == null) {
			return 0;
		}
		
		int thisWidth = String.format("%d", node._value).length();
		int leftMax = maxValueWidth(node._left);
		int rightMax = maxValueWidth(node._right);
		int max = Math.max(Math.max(thisWidth, leftMax), rightMax);
		
		return max;
	}
	
	/**
	 * Do in-order traversal of the tree.
	 * @param node
	 */
	public static void inOrderTraversal(TreeNode node) {
		if (node == null) {
			return;
		}
		inOrderTraversal(node._left);
		node.print();
		inOrderTraversal(node._right);
	}
	
	/**
	 * Returns all parents of node aNode starting from the node onwards, i.e. last element in the deque would be the root
	 * Returns an empty list if root or node are null or if node does not exists in the subtree rooted at root.  
	 * @param root
	 * @param aNode
	 * @return
	 */
	public static Deque<TreeNode> getParents(TreeNode root, TreeNode aNode) {
		Deque<TreeNode> result = new ArrayDeque<TreeNode>();

		// add parents to the deque
		getParents(result, root, aNode);
		return result;
		
	}

	private static boolean getParents(Deque<TreeNode> parents, TreeNode root,
			TreeNode node) {
		
		// bail out if invalid input is received leaving list untouched
		if (root == null || node == null) {
			return false;
		}
		
		parents.push(root);
		if (root == node) {
			// terminating condition for recursion
			return true;
		}
		if (getParents(parents, root._left, node) || getParents(parents, root._right, node)) {
			return true;
		}
		else {
			// node isn't under this subtree
			parents.pop();
			return false;
		}
	}

	/**
	 * Assuming that this node is the root of a tree returns the lowest common ancestor for two nodes  
	 * @param first a node in the subtree below this node
	 * @param second a node in the subtree below this node
	 * @return if root is null or if first and/or second is not under the root
	 */
	public static TreeNode commonAncestor(TreeNode root, TreeNode first, TreeNode second) {
		
		if (root == null) {
			return root;
		}
		/*
		 * We are not doing trivial optimizations, e.g. first==second or first==null etc becuase
		 * in that case we may return first/second even if they are not under root.  That is
		 * unlikely to be a desired return.
		 */
		Deque<TreeNode> firstParents = Trees.getParents(root, first);
		_LOGGER.info("Parents of node " + first + " are: " + firstParents);
		
		Deque<TreeNode> secondParents = Trees.getParents(root, second);
		_LOGGER.info("Parents of node " + second + " are: " + secondParents);
		/*
		 * both lists must start at the respective nodes and end at the root
		 * We can use that to assume and simplify finding first common element
		 */
		TreeNode result = null;
		boolean done = false;
		while (!(firstParents.isEmpty() || secondParents.isEmpty() || done)) {
			TreeNode a = firstParents.removeLast();
			TreeNode b = secondParents.removeLast();
			if (a == b) {
				result = a;
			}
			else {
				// we are looking for the first mismatch
				done = true;
			}
		}
		
		return result;
	}
	private static final Logger _LOGGER = LoggerFactory.getLogger(Trees.class);
}
