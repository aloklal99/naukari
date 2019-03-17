class Solution:
    def bft(self, todo):
        level = 0
        while level < len(todo):
            for node, rank in todo[level]:
                if node.left:
                    todo[level+1].append((node.left, rank*2))
                if node.right:
                    todo[level+1].append((node.right, rank*2+1))
            level = level + 1
    
    def widthOfBinaryTree(self, root):
        """
        :type root: TreeNode
        :rtype: int
        """
        todo = collections.defaultdict(list)
        if root:
            todo[0].append((root, 0))
            self.bft(todo)
        return max([nodes[~0][1] - nodes[0][1] + 1 for level, nodes in todo.items()], default=0)

class Solution_depth_first:
    def preorder(self, node, level, rank):
        if node:
            self.preorder(node.left, level+1, 2*rank)        
            self.ans = max(self.ans, rank - self.left.setdefault(level, rank) + 1)
            self.preorder(node.right, level+1, 2*rank+1)        
        
    def widthOfBinaryTree(self, root):
        """
        :type root: TreeNode
        :rtype: int
        """
        self.left = {}
        self.ans = 0
        self.preorder(root, 0, 0)
        return self.ans

class Solution_breadth_first:
    def widthOfBinaryTree(self, root):
        """
        :type root: TreeNode
        :rtype: int
        """
        ans = 0
        q = []
        q.append((root, 0, 0))
        left = {}
        for node, rank, level in q:
            if node:
                ans = max(ans, rank - left.setdefault(level, rank) + 1)
                q.append((node.left, 2*rank, level+1))
                q.append((node.right, 2*rank+1, level+1))
        return ans