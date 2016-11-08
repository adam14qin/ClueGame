package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import GUI.detectiveNotes;

public class ClueGame extends JFrame{


	public static final int CELL_PIXEL_SIZE = 25; 

	public Board board;
	private JDialog dNotes;

	public ClueGame(Board board, JDialog dialog)
	{
		this.board=board;
		this.dNotes=dialog;
		setTitle("Clue Game");
		setSize(board.getNumColumns()*CELL_PIXEL_SIZE, board.getNumRows()*(3+CELL_PIXEL_SIZE));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setResizable(true);
		add(board);
		// Set menu bar
		JMenu menu=new JMenu("File");
		JMenuBar menuBar=new JMenuBar();
		setJMenuBar(menuBar);
		menu.add(createDetectiveNotes());
		menu.add(creatFileExitItem());
		menuBar.add(menu);
		displaySplashScreen(this);
	}
	
	private void displaySplashScreen(JFrame frame)
	{
		JOptionPane.showMessageDialog(frame, "You are " + board.getHuman().getName() + ", press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
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
		Board board= Board.getInstance();
		board.setConfigFiles("SSAL_ClueLayout.csv", "SSAL_ClueLegend.txt", "SSAL_Weapons.txt", "SSAL_Players.txt");
		board.initialize();
		// Create a new detective note 
		detectiveNotes notes = new detectiveNotes(board);
		notes.setVisible(false);
		notes.setSize(new Dimension(600,600));
		notes.setEnabled(true);
		notes.setDefaultCloseOperation(HIDE_ON_CLOSE);
		ClueGame game = new ClueGame(board, notes);
		game.setVisible(true);
	}

}
