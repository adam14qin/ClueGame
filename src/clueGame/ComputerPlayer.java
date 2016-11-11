package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import clueGame.Solution.typeSolution;
import jdk.internal.org.objectweb.asm.Handle;

public class ComputerPlayer extends Player {

	private char lastRoomVisited; 
	private Set<Character> roomsVisited; 
	
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		roomsVisited = new TreeSet<Character>(); 
		// TODO Auto-generated constructor stub
	}

	@Override
	public BoardCell getMove(Set<BoardCell>targets) {
		ArrayList<BoardCell> locations = new ArrayList<BoardCell>();
		for(BoardCell x : targets)
		{
			if(x.isRoom() && !roomsVisited.contains(x.getInitial()))
			{
				lastRoomVisited = x.getInitial(); 
				roomsVisited.add(x.getInitial()); 
				return x; 
			}
			else locations.add(x);
		}
		Random rand = new Random();
		int random = rand.nextInt(locations.size());
		return locations.get(random); 
	}
	
	@Override 
	public Solution moveToSpot(BoardCell spot, Board board, boolean onlyMove)
	{
		
		super.moveToSpot(spot, board, true);
		if(onlyMove)
		{
			return null;
		}
		Solution accusation = makeAccusation(board); 
		if(accusation == null)
		{
			if(spot.isRoom())
			{
				Solution suggestion = makeSuggestion(board); 
				return suggestion; 
			}
			else
				return null; 
		} else {
			return makeAccusation(board); 
		}
		
	}
	
	@Override
	public Solution makeAccusation(Board board)
	{
		ArrayList<Card> allUnseen = new ArrayList<>();
		for(CardType type : board.getUnseen().keySet())
		{
			for(Card card : board.getUnseen().get(type))
			{
				allUnseen.add(card); 
			}
		}
		if(allUnseen.size() == 3)
		{
		Card playerGuess = board.getUnseen().get(CardType.PERSON).get(0); 
		Card roomGuess = board.getUnseen().get(CardType.ROOM).get(0); 
		Card weaponGuess = board.getUnseen().get(CardType.WEAPON).get(0); 
		return new Solution(weaponGuess, playerGuess, roomGuess, typeSolution.ACCUSATION); 
		}
		else
			return null;
	}
	
	@Override
	public Solution makeSuggestion(Board board)
	{
		Random rand = new Random();
		Card playerGuess; 
		Card weaponGuess;
		Card roomGuess = new Card(CardType.ROOM, board.getLegend().get(board.getCellAt(row, column).getInitial())); 
		do {
		ArrayList<Card> weapons = board.getUnseen().get(CardType.WEAPON);
		weaponGuess = weapons.get(rand.nextInt(weapons.size())); 
		ArrayList<Card> people = board.getUnseen().get(CardType.PERSON);
		playerGuess = people.get(rand.nextInt(people.size())); 
		
		}while(hand.contains(weaponGuess) && hand.contains(playerGuess));
		
		return new Solution(weaponGuess, playerGuess, roomGuess, typeSolution.SUGGESTION); 
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
