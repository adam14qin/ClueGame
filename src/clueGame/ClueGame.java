package clueGame;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ClueGame extends JFrame{

	public static final int CELL_PIXEL_SIZE = 30; 
	
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
		JMenu menu=new JMenu("File");
		JMenuBar menuBar=new JMenuBar();
		setJMenuBar(menuBar);
		menu.add(createDetectiveNotes());
		menu.add(creatFileExitItem());
		menuBar.add(menu);
	}
	
	private JMenuItem creatFileExitItem()
	{
		JMenuItem item=new JMenuItem("Exit");
		return item;
	}
	
	private JMenuItem createDetectiveNotes()
	{
		JMenuItem item=new JMenuItem("new detective note");
		return item;
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);
	}

}
