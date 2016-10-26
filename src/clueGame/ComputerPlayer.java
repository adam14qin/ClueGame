package clueGame;

import java.awt.Color;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {

	private char lastRoomVisited; 
	
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BoardCell getMove(Set<BoardCell>targets) {
		for(BoardCell x : targets)
		{
			if(x.isRoom() && x.getInitial()!=lastRoomVisited)
			{
				lastRoomVisited = x.getInitial(); 
				return x; 
			}
		}
		Random rand = new Random();
		return (BoardCell) targets.toArray()[rand.nextInt(targets.size())]; 
	}

}
