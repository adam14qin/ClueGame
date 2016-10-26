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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + column;
		result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		Player other = (Player) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (column != other.column)
			return false;
		if (playerName == null) {
			if (other.playerName != null)
				return false;
		} else if (!playerName.equals(other.playerName))
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Player [playerName=" + playerName + ", color=" + color + ", row=" + row + ", column=" + column
				+ ", hand=" + hand + "]";
	}
	
	public ArrayList<Card> getHand() {
		return hand; 
	}
	
	public String getName() {
		return playerName;
	}
	
	
	
}
