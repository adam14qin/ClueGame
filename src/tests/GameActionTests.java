package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.Computer;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameActionTests {
	// Constants that I will use to test whether the file was loaded correctly
			public static final int LEGEND_SIZE = 11;
			public static final int NUM_ROWS = 24;
			public static final int NUM_COLUMNS = 24;

			// NOTE: I made Board static because I only want to set it up one 
			// time (using @BeforeClass), no need to do setup before each test.
			private static Board board;
			
			@BeforeClass
			public static void setUp() {
				// Board is singleton, get the only instance
				board = Board.getInstance();
				// set the file names to use my config files
				board.setConfigFiles("SSAL_ClueLayout.csv", "SSAL_ClueLegend.txt", "SSAL_Weapons.txt", "SSAL_Players.txt");		
				// Initialize will load BOTH config files 
				board.initialize();
			}
			
			@Test
			public void selectValidTargetLocation() {
				/*
				 * We check to make sure that the player only chooses valid targets and nothing else.
				 */
				Player testPlayer = board.getPlayers().get(3); 
				for(int i=0; i<100; i++)
				{
					board.calcTargets(testPlayer.getRow(), testPlayer.getCol(), 2);
					BoardCell choice = testPlayer.getMove(board.getTargets()); 
					assertTrue(board.getTargets().contains(choice)); 
				}
			}
			
			@Test
			public void selectRandomTargetLocation() {
				/*
				 * Looked to make sure that one of the players with no access to a doorway or room was 
				 * picking all three possible random spots it could out of 100 tries. 
				 */
				Player testPlayer = board.getPlayers().get(1); 
				boolean loc_21_4 = false;
				boolean loc_20_5 = false;
				boolean loc_23_4 = false;
				board.calcTargets(testPlayer.getRow(), testPlayer.getCol(), 2);
				for(int i=0; i<100; i++)
				{
					BoardCell choice = testPlayer.getMove(board.getTargets()); 
					if(choice.equals(new BoardCell(21,4,""))){
						loc_21_4 = true;
					}
					else if(choice.equals(new BoardCell(20,5,"  "))){
						loc_20_5 = true;
					}
					else if(choice.equals(new BoardCell(23,4,""))){
						loc_23_4 = true;
					}
					else
					{
						fail("Invalid target selected"); 
					}
				}
			 	assertTrue(loc_21_4);
		        assertTrue(loc_20_5);
		        assertTrue(loc_23_4);
			}
			
			@Test 
			public void selectRoomTarget() {
				/*
				 * Tests to make sure that we do indeed choose the room because it is within our range of steps
				 * After we move to the room, pass in new targets and assure that the player does not go to the 
				 * room he just came from
				 */
				Player testPlayer = board.getPlayers().get(4); 
				board.calcTargets(testPlayer.getRow(), testPlayer.getCol(), 6);
				BoardCell newSpot = testPlayer.getMove(board.getTargets());
				assertTrue(newSpot.equals(board.getCellAt(14, 18)));
				board.calcTargets(newSpot.getRow(), newSpot.getColumn(), 6);
				for(int i =0; i<100; i++)
				{
					assertFalse(testPlayer.getMove(board.getTargets()).equals(board.getCellAt(14, 18)));
				}
			}
			
			@Test
			public void checkAccusation() {
				/*
				 * Checking to see if out check accusation works. We make an accusation that 
				 * must be false as we check to make sure it is different than the answer for the board,
				 * then we check to see if our method returns false with the guess and true with the true answer.
				 */
				Random rand = new Random();
				Solution guess = new Solution();
				String weaponGuess;
				String roomGuess; 
				Player playerGuess; 
				boolean guessIsNotAnswer = false;
				while(!guessIsNotAnswer) {
					weaponGuess = board.getWeapons().get(rand.nextInt(board.getWeapons().size()));
					roomGuess = board.getHabitableRooms().get(rand.nextInt(board.getHabitableRooms().size()));
					playerGuess = board.getPlayers().get(rand.nextInt(board.getPlayers().size())); 
					guess = new Solution(new Card('W', weaponGuess), new Card('P', playerGuess.getName()), new Card('R', roomGuess));
					if(!board.answer.equals(guess))
							guessIsNotAnswer = true; 
				}
				
				assertFalse(board.checkAccusation(guess));
				assertTrue(board.checkAccusation(new Solution(board.answer.getWeapon(),board.answer.getPlayer(), board.answer.getRoom()))); 
			}
			
			@Test
			public void makeSuggestion() {
				ComputerPlayer tempPlayer = new ComputerPlayer("Temp", 11, 3, Color.yellow); 
				Solution sol = tempPlayer.makeSuggestion(); 
				ArrayList<Card> newHand = new ArrayList<>(); 
				newHand.add(new Card('P', "Balin")); 
				newHand.add(new Card('W', "Acid")); 
				newHand.add(new Card('W', "Bunny")); 
				
				tempPlayer.setHand(newHand); 
				assertTrue(sol.getRoom().getCardName().equals(board.getLegend().get(board.getCellAt(11, 3).getInitial()))); 
				assertFalse(tempPlayer.getHand().contains(sol.getPlayer())); 
				assertFalse(tempPlayer.getHand().contains(sol.getWeapon()));
				
				Map<CardType,Card> newUnseen = new HashMap<>(); 
				newUnseen.put(CardType.PERSON, new Card('P', "Thorin"));
				newUnseen.put(CardType.PERSON, new Card('P', "Kili"));
				newUnseen.put(CardType.WEAPON, new Card('W', "Cat"));
				newUnseen.put(CardType.WEAPON, new Card('W', "AK47"));
			}
			
}