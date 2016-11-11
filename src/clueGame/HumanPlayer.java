package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import GUI.MakeAGuess;
import clueGame.Solution.typeSolution;

public class HumanPlayer extends Player{

	public boolean isFinished; 
	
	public ClueGame clueGame; 
	
	public HumanPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		isFinished = true;
		// TODO Auto-generated constructor stub
	}
	
	public void submitSuggestion(Solution suggestion)
	{
		isFinished = true;
		if(suggestion.getType()==typeSolution.SUGGESTION)
		clueGame.makeTurn(this, suggestion);
	}
	
	public void setClueGame(ClueGame game)
	{
		this.clueGame = game;
	}
	
	@Override 
	public Solution moveToSpot(BoardCell spot, Board board, boolean onlyMove)
	{
		super.moveToSpot(spot, board, false);
		if(onlyMove)
			return null;
		if(spot.isRoom())
		{
			MakeAGuess guess = new MakeAGuess(board, Solution.typeSolution.SUGGESTION, this);		 
		} else {
			isFinished = true;
		}
		return null;
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

}
