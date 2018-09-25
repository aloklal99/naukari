package alok.naukari.trees;

import java.util.ArrayList;
import java.util.List;

public class CommonAncestorHasParentPtr {
    public static void main( String[] args ) {
    	
	}
    
    TreeNode commonAncestor(TreeNode first, TreeNode second) {
    	if (first == second) {
    		return first;
    	}
    	else if (first == null) {
    		return second;
    	}
    	else if (second == null) {
    		return first;
    	}
    	else if (first.parentOf(second)) {
    		return first;
    	}
    	else if (second.parentOf(first)) {
    		return second;
    	}
    	else {
    		List<TreeNode> firstParents = first.getParents();
    		List<TreeNode> secondParents = second.getParents();
    		TreeNode node = findCommon(firstParents, secondParents);
    		return node;
    	}
    }

	TreeNode findCommon(List<TreeNode> firstParents,
			List<TreeNode> secondParents) {
		int i = 0;
		while (firstParents.get(i) == secondParents.get(i)) {
			i++;
		}
		return firstParents.get(--i);
	}

	public class TreeNode {
		TreeNode _left;
		TreeNode _right;
		Object value;
		TreeNode _parent;
		
		public boolean parentOf(TreeNode node) {
			if (node == null) {
				return false;
			}
			if (node == _left || node == _right) {
				return true;
			}
			return _left.parentOf(node) || _right.parentOf(node);
		}
		
		public List<TreeNode> getParents() {
			List<TreeNode> result = new ArrayList<TreeNode>();
			
			TreeNode n = this;
			do {
				result.add(n);
				n = n._parent;
				// parent of the root node would be null 
			} while (n != null);
			
			return result;
		}
	}
}
