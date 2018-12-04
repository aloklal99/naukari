# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

class Solution:
    def insert(self, root, val):
        if val < root.val:
            if root.left:
                self.insert(root.left, val)
            else:
                root.left = TreeNode(val)
        else:
            if root.right:
                self.insert(root.right, val)
            else:
                root.right = TreeNode(val)

    def insertNums(self, arr, start, end, root):
        if end > start:
            mid = int((start+end)/2)  # start + int(len(end-start)/2)
            if not root:
                root = TreeNode(arr[mid])
            else:
                self.insert(root, arr[mid])
            self.insertNums(arr, start, mid, root)
            self.insertNums(arr, mid+1, end, root)
        return root
            
    def sortedArrayToBST(self, nums):
        """
        :type nums: List[int]
        :rtype: TreeNode
        """
        if nums:
            return self.insertNums(nums, 0, len(nums), None)
        else:
            return None