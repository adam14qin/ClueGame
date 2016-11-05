package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Scanner;

import javax.swing.JPanel;

public class BoardCell {
	private static final char walkway = 'W';
	
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDirection;
	private static final int DOOR_SIZE = 5; 
	
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
		//Added equals for comparing
		BoardCell other = (BoardCell) obj;
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

	public void draw(JPanel boardPanel, Graphics g) {
		System.out.println(" ROW: " + row + " COL: " + column + " Initial: " + initial);
		int pixelRow = row*ClueGame.CELL_PIXEL_SIZE; 
		int pixelCol = column*ClueGame.CELL_PIXEL_SIZE;
		if(initial == 'X')
		{
			g.setColor(Color.RED);
			g.fillRect(pixelCol, pixelRow, ClueGame.CELL_PIXEL_SIZE, ClueGame.CELL_PIXEL_SIZE);
		}
		if(isRoom() && initial != 'X')
		{
			
			g.setColor(Color.darkGray);
			g.fillRect(pixelCol, pixelRow, ClueGame.CELL_PIXEL_SIZE, ClueGame.CELL_PIXEL_SIZE);
			g.setColor(Color.cyan);
			switch(doorDirection)
			{
			case UP:
			{
				g.fillRect(pixelCol, pixelRow, ClueGame.CELL_PIXEL_SIZE, DOOR_SIZE);
				break;
			}
			case DOWN:
			{
				g.fillRect(pixelCol, pixelRow + ClueGame.CELL_PIXEL_SIZE-DOOR_SIZE, ClueGame.CELL_PIXEL_SIZE, DOOR_SIZE);
				break;
			}
			case RIGHT:
			{
				g.fillRect(pixelCol+ ClueGame.CELL_PIXEL_SIZE-DOOR_SIZE, pixelRow, DOOR_SIZE, ClueGame.CELL_PIXEL_SIZE);
				break;
			}
			case LEFT:
			{
				g.fillRect(pixelCol, pixelRow, DOOR_SIZE, ClueGame.CELL_PIXEL_SIZE);
				break;
			}
			}
		}
		if(isWalkway())
		{
			g.setColor(Color.YELLOW);
			g.fillRect(pixelCol, pixelRow, ClueGame.CELL_PIXEL_SIZE, ClueGame.CELL_PIXEL_SIZE);
			g.setColor(Color.BLACK);
			g.drawRect(pixelCol, pixelRow, ClueGame.CELL_PIXEL_SIZE, ClueGame.CELL_PIXEL_SIZE);
		}
	}
	
	
	
	
}
