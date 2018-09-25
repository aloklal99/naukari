package alok.naukari.trees;

public class TreeNode {
	TreeNode _left;
	TreeNode _right;
	int _value;
	
	public TreeNode(int value) {
		_value = value;
		_left = null;
		_right = null;
	}
	
	public boolean isLeaf() {
		return _left == null && _right == null;
	}
	
	/**
	 * Adds the supplied <code>node</code> to the tree rooted at this node.  Node
	 * addition is done such that tree remains a BST.  Duplicates are allowed
	 * @param node
	 * @return the added node
	 */
	public TreeNode add(TreeNode node) {
		if (node == null) {
			// weird!  Why is the node being added null?
			return node;
		}
		if (node._value < this._value) {
			if (this._left == null) {
				this._left = node;
			}
			else {
				_left.add(node);
			}
		}
		else {
			if (this._right == null) {
				this._right = node;
			}
			else {
				_right.add(node);
			}
		}
		
		return node;
	}

	public void print() {
		System.out.println(_value);
	}
	
	public String toString() {
		return String.format("[ (L) %s <- { %s } -> %s (R) ]", 
				_left == null ? "null" : Integer.toString(_left._value),
				Integer.toString(_value),
				_right == null ? "null" : Integer.toString(_right._value));
	}
}
