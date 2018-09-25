package alok.naukari.trees;

public class TreePrinter {
	
	public static abstract class Cell {
		static Cell Empty = new EmptyCell();
		static Cell Slash = new SlashCell();
		static Cell BackSlash = new BackSlashCell();
		static Cell UnderScores = new UnderScoresCell();
		
		abstract public String toString();
		
		protected String formatString(String str, boolean leftJustified) {
			return String.format("%" + (leftJustified?"-":"") + _Width + "s", str);
		}
		
		protected String formatString(String str) {
			// by default strings are not left justified in the cell
			return formatString(str, false);
		}
		
		static int _Width = 0;
	}
	
	public static class NumberCell extends Cell {
		
		final int _value;
		public NumberCell(int value) {
			_value = value;
		}

		public String toString() {
			String str = formatString(Integer.toString(_value));
			return str;
		}
		
	}
	
	public static class EmptyCell extends Cell {
		
		public String toString() {
			String str = formatString("");
			return str;
		}
	}
	
	public static class SlashCell extends Cell {
		public String toString() {
			String str = formatString("/");
			
			return str;
		}
	}
	
	public static class BackSlashCell extends Cell {
		public String toString() {
			String str = formatString("\\", true);
			
			return str;
		}
	}
	
	public static class UnderScoresCell extends Cell {
		static String _underScores = null;
		public String toString() {
			if (_underScores == null) {
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < Cell._Width; i++) {
					builder.append('_');
				}
				_underScores = builder.toString();
			}
			return _underScores;
		}
	}
	
	Cell[][] _cells;

	void print(TreeNode node) {
		int height = Trees.heightOf(node);
		int cellWidth = Trees.maxValueWidth(node);
		Cell._Width = cellWidth;

		// we are screwed if tree height is more than 15!
		int cellsPerRow = (1 << height) - 1;  // (2^height)-1
		// we need one row for connectors for each row of nodes (except for the last row) hence (height*2-1)
		_cells = new Cell[height*2-1][cellsPerRow];
		initializeCells(_cells);

		// start filling in the matrix starting at the root
		int row = 0;  // root node is at the top of the matrix
		int position = rootCellPosition(height); 
		populateCells(node, row, position, height);
		printCells();
	}
	
	// set all cells to empty
	void initializeCells(final Cell[][] matrix) {
		for (Cell[] row: matrix) {
			for (int i = 0; i < row.length; i++) {
				row[i] = Cell.Empty;
			}
		}
	}

	void printCells() {
		System.out.println("---Tree Start---");

		for (int i = 0; i < _cells.length; i++) {
			Cell[] row = _cells[i];
			for (int j = 0; j < row.length; j++) {
				System.out.print(row[j].toString());
			}
			System.out.println();
		}
		
		System.out.println("---Tree End ---");
	}

	/**
	 * 
	 * @param node node which is to be populated
	 * @param height of that node
	 * @param position position at this this node should be for its level
	 */
	void populateCells(final TreeNode node, final int row, final int position, final int height) {
		if (node == null) {
			return;
		}
		
		int col = position - 1;
		_cells[row][col] = new NumberCell(node._value);

		// populate children!
		int childHeight = height - 1;
		int childRow = row + 2; // next row is for slashes
		
		TreeNode child = node._left; 
		int childPosition = leftChildPosition(position, height);
		if (child != null) {
			drawConnectorToChild(row, position, childPosition);
			populateCells(child, childRow, childPosition, childHeight);
		}
		
		child = node._right;
		childPosition = rightChildPosition(position, height);
		if (child != null) {
			drawConnectorToChild(row, position, childPosition);
			populateCells(child, childRow, childPosition, childHeight);
		}
	}

	void drawConnectorToChild(final int parentRow, final int parentPosition, int childPosition) {
		boolean leftChild = true;
		if (childPosition > parentPosition) {
			leftChild = false; // it is the right child
		}
		// the row with slashes is hte one below the parent
		int connectorRow = parentRow + 1;
		// slash is right above the child
		int column = childPosition - 1; // zero based indexing
		if (leftChild) {
			_cells[connectorRow][column] = Cell.Slash;
		}
		else { // right child
			_cells[connectorRow][column] = Cell.BackSlash;
		}
		
		// draw horizonal connectors now.
		int start, end;
		if (leftChild) {
			start = childPosition; // we start one cell left of child, thus index (being 0 based) would be same as childPosition
			end = parentPosition - 2; // we top at the cell left of parent cell, thus (0 based) index would be one less than parentPosition
		}
		else {
			start = parentPosition; // we start the cell right of parent
			end = childPosition - 2; // child is at childPosition -1 and we want to stop one cell left of it.
		}
		// all underscores are at same level as parent
		for (int i = start; i <= end; i++) {
			_cells[parentRow][i] = Cell.UnderScores;
		}
	}

	int rootCellPosition(int height) {
		// 2^(height-1)
		int pos = 1 << (height - 1);
		return pos;
	}
	
	int cellDistanceForLevel(final int height) {
		// 2^n
		int distance = 1 << height;
		return distance;
	}
	
	int leftChildPosition(final int parentPosition, final int parentHeight) {
		int childHeight = parentHeight - 1;
		int dist = cellDistanceForLevel(childHeight) / 2;
		int childPos = parentPosition - dist;
		
		return childPos;
	}
	
	int rightChildPosition(final int parentPosition, final int parentHeight) {
		int childHeight = parentHeight - 1;
		int dist = cellDistanceForLevel(childHeight);
		int childPos = leftChildPosition(parentPosition, parentHeight) + dist;
		
		return childPos;
	}
}
