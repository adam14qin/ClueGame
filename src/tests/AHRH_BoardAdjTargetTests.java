package tests;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class AHRH_BoardAdjTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		board.setConfigFiles("AHRH_ClueLayout.csv", "AHRH_ClueLegend.txt");		
		board.initialize();
	}
	
	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit() {
		// TEST DOORWAY UP 
		Set<BoardCell> testList = board.getAdjList(16, 3);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(15, 3)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(4, 14);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(4, 13)));
		//TEST DOORWAY RIGHT
		testList = board.getAdjList(4, 6);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(4, 7)));
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways() {
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(10, 20);
		assertTrue(testList.contains(board.getCellAt(10, 21)));
		assertTrue(testList.contains(board.getCellAt(10, 19)));
		assertTrue(testList.contains(board.getCellAt(9, 20)));
		assertTrue(testList.contains(board.getCellAt(11, 20)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(14, 8);
		assertTrue(testList.contains(board.getCellAt(14, 9)));
		assertTrue(testList.contains(board.getCellAt(14, 7)));
		assertTrue(testList.contains(board.getCellAt(13, 8)));
		assertTrue(testList.contains(board.getCellAt(15, 8)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(10, 4);
		assertTrue(testList.contains(board.getCellAt(10, 5)));
		assertTrue(testList.contains(board.getCellAt(10, 3)));
		assertTrue(testList.contains(board.getCellAt(9, 4)));
		assertTrue(testList.contains(board.getCellAt(11, 4)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(15, 4);
		assertTrue(testList.contains(board.getCellAt(16, 4)));
		assertTrue(testList.contains(board.getCellAt(14, 4)));
		assertTrue(testList.contains(board.getCellAt(15, 5)));
		assertTrue(testList.contains(board.getCellAt(15, 3)));
		assertEquals(4, testList.size());
	}
	

	// Test against the walls with no doors
	// These tests are GOLD on the planning spreadsheet
	@Test
	public void testAdjacencyWall() {
		// Test in an internal corner
		Set<BoardCell> testList = board.getAdjList(11, 3);
		assertTrue(testList.contains(board.getCellAt(11, 4)));
		assertTrue(testList.contains(board.getCellAt(10, 3)));
		assertEquals(2, testList.size());
		
		// Test against room
		testList = board.getAdjList(6, 21);
		assertTrue(testList.contains(board.getCellAt(6, 22)));
		assertTrue(testList.contains(board.getCellAt(6, 20)));
		assertTrue(testList.contains(board.getCellAt(7, 21)));
		assertEquals(3, testList.size());
	}
	
	// Test against the board edges with no doors
	// These tests are DARK BLUE on the planning spreadsheet
	@Test
	public void testEdges() {
		// Test in an internal corner on left wall
		Set<BoardCell> testList = board.getAdjList(7, 0);
		assertTrue(testList.contains(board.getCellAt(6, 0)));
		assertEquals(1, testList.size());
		
		// Test against top wall in a corner
		testList = board.getAdjList(0, 13);
		assertTrue(testList.contains(board.getCellAt(1, 13)));
		assertEquals(1, testList.size());
		
		// Test against right wall 
		testList = board.getAdjList(20, 22);
		assertTrue(testList.contains(board.getCellAt(20, 21)));
		assertTrue(testList.contains(board.getCellAt(19, 22)));
		assertTrue(testList.contains(board.getCellAt(21, 22)));
		assertEquals(3, testList.size());
				
		// Test against bottom wall 
		testList = board.getAdjList(22, 9);
		assertTrue(testList.contains(board.getCellAt(22, 10)));
		assertTrue(testList.contains(board.getCellAt(22, 8)));
		assertTrue(testList.contains(board.getCellAt(21, 9)));
		assertEquals(3, testList.size());
						
	}
	
	// Test against the board edges with no doors
	// These tests are WHITE on the planning spreadsheet
	@Test
	public void testWalkway() {
		// Test in an internal corner on left wall
		Set<BoardCell> testList = board.getAdjList(21, 9);
		assertTrue(testList.contains(board.getCellAt(21, 10)));
		assertTrue(testList.contains(board.getCellAt(21, 8)));
		assertTrue(testList.contains(board.getCellAt(22, 9)));
		assertTrue(testList.contains(board.getCellAt(20, 9)));
		assertEquals(4, testList.size());
						
	}
	
	
	// Tests of just walkways, 1 step, 
	// These are DARK RED on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(4, 12, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 13)));
		assertTrue(targets.contains(board.getCellAt(4, 11)));
		assertTrue(targets.contains(board.getCellAt(3, 12)));
		assertTrue(targets.contains(board.getCellAt(5, 12)));
	}
	
	// Tests of just walkways, 2 steps
	// These are DARK RED on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(0, 8, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(2, 8)));
		assertTrue(targets.contains(board.getCellAt(1, 7)));
		
	}
	
	// Tests of just walkways, 3 steps
	// These are DARK RED on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(15, 15, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 18)));
		assertTrue(targets.contains(board.getCellAt(14, 17)));
		assertTrue(targets.contains(board.getCellAt(13, 16)));
		assertTrue(targets.contains(board.getCellAt(17, 16)));
		assertTrue(targets.contains(board.getCellAt(15, 12)));
		
	}	
	
	// Tests of just walkways plus one door, 4 steps
	// These are DARK RED on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(11, 11, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(7, 11)));
		assertTrue(targets.contains(board.getCellAt(13, 9)));	
		assertTrue(targets.contains(board.getCellAt(9, 12)));	
		assertTrue(targets.contains(board.getCellAt(8, 12)));	
	}	
	
	// Test getting into a room
	// These are DARK RED on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom() {
		// One room is exactly 1 away
		board.calcTargets(18, 21, 1);
		Set<BoardCell> targets= board.getTargets();
		assertTrue(targets.contains(board.getCellAt(18, 20)));	
		assertTrue(targets.contains(board.getCellAt(18, 22)));	
		assertTrue(targets.contains(board.getCellAt(19, 21)));	
		assertTrue(targets.contains(board.getCellAt(17, 21)));
		assertEquals(4, targets.size());

		// One room is exactly 1 away
		board.calcTargets(6, 18, 1);
		targets= board.getTargets();
		assertTrue(targets.contains(board.getCellAt(6, 17)));	
		assertTrue(targets.contains(board.getCellAt(6, 19)));	
		assertTrue(targets.contains(board.getCellAt(7, 18)));	
		assertTrue(targets.contains(board.getCellAt(5, 18)));
		assertEquals(4, targets.size());
	}

	// Test getting out of a room
	// These are DARK RED on the planning spreadsheet
	@Test
	public void testRoomExit() {
		// Walkway is exactly 1 away
		board.calcTargets(19, 11, 1);
		Set<BoardCell> targets= board.getTargets();
		assertTrue(targets.contains(board.getCellAt(19, 10)));	
		assertEquals(1, targets.size());

		// Walkway is exactly 1 away
		board.calcTargets(9, 19, 1);
		targets= board.getTargets();
		assertTrue(targets.contains(board.getCellAt(9, 20)));	
		assertEquals(1, targets.size());
	}
}
