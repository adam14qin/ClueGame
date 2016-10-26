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
		if(initials.length()>0)
		{
		this.initial = initials.charAt(0);
		} else {
			this.initial = 'n'; 
		}
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
		return (!isWalkway());
	}
	
	public boolean isDoorway() {
		return (doorDirection != DoorDirection.NONE);
	}
	
	
	public DoorDirection getDoorDirection(){
		return doorDirection;
	}

	@Override
	public boolean equals(Object obj) {
		BoardCell other = (BoardCell) obj;
		System.out.println(row + " " + other.row);
		System.out.println(column + " " + other.column);
		if (column != other.column)
		{
			return false;
		}
		if (row != other.row)
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + ", initial=" + initial + ", doorDirection="
				+ doorDirection + "]";
	}
	
	
	
	
}
