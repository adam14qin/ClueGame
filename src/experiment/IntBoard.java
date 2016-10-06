package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import clueGame.BoardCell;

public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;

	// For the purposes of this assignment, test a 4x4 board
	private int rows = 4, cols = 4;

	public IntBoard() {
		// Initialize Instance Data Structures
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		grid = new BoardCell[rows][cols];
		
		// Add BoardCell(s) to grid
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				grid[i][j] = new BoardCell(i, j, "");
		
		// Calculate Board Adjacencies (this may need to be moved elsewhere...)
		calcAdjacencies();
	}
	
	public BoardCell getCell(int row, int col) {
		// Out of Bounds
		if (row < 0 || row >= rows || col < 0 || col >= cols)
			return null;
		
		return grid[row][col];
	}
	
	private void calcAdjacencies() {
		// Complexity: O(n)-> n=number of cells
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				// Get cell of interest
				BoardCell bc = grid[i][j];
				
				// Ensure entry exists in map
				if (!adjMtx.containsKey(bc))
					adjMtx.put(bc, new HashSet<BoardCell>());
				
				// Add left, right, up, down cells conditionally
				if (i > 0) adjMtx.get(bc).add(grid[i-1][j]);
				if (i < cols - 1) adjMtx.get(bc).add(grid[i+1][j]);
				if (j > 0) adjMtx.get(bc).add(grid[i][j-1]);
				if (j < rows - 1) adjMtx.get(bc).add(grid[i][j+1]);
			}
		}
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		// Empty out the visited/targets lists
		visited.clear();
		targets.clear();
		
		// Add the start location to visited
		visited.add(startCell);
		
		// Kick off the recursion
		findAllTargets(startCell, pathLength);
	}
	
	private void findAllTargets(BoardCell startCell, int pathLength) {
		// Iterate through adjacent BoardCell(s)
		for (BoardCell adj : adjMtx.get(startCell)) {
			// Don't double back on our path
			if (visited.contains(adj))
				continue;
			
			// Protect ourselves from doubling back in the next round
			visited.add(adj);
			
			// Add a target if we expired the pathLength counter, otherwise recurse
			if (pathLength == 1)
				targets.add(adj);
			else
				findAllTargets(adj, pathLength - 1);
			
			// Clean adjacent cell out of visited set
			visited.remove(adj);
		}
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public Set<BoardCell> getAdjList(BoardCell bc) {
		return adjMtx.get(bc);
	}

}
