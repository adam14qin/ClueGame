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
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class SSAL_GameActionTests {
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
			guess = new Solution(new Card(CardType.WEAPON, weaponGuess), new Card(CardType.PERSON, playerGuess.getName()), new Card(CardType.ROOM, roomGuess));
			if(!board.answer.equals(guess))
				guessIsNotAnswer = true; 
		}
		// All wrong
		assertFalse(board.checkAccusation(guess));
		//Wrong person
		Solution wrongPerson = new Solution(board.answer.getWeapon(), new Card(CardType.PERSON, "Wrong"), board.answer.getRoom()); 
		assertFalse(board.checkAccusation(wrongPerson));
		//Wrong Weapon
		Solution wrongWeapon = new Solution(new Card(CardType.WEAPON, "wrong"), board.answer.getPlayer(), board.answer.getRoom()); 
		assertFalse(board.checkAccusation(wrongWeapon));
		//Wrong Room
		Solution wrongRoom = new Solution(board.answer.getWeapon(), board.answer.getPlayer(), new Card(CardType.ROOM, "Wrong")); 
		assertFalse(board.checkAccusation(wrongRoom));
		//All right
		assertTrue(board.checkAccusation(new Solution(board.answer.getWeapon(),board.answer.getPlayer(), board.answer.getRoom()))); 
	}

	/* This test checks that we created a suggestion properly, 
	 * If checks that:
	 * the room matches the current location
	 * If only one weapon is not seen, it's selected
	 * If only one persons not seen, it's selected
	 * If multiple weapons not seen, one of them is randomly selected
	 * if multiple persons not seen, on eof them is randomly selected
	 * 
	 */
	@Test
	public void makeSuggestion() {
		ComputerPlayer tempPlayer = new ComputerPlayer("Temp", 11, 3, Color.yellow); 
		ArrayList<Card> newHand = new ArrayList<>(); 
		newHand.add(new Card(CardType.PERSON, "Balin")); 
		newHand.add(new Card(CardType.WEAPON, "Acid")); 
		newHand.add(new Card(CardType.WEAPON, "Bunny"));
		tempPlayer.setHand(newHand); 

		Map<CardType, ArrayList<Card>> newUnseen = new HashMap<>(); 
		ArrayList<Card> weapons = new ArrayList<>(); 
		ArrayList<Card> people = new ArrayList<>(); 
		people.add(new Card(CardType.PERSON, "Thorin"));
		people.add(new Card(CardType.PERSON, "Kili"));
		weapons.add(new Card(CardType.WEAPON, "Cat"));
		weapons.add(new Card(CardType.WEAPON, "AK47"));
		newUnseen.put(CardType.PERSON, people);
		newUnseen.put(CardType.WEAPON, weapons); 
		board.setUnseen(newUnseen);

		Solution sol = tempPlayer.makeSuggestion(board); 

		assertTrue(sol.getRoom().getCardName().equals(board.getLegend().get(board.getCellAt(11, 3).getInitial()))); 
		assertFalse(tempPlayer.getHand().contains(sol.getPlayer())); 
		assertFalse(tempPlayer.getHand().contains(sol.getWeapon()));

	}

	/* This tests that we can disprove suggestions properly
	 * If:
	 * The player has only one matching card it will be returned
	 * if the player has >1 matching card, return a random card
	 * If a player has no matching cards, return null
	 * 
	 */
	@Test 
	public void disproveSuggestion() {
		ComputerPlayer tempPlayer = new ComputerPlayer("Temp", 11, 3, Color.yellow); 
		ArrayList<Card> newHand = new ArrayList<>(); 
		newHand.add(new Card(CardType.PERSON, "Balin")); 
		newHand.add(new Card(CardType.WEAPON, "Acid")); 
		newHand.add(new Card(CardType.WEAPON, "Bunny"));
		tempPlayer.setHand(newHand); 

		Solution cantBeDisproved = new Solution(new Card(CardType.WEAPON, "Puppy"), new Card(CardType.PERSON, "Dori"), new Card(CardType.ROOM, "Kitchen"));
		Card returned = tempPlayer.disproveSuggestion(board, cantBeDisproved); 
		assertNull(returned);
		Solution oneCanBeDisproved = new Solution(new Card(CardType.WEAPON, "Acid"), new Card(CardType.PERSON, "Dori"), new Card(CardType.ROOM, "Kitchen"));
		returned = tempPlayer.disproveSuggestion(board, oneCanBeDisproved);
		assertEquals(new Card(CardType.WEAPON, "Acid"), returned);
		Solution multipleCanBeDisproved = new Solution(new Card(CardType.WEAPON, "Acid"), new Card(CardType.PERSON, "Balin"), new Card(CardType.ROOM, "Kitchen"));
		boolean acidCard = false;
		boolean balinCard = false;
		for(int i=0; i<100; i++)
		{
			returned = tempPlayer.disproveSuggestion(board, multipleCanBeDisproved); 
			if(returned.equals(new Card(CardType.WEAPON, "Acid"))) {
				acidCard = true; 
			}
			else if(returned.equals(new Card(CardType.PERSON, "Balin"))) {
				balinCard = true; 
			}
		}
		assertTrue(acidCard); 
		assertTrue(balinCard); 	
	}

	@Test
	public void testHandlingSuggestions() {
		//Make three players two computer one person
		ArrayList<Player> players = new ArrayList<>(); 
		ArrayList<Card> hand1 = new ArrayList<Card>(); 
		ArrayList<Card> hand2 = new ArrayList<Card>(); 
		ArrayList<Card> hand3 = new ArrayList<Card>(); 

		//Assign cards
		ComputerPlayer playerOne = new ComputerPlayer("Shea", 17, 1, Color.black);
		hand1.add(new Card(CardType.PERSON, "Balin")); 
		hand1.add(new Card(CardType.WEAPON, "Acid")); 
		hand1.add(new Card(CardType.WEAPON, "Bunny"));
		playerOne.setHand(hand1);
		hand1.clear(); 

		//Assign cards
		ComputerPlayer playerTwo = new ComputerPlayer("Anthony", 15, 8, Color.white);
		hand2.add(new Card(CardType.PERSON, "Fili")); 
		hand2.add(new Card(CardType.WEAPON, "Bird")); 
		hand2.add(new Card(CardType.ROOM, "Great Hall"));
		playerTwo.setHand(hand2);
		hand2.clear(); 

		//Assign Cards
		HumanPlayer playerThree = new HumanPlayer("Human", 5, 14, Color.blue); 
		hand3.add(new Card(CardType.PERSON, "Nori")); 
		hand3.add(new Card(CardType.WEAPON, "Cat")); 
		hand3.add(new Card(CardType.ROOM, "Library"));
		playerThree.setHand(hand3);
		players.add(playerOne);
		players.add(playerTwo);
		players.add(playerThree); 

		//Nobody can disprove
		Solution nobodyCanDisprove = new Solution(new Card(CardType.WEAPON, "null1"), new Card(CardType.PERSON, "null2"), new Card(CardType.ROOM, "null3") );
		Card x = board.handleSuggestion(players, 0, nobodyCanDisprove);
		assertNull(x); 

		//Only player one (Accuser) can disprove
		Solution playerOneCanDisprove = new Solution(new Card(CardType.WEAPON, "Acid"), new Card(CardType.PERSON, "null2"), new Card(CardType.ROOM, "null3") );
		x = board.handleSuggestion(players, 0, playerOneCanDisprove);
		assertNull(x);

		//Only the human can disprove, but is not accuser
		Solution humanCanDisprove = new Solution(new Card(CardType.WEAPON, "Cat"), new Card(CardType.PERSON, "null2"), new Card(CardType.ROOM, "null3") );
		x = board.handleSuggestion(players, 0, humanCanDisprove);
		assertTrue(playerThree.getHand().contains(x));

		//Only the human can disprove but is accuser
		x = board.handleSuggestion(players, 2, humanCanDisprove);
		assertNull(x);

		//Only player 1 and 2 can disprove, but player 1 disproves 1st
		Solution _0_1_canDisprove = new Solution(new Card(CardType.WEAPON, "Bird"), new Card(CardType.PERSON, "Balin"), new Card(CardType.ROOM, "null3"));
		x = board.handleSuggestion(players, 2, _0_1_canDisprove);
		assertTrue(playerOne.getHand().contains(x));

		//Only player 2 and human can disprove, but player 2 goes first
		Solution _1_2_canDisprove = new Solution(new Card(CardType.WEAPON, "null1"), new Card(CardType.PERSON, "Fili"), new Card(CardType.ROOM, "Library"));
		x = board.handleSuggestion(players, 0, _1_2_canDisprove);
		assertTrue(playerTwo.getHand().contains(x));

		//Only human player and 1 can disprove, but human (player three) goes first
		Solution _2_0_canDisprove = new Solution(new Card(CardType.WEAPON, "Cat"), new Card(CardType.PERSON, "Balin"), new Card(CardType.ROOM, "null3"));
		x = board.handleSuggestion(players, 1, _2_0_canDisprove);
		assertTrue(playerThree.getHand().contains(x));
	}

}