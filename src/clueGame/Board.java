package clueGame;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.lang.reflect.Field;

public class Board {
	public static final int MAX_BOARD_SIZE = 50;
	private int numRows, numColumns;
	private BoardCell[][] board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
	private Map<Character, String> rooms = new HashMap<Character, String>();
	private String roomConfigName;
	private String boardConfigName;
	private String playerConfigName; 
	private String weaponConfigName; 
	
	private ArrayList<Player> players = new ArrayList<>(); 
	private Map<BoardCell, Set<BoardCell>> adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	
	private ArrayList<Card> deck = new ArrayList<>();
	
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
	public void setConfigFiles(String layoutFileName, String legendFileName, String weaponFileName, String playerFileName) {
		roomConfigName = legendFileName;
		boardConfigName = layoutFileName;
		weaponConfigName = weaponFileName; 
		playerConfigName = playerFileName;
	}

	// Read from the configuration files and initialize the Board
	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
			loadCardConfig(); 
		} catch (BadConfigFormatException | FileNotFoundException e) {
			e.printStackTrace();
		}
		
		calcAdjacencies();
	}
	
	// Read the legend file
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException{
		// File reader objects
		FileReader reader = new FileReader(roomConfigName);
		Scanner in = null;
		rooms.clear();
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
		numColumns = -1; numRows = 0;
		
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
	
	public void loadPlayerConfig() throws FileNotFoundException, BadConfigFormatException
	{
		// File reader objects
				FileReader reader = new FileReader(playerConfigName);
				Scanner in = null;
				try {
					// Initialize file scanner
					in = new Scanner(reader);
					
					// Iterate through definitions of rooms
					while(in.hasNextLine()){
						String theLine = in.nextLine();
						String[] theChunks = theLine.split(", ");
						
						// Error condition: Row of legend doesn't have 3 comma separated values
						if (theChunks.length != 4)
							throw new BadConfigFormatException("Invalid legend entry: " + theLine);
						
						// Error condition: Type of room is not 'Other' or 'Card'
						
						// Store the room into the map
						deck.add(new Card('P', theChunks[0])); 
						players.add(new Player)
					}	
				for(char x : rooms.keySet())
				{
					if(x != 'W' && x!= 'X')
					deck.add(new Card('R', rooms.get(x))); 
				}
				} finally {
					// Cleanup the scanner, even if an exception was thrown
					if (in != null)
						in.close();
				}
	}
	
	private void calcAdjacencies() {
		// Complexity: O(n)-> n=number of cells
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				// Get cell of interest
				BoardCell bc = board[i][j];
				
				// Ensure entry exists in map
				if (!adjMtx.containsKey(bc))
					adjMtx.put(bc, new HashSet<BoardCell>());
				
				// Add left, right, up, down cells conditionally
				// If inside a walkway
				if (board[i][j].isWalkway()){

					if (i > 0 && (board[i-1][j].isWalkway() || (board[i-1][j].isDoorway() && board[i-1][j].getDoorDirection() == DoorDirection.DOWN))) 				
						adjMtx.get(bc).add(board[i-1][j]);		
					if (i < numRows - 1 && (board[i+1][j].isWalkway() || (board[i+1][j].isDoorway() && board[i+1][j].getDoorDirection() == DoorDirection.UP))) 	
						adjMtx.get(bc).add(board[i+1][j]);
					if (j > 0 && (board[i][j-1].isWalkway() || (board[i][j-1].isDoorway() && board[i][j-1].getDoorDirection() == DoorDirection.RIGHT))) 				
						adjMtx.get(bc).add(board[i][j-1]);
					if (j < numColumns - 1 && (board[i][j+1].isWalkway() || (board[i][j+1].isDoorway() && board[i][j+1].getDoorDirection() == DoorDirection.LEFT))) 
						adjMtx.get(bc).add(board[i][j+1]);
				}
				
				else if (board[i][j].isDoorway()){
					if (i > 0 && board[i-1][j].isWalkway()) 				
						adjMtx.get(bc).add(board[i-1][j]);			
					if (i < numRows - 1 && board[i+1][j].isWalkway()) 		
						adjMtx.get(bc).add(board[i+1][j]);
					if (j > 0 && board[i][j-1].isWalkway()) 				
						adjMtx.get(bc).add(board[i][j-1]);
					if (j < numColumns - 1 && board[i][j+1].isWalkway()) 	
						adjMtx.get(bc).add(board[i][j+1]);
				}
			}
		}
	}
	
	public void calcTargets(int row, int column, int pathLength) {
		// Empty out the visited/targets lists
		visited.clear();
		targets.clear();
		
		// Add the start location to visited
		BoardCell startCell = board[row][column];
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
			if (pathLength == 1 || adj.isDoorway())
				targets.add(adj);
			else
				findAllTargets(adj, pathLength - 1);
			
			// Clean adjacent cell out of visited set
			visited.remove(adj);
		}
	}
	
	public Set<BoardCell> getAdjList(int row, int column) {
		return adjMtx.get(board[row][column]);
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	} 
	
	public ArrayList<Card> getDeck()
	{
		return deck; 
	}
	
	public Color convertColor(String strColor) {
	    Color color; 
	    try {     
	        // We can use reflection to convert the string to a color
	        Field field = Class.forName("java.awt.Color").getField(strColor.trim().toLowerCase());     
	        color = (Color)field.get(null); 
	    } catch (Exception e) {  
	        color = null; // Not defined  
	    }
	    return color;
	}
}
