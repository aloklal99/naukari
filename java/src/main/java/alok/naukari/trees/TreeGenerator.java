package alok.naukari.trees;

import java.util.Random;

public class TreeGenerator {
	/**
	 * Generates a random BST of specified number of nodes
	 * @param numNodes
	 * @return
	 */
	public TreeGenerator() {
		_random = new Random();
	}
	
	public TreeGenerator(long seed) {
		_random = new Random(seed);
	}
	
	public TreeGenerator(Random random) {
		_random = random;
	}
	
	/**
	 * If <code>numNodes</code> is 0 then tree returned would be a null node!
	 * @param numNodes
	 * @return
	 */
	public TreeNode generateBST(int numNodes) {
		TreeNode root = null;
		for (int i = 0; i < numNodes; i++) {
			int value = nextValue();
			TreeNode node = new TreeNode(value);
			if (root == null) {
				root = node;
			}
			else {
				root.add(node);
			}
		}
		
		return root;
	}
	
	int nextValue() {
		return _random.nextInt(100);
	}
	
	// for determinizm
	Random _random;
}
