package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class SSAL_FileInitTests {
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
			board.setConfigFiles("SSAL_ClueLayout.csv", "SSAL_ClueLegend.txt", "SSAL_ClueDeck.txt");		
			// Initialize will load BOTH config files 
			board.initialize();
		}
		@Test
		public void testRooms() {
			// Get the map of initial => room 
			Map<Character, String> legend = board.getLegend();
			// Ensure we read the correct number of rooms
			assertEquals(LEGEND_SIZE, legend.size());
			// To ensure data is correctly loaded, test retrieving a few rooms 
			// from the hash, including the first and last in the file and a few others
			assertEquals("Great Hall", legend.get('G'));
			assertEquals("Dungeon", legend.get('D'));
			assertEquals("Kitchen", legend.get('K'));
			assertEquals("Laboratory", legend.get('O'));
			assertEquals("Scriptorium", legend.get('X'));
		}
		
		@Test
		public void testBoardDimensions() {
			// Ensure we have the proper number of rows and columns
			assertEquals(NUM_ROWS, board.getNumRows());
			assertEquals(NUM_COLUMNS, board.getNumColumns());		
		}
		
		// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus 
		// two cells that are not a doorway.
		// These cells are white on the planning spreadsheet
		@Test
		public void FourDoorDirections() {
			BoardCell room = board.getCellAt(4, 4);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
			room = board.getCellAt(3, 7);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.DOWN, room.getDoorDirection());
			room = board.getCellAt(8, 19);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.LEFT, room.getDoorDirection());
			room = board.getCellAt(17, 1);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.UP, room.getDoorDirection());
			// Test that room pieces that aren't doors know it
			room = board.getCellAt(2, 2);
			assertFalse(room.isDoorway());	
			// Test that walkways are not doors
			BoardCell cell = board.getCellAt(12, 10);
			assertFalse(cell.isDoorway());		

		}
		
		// Test that we have the correct number of doors
		@Test
		public void testNumberOfDoorways() 
		{
			int numDoors = 0;
			for (int row=0; row<board.getNumRows(); row++)
				for (int col=0; col<board.getNumColumns(); col++) {
					BoardCell cell = board.getCellAt(row, col);
					if (cell.isDoorway())
						numDoors++;
				}
			Assert.assertEquals(12, numDoors);
		}

		// Test a few room cells to ensure the room initial is correct.
		@Test
		public void testRoomInitials() {
			// Test first cell in room
			assertEquals('L', board.getCellAt(0, 0).getInitial());
			assertEquals('O', board.getCellAt(0, 6).getInitial());
			assertEquals('G', board.getCellAt(0, 14).getInitial());
			// Test last cell in room
			assertEquals('R', board.getCellAt(10, 23).getInitial());
			assertEquals('C', board.getCellAt(18, 23).getInitial());
			// Test a walkway
			assertEquals('W', board.getCellAt(6, 14).getInitial());
			// Test the closet
			assertEquals('X', board.getCellAt(9,7).getInitial());
		}
		
}