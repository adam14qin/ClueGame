package clueGame;

import javax.swing.JFrame;

public class ClueGame extends JFrame{

	public static final int CELL_PIXEL_SIZE = 25; 
	
	public ClueGame()
	{
		Board board= Board.getInstance();
		board.setConfigFiles("SSAL_ClueLayout.csv", "SSAL_ClueLegend.txt", "SSAL_Weapons.txt", "SSAL_Players.txt");
		board.initialize();
		setTitle("Clue Game");
		setSize(board.getNumRows()*CELL_PIXEL_SIZE, board.getNumColumns()*CELL_PIXEL_SIZE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setResizable(true);
		add(board);
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);
	}

}
