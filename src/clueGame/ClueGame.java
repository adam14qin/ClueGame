package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import GUI.ControlGui;
import GUI.detectiveNotes;

public class ClueGame extends JFrame{


	public static final int CELL_PIXEL_SIZE = 25; 

	public Board board;
	private detectiveNotes notes;
	private MyCards cards;

	public ClueGame()
	{
		Board board= Board.getInstance();
		board.setConfigFiles("SSAL_ClueLayout.csv", "SSAL_ClueLegend.txt", "SSAL_Weapons.txt", "SSAL_Players.txt");
		this.board=board;
		board.initialize();
//		this.dNotes=new JDialog();
		setTitle("Clue Game");
		setSize((8+board.getNumColumns())*CELL_PIXEL_SIZE, board.getNumRows()*(8+CELL_PIXEL_SIZE));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setResizable(true);
		add(board, BorderLayout.CENTER);

		this.notes = new detectiveNotes(board);
		
		cards=new MyCards(board);
		add(cards, BorderLayout.EAST);
		
		ControlGui control = new ControlGui();
		add(control, BorderLayout.SOUTH);
		
		// Set menu bar
		JMenu menu=new JMenu("File");
		JMenuBar menuBar=new JMenuBar();
		setJMenuBar(menuBar);
		menu.add(createDetectiveNotes());
		menu.add(creatFileExitItem());
		menuBar.add(menu);
	}
	
	public void displaySplashScreen()
	{
		JOptionPane.showMessageDialog(this, "You are " + board.getHuman().getName() + ", press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
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
				notes.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	public static void main(String[] args) {
		// Create a new detective note 

		ClueGame game = new ClueGame();
		game.setVisible(true);
		game.displaySplashScreen();
	}

}
