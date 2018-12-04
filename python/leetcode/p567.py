class Solution:
    def unexplored(self):
        for i, row in enumerate(self.grid):
            for j, cell in enumerate(row):
                if int(self.grid[i][j]) and (i, j) not in self.explored:
                    yield (i, j)

    def neighbours(self, node):
        i, j = node
        nbrs = []
        if i > 0 and int(self.grid[i - 1][j]):
            nbrs.append((i - 1, j))
        if i < len(self.grid) - 1 and int(self.grid[i + 1][j]):
            nbrs.append((i + 1, j))
        if j > 0 and int(self.grid[i][j - 1]):
            nbrs.append((i, j - 1))
        if j < len(self.grid[i]) - 1 and int(self.grid[i][j + 1]):
            nbrs.append((i, j + 1))
        return nbrs

    def explore(self, node):
        self.explored.add(node)
        for nbr in self.neighbours(node):
            if nbr not in self.explored:
                self.explore(nbr)

    def numIslands(self, grid):
        """
        :type grid: List[List[str]]
        :rtype: int
        """
        self.grid = grid
        self.explored = set()
        num = 0
        for node in self.unexplored():
            num += 1
            self.explore(node)
        return num
