package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Board {
	public static final int MAX_BOARD_SIZE = 50;
	private int numRows, numColumns;
	private BoardCell[][] board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
	private Map<Character, String> rooms = new HashMap<Character, String>();
	private String roomConfigName;
	private String boardConfigName;
	
	// Variable used for singleton pattern
	private static Board theInstance = new Board();

	// Constructor is private to ensure only one can be created
	private Board() {}

	// This method returns the only Board
	public static Board getInstance() { return theInstance; }
	
	// Instance getters
	public int getNumRows() { return numRows; }
	public int getNumColumns() { return numColumns;	}
	public Map<Character, String> getLegend() { return rooms; }
	
	// Return the BoardCell at (row, column)
	public BoardCell getCellAt(int row, int column) {
		if (row < numRows && column < numColumns && row >= 0 && column >= 0)
			return board[row][column];
		else
			return null;
	}
	
	// Store the legend and layout configuration file names
	public void setConfigFiles(String layoutFileName, String legendFileName) {
		roomConfigName = legendFileName;
		boardConfigName = layoutFileName;
	}

	// Read from the configuration files and initialize the Board
	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
		} catch (BadConfigFormatException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// Read the legend file
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException{
		// File reader objects
		FileReader reader = new FileReader(roomConfigName);
		Scanner in = null;
		
		try {
			// Initialize file scanner
			in = new Scanner(reader);
			
			// Iterate through definitions of rooms
			while(in.hasNextLine()){
				String theLine = in.nextLine();
				String[] theChunks = theLine.split(", ");
				
				// Error condition: Row of legend doesn't have 3 comma separated values
				if (theChunks.length != 3)
					throw new BadConfigFormatException("Invalid legend entry: " + theLine);
				
				// Error condition: Type of room is not 'Other' or 'Card'
				else if (!(theChunks[2].equals("Other") || theChunks[2].equals("Card")))
					throw new BadConfigFormatException("Invalid room type: " + theChunks[2]);
				
				// Store the room into the map
				rooms.put(theChunks[0].charAt(0), theChunks[1]);
			}
		} finally {
			// Cleanup the scanner, even if an exception was thrown
			if (in != null)
				in.close();
		}
	}
	
	// Read the layout file
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		// File reader objects
		FileReader reader = new FileReader(boardConfigName);
		Scanner in = null;
		numColumns = -1;
		
		try {
			// Initialize the file scanner
			in = new Scanner(reader);
			
			// Iterate through rows of layout file
			while(in.hasNextLine()){
				String theLine = in.nextLine();
				String[] elements = theLine.split(",");
				
				// If the number of columns is unknown, set it to the length of this row
				if (numColumns == -1)
					numColumns = elements.length;
				
				// Error condition: more rows or columns than the absolute maximum
				if (elements.length >= MAX_BOARD_SIZE || numRows >= MAX_BOARD_SIZE)
					throw new BadConfigFormatException("Number of rows/columns exceeded maximum of " + MAX_BOARD_SIZE);
				
				// Error condition: row has different number of elements than the first row
				else if (elements.length != numColumns)
					throw new BadConfigFormatException("Inconsistent number of columns on row " + numRows);
				
				// Iterate through columns
				for (int i = 0; i < elements.length; i++) {
					
					// Error condition: string representation of cell is null or empty
					if (elements[i] == null || elements[i].length() == 0)
						throw new BadConfigFormatException("Invalid board cell specifier found at (" + numRows + ", " + i + ")");
					
					// Error condition: string representation references unknown room
					else if (!rooms.containsKey(elements[i].charAt(0)))
						throw new BadConfigFormatException("Unknown room specifier found: " + elements[i].charAt(0));
					
					// Add a new BoardCell to the Board
					board[numRows][i] = new BoardCell(numRows, i, elements[i]);					
				}
				
				// Go to the next row
				numRows++;
			}
		} finally {
			// Cleanup the scanner, even if an exception was thrown
			if (in != null)
				in.close();
		}
	}
	
	public void calcAdjacencies() {
		
	}
	
	public void calcTargets(BoardCell cell, int pathLength) {
		
	}
}
