# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None
import functools

class Solution:
    @functools.lru_cache(maxsize=128)
    def height(self, root):
        if root:
            return 1 + max(self.height(root.left), self.height(root.right))
        else:
            return 0

    def isBalanced(self, root):
        """
        :type root: TreeNode
        :rtype: bool
        """
        if root:
            return self.isBalanced(root.left) and self.isBalanced(root.right) and abs(self.height(root.left) - self.height(root.right)) < 2
        else:
            return True
        