package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import GUI.detectiveNotes;

public class ClueGame extends JFrame{

	public static final int CELL_PIXEL_SIZE = 30; 
	private static JFrame dNotes;
	
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
		// Set detective notes 
		dNotes=new JFrame();
		dNotes.setSize(600,600);
		dNotes.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		// Create the JPanel and add it to the JFrame
		detectiveNotes notes = new detectiveNotes();
		dNotes.add(notes, BorderLayout.CENTER);
		dNotes.setEnabled(true);
		// Set menu bar
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
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	private JMenuItem createDetectiveNotes()
	{
		JMenuItem item=new JMenuItem("Open Detective Notes");
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				dNotes.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);
	}

}
