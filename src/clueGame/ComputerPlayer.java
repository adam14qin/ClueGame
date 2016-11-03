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
		Card roomGuess = new Card(CardType.ROOM, board.getLegend().get(board.getCellAt(row, column).getInitial())); 
		do {
		ArrayList<Card> weapons = board.getUnseen().get(CardType.WEAPON);
		weaponGuess = weapons.get(rand.nextInt(weapons.size())); 
		ArrayList<Card> people = board.getUnseen().get(CardType.PERSON);
		playerGuess = people.get(rand.nextInt(people.size())); 
		
		}while(hand.contains(weaponGuess) && hand.contains(playerGuess));
		
		return new Solution(weaponGuess, playerGuess, roomGuess); 
	}
	
	@Override
	public Card disproveSuggestion(Board board, Solution suggestion) {
		Random rand = new Random();
		ArrayList<Card> iHave = new ArrayList<>(); 
		
		if(hand.contains(suggestion.getPlayer()))
		{
			iHave.add(suggestion.getPlayer());
		}
		if(hand.contains(suggestion.getWeapon()))
		{
			iHave.add(suggestion.getWeapon());
		}
		if(hand.contains(suggestion.getRoom()))
		{
			iHave.add(suggestion.getRoom());
		}
		
		return (iHave.size()==0) ? null : iHave.get(rand.nextInt(iHave.size())); 
	}
	
	public void setLastRoomVisited(char c){
		lastRoomVisited = c; 
	}

}
