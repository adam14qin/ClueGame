package clueGame;

public class BoardCell {
	private static final char walkway = 'W';
	
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDirection;
	
	// Parameterized constructor
	public BoardCell(int row, int col, String initials) {
		this.row = row;
		this.column = col;
		
		// First character is the room name, second character is the door direction
		this.initial = initials.charAt(0);
		doorDirection = (initials.length() > 1 ? DoorDirection.fromCharacter(initials.charAt(1)) : DoorDirection.NONE);
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public char getInitial() {
		return initial;
	}
	
	public boolean isWalkway() {
		return (initial == walkway);
	}
	
	public boolean isRoom() {
		return (initial != walkway);
	}
	
	public boolean isDoorway() {
		return (doorDirection != DoorDirection.NONE);
	}
	
	public DoorDirection getDoorDirection(){
		return doorDirection;
	}
}
