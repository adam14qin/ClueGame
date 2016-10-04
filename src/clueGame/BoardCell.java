package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char inital;
	private DoorDirection doorDirection;
	
	public BoardCell(int row, int col) {
		this.row = row;
		this.column = col;
	}
	
	public char getInitial() {
		// TODO
		return 0;
	}
	
	public boolean isWalkway() {
		// TODO
		return false;
	}
	
	public boolean isRoom() {
		// TODO
		return false;
	}
	
	public boolean isDoorway() {
		// TODO
		return false;
	}
	
	public DoorDirection getDoorDirection(){
		// TODO
		return DoorDirection.NONE;
	}
}
