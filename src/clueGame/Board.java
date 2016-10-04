package clueGame;

import java.util.Map;

public class Board {
	public static final int MAX_BOARD_SIZE = 50;
	private int numRows, numColumns;
	private BoardCell[][] board;
	private Map<Character, String> rooms;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();

	// ctor is private to ensure only one can be created
	private Board() {}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
	
	public BoardCell getCellAt(int row, int column) {
		return null;
	}
	
	public Map<Character, String> getLegend() {
		return rooms;
	}
	
	public void setConfigFiles(String layoutFileName, String legendFileName) {
		
	}

	public void initialize() {
		
	}
	
	public void loadRoomConfig() {
		
	}
	
	public void loadBoardConfig() {
		
	}
	
	public void calcAdjacencies() {
		
	}
	
	public void calcTargets(BoardCell cell, int pathLength) {
		
	}
}
