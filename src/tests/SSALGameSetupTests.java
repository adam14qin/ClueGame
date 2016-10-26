package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.Player;
import clueGame.Solution;

public class SSALGameSetupTests {

	private static Board board;
	
	/* The beforeClass sets up the board before starting the tests 
	 */
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
				board = Board.getInstance();
				board.setConfigFiles("SSAL_ClueLayout.csv", "SSAL_ClueLegend.txt", "SSAL_Weapons.txt", "SSAL_Players.txt");		
				board.initialize();
	}
	
	/* This tests the people cards in the desk, by seeing if they have the proper 
	 * letter ('P' for person), as well as having the correct name.
	 * Then we test that the deck has the proper total of cards  
	 */
	@Test
	public void testPeopleCards()
	{
		ArrayList<Card> deck = board.getDeck(); 
		assertTrue(deck.contains(new Card('P', "Thorin")));
		assertTrue(deck.contains(new Card('P', "Fili")));
		assertTrue(deck.contains(new Card('P', "Kili")));
		assertTrue(deck.contains(new Card('P', "Nori")));
		assertEquals(21, deck.size());
	}
	
	/* testWeapon cards checks the after reading in the weapons, 
	 * they are in the correct format ('W' for weapon) and that 
	 * the names are the properly associated weapons 
	 */
	@Test
	public void testWeaponCards()
	{
		ArrayList<Card> deck = board.getDeck(); 
		assertTrue(deck.contains(new Card('W', "Cat")));
		assertTrue(deck.contains(new Card('W', "Bunny")));
		assertTrue(deck.contains(new Card('W', "AK47")));
		assertTrue(deck.contains(new Card('W', "Acid")));
		assertEquals(21, deck.size());
	}
	
	/* testRoomCards tests that the rooms are loaded in correctly
	 * we see that each room has an 'R' with it, and that it is also
	 * named correctly according to our config file
	 */
	@Test
	public void testRoomCards()
	{
		ArrayList<Card> deck = board.getDeck(); 
		assertTrue(deck.contains(new Card('R', "Kitchen")));
		assertTrue(deck.contains(new Card('R', "Great Hall")));
		assertTrue(deck.contains(new Card('R', "Laboratory")));
		assertTrue(deck.contains(new Card('R', "Study")));
		assertEquals(21, deck.size());
	}
	
	/* testPeople checks that our players ArrayList has not only the names
	 * but also the proper row/col number for starting locations, as well as 
	 * the color associated to each player is have been properly converted into 
	 * a usable value
	 */
	@Test
	public void testPeople()
	{
		ArrayList<Player> players = board.getPlayers();
		assertTrue(players.contains(new Player("Thorin", 15, 1, Color.blue)));
		assertTrue(players.contains(new Player("Fili", 22, 5, Color.red)));
		assertTrue(players.contains(new Player("Dori", 11, 21, Color.orange)));
		assertTrue(players.contains(new Player("Nori", 1, 21, Color.gray)));
		assertEquals(6, players.size());
	}
	
	/*testWeapons tests the weapons ArrayList to see that there are weapons
	 * in it. The first and last weapons, as well as 2 middle weapons were tested
	 * and it also checks that it's size is equal to the total number of weapons
	 */
	
	@Test
	public void testWeapons()
	{
		ArrayList<String> weapons = board.getWeapons(); 
		assertTrue(weapons.contains("Cat"));
		assertTrue(weapons.contains("Bunny"));
		assertTrue(weapons.contains("Bird"));
		assertTrue(weapons.contains("AK47"));
		assertEquals(6, weapons.size());
		
	}
	
	@Test
	public void testAnswerSolution()
	{
		Solution sol = board.getAnswer(); 
		//Check that the solution to the game is not null, was correctly generated
		assertNotNull(sol);
		//Check that each piece of the solution is not null
		assertNotNull(sol.getPlayer());
		assertNotNull(sol.getRoom());
		assertNotNull(sol.getWeapon()); 
	}
	
	@Test 
	public void testDealingCards()
	{
		ArrayList<Card >allCards = new ArrayList<>(); 
		for(int i=0; i<board.getPlayers().size()-1; i++)
		{
			for(Card x: board.getPlayers().get(i).getHand())
			{
				assertFalse(allCards.contains(x)); 
				allCards.add(x); 
			}
			assertTrue((board.getPlayers().get(i).getHand().size() - board.getPlayers().get(i+1).getHand().size()) < 2);
		}
		
		
	}
	
}
