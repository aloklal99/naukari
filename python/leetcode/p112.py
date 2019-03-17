# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

class Solution:
    def isLeaf(self, node):
        return not node.left and not node.right
    
    def hasPathSum_(self, root, s):
        remainder = s - root.val
        if self.isLeaf(root):
            return remainder == 0
        if root.left:
            if self.hasPathSum_(root.left, remainder):
                return True
        if root.right:
            if self.hasPathSum_(root.right, s - root.val):
                return True
        return False

    def hasPathSum(self, root, s):
        """
        :type root: TreeNode
        :type sum: int
        :rtype: bool
        """
        if root:
            return self.hasPathSum_(root, s)
        else:
            return False