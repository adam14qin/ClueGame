package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;

public class SSALGameSetupTests {

	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
				board = Board.getInstance();
				board.setConfigFiles("SSAL_ClueLayout.csv", "SSAL_ClueLegend.txt", "SSAL_ClueDeck.txt");		
				board.initialize();
	}
	
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
	
}
