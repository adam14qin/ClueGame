package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class Player {

	private String playerName;
	private Color color; 
	int row; 
	int column; 
	private ArrayList<Card> hand; 
	
	public Player(String playerName,int row, int column, Color color)
	{
		hand = new ArrayList<>(); 
		this.playerName = playerName; 
		this.row = row;
		this.column = column; 
		this.color = color;
	}
}
