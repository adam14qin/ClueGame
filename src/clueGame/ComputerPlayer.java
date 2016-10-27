package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import jdk.internal.org.objectweb.asm.Handle;

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
	
	@Override
	public Solution makeSuggestion(Board board)
	{
		Random rand = new Random();
		Solution suggestion; 
		Card playerGuess; 
		Card weaponGuess;
		Card roomGuess = new Card('R', board.getLegend().get(board.getCellAt(11, 3).getInitial())); 
		do {
		ArrayList<Card> weapons = board.getUnseen().get(CardType.WEAPON);
		System.out.println(weapons.toString());
		weaponGuess = weapons.get(rand.nextInt(weapons.size())); 
		ArrayList<Card> people = board.getUnseen().get(CardType.PERSON);
		playerGuess = people.get(rand.nextInt(people.size())); 
		System.out.println(hand.toString());
		System.out.println(weaponGuess.toString());
		System.out.println(playerGuess.toString());
		
		}while(hand.contains(weaponGuess) && hand.contains(playerGuess));
		
		return new Solution(weaponGuess, playerGuess, roomGuess); 
	}
	
	@Override
	public Card disproveSuggestion(Board board, Solution suggestion) {
		return new Card('P', "temp"); 
	}
	
	public void setLastRoomVisited(char c){
		lastRoomVisited = c; 
	}

}
