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


	public static final int CELL_PIXEL_SIZE = 25; 
	public Board board;

	
	public ClueGame(Board board, JFrame frame)
	{
		this.board=board;
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
				setVisible(true);
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
		JFrame frame=new JFrame();
		frame.setSize(600,600);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		// Create the JPanel and add it to the JFrame
		detectiveNotes notes = new detectiveNotes(board);
		frame.add(notes, BorderLayout.CENTER);
		frame.setEnabled(true);
		ClueGame game = new ClueGame(board, frame);
		game.setVisible(true);
	}

}
