package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BoardCell getMove(Set<BoardCell>targets) {
		// TODO Auto-generated method stub
		return new BoardCell(0,0,"");
	}

}
