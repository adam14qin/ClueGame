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
import GUI.MyCards;
import GUI.detectiveNotes;
import clueGame.Solution.typeSolution;

public class ClueGame extends JFrame{


	public static final int CELL_PIXEL_SIZE = 20; 

	public Board board;
	private detectiveNotes notes;
	private MyCards cards;
	private ControlGui control; 

	public ClueGame()
	{
		// Get single instance of board
		this.board = Board.getInstance();
		board.initialize();
		board.getHuman().setClueGame(this);
		setTitle("Clue Game");
		setSize(650,675);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setResizable(true);
		add(board, BorderLayout.CENTER);
		
		this.notes = new detectiveNotes(board);
		
		this.cards=new MyCards(board);
		add(cards, BorderLayout.EAST);
		
		this.control = new ControlGui();
		add(control, BorderLayout.SOUTH);
		
		// Set menu bar
		JMenu menu=new JMenu("File");
		JMenuBar menuBar=new JMenuBar();
		setJMenuBar(menuBar);
		menu.add(createDetectiveNotes());
		menu.add(creatFileExitItem());
		menuBar.add(menu); 
		setupActionListeners();
	}
	
	public void setupActionListeners() {
		control.nextPlayerButton.addActionListener(e -> nextPlayerButtonPressed());
		control.makeAccusationButton.addActionListener(e-> makeAccusationButtonPressed());
	}
	
	public void nextPlayerButtonPressed()
	{
		if(!board.currentPlayer.equals(board.getHuman()) || board.getHuman().isFinished)
		{
			board.playerIndex = (board.playerIndex+1)%board.getPlayers().size();
			board.currentPlayer = board.getPlayers().get(board.playerIndex%board.getPlayers().size()); 
			if(board.currentPlayer.equals(board.getHuman()))
			{
				board.getHuman().isFinished = false;
			}
			board.rollDie(); 
			board.calcTargets(board.currentPlayer.getRow(), board.currentPlayer.getCol(), board.dieRoll);
			update(); 
			nextMove(board.currentPlayer);
		}
		else {
			JOptionPane.showMessageDialog(this, "You must finish your turn!", "Not done!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void makeAccusationButtonPressed()
	{
		System.out.println("Also Pressed");
	}
	
	public void update()
	{
		control.guessLabel.setText("");
		control.guessResultLabel.setText("");
		control.rollNum.setText("      " + board.dieRoll);
		control.turnName.setText(board.getPlayers().get(board.playerIndex).getName());
		repaint(); 
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
	
	private void nextMove(Player player)
	{
		if(board.currentPlayer!= board.getHuman())
		{
			BoardCell newSpot = board.currentPlayer.getMove(board.getTargets());
			Solution guess = player.moveToSpot(newSpot, board);
			makeTurn(player, guess); 
		}
	}
	
	public boolean makeTurn(Player currentPlayer, Solution guess) {
		
		if(guess != null)
		{
			control.guessLabel.setText(guess.getPlayer().getCardName() + " " + guess.getWeapon().getCardName() + " " + guess.getRoom().getCardName());
			Card disprove = board.handleSuggestion(board.getPlayers(), board.playerIndex, guess);
			if(disprove == null && guess.getType()==typeSolution.ACCUSATION)
			{
				return true;
			}
			else if(disprove == null && guess.getType() == typeSolution.SUGGESTION)
			{
				control.guessResultLabel.setText("No disprove Card");
				return false; 
			}
			else if(disprove != null)
			{
				control.guessResultLabel.setText(disprove.getCardName());
				return false;
			}
		}
		return false;
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
		ClueGame game = new ClueGame();
		game.setVisible(true);
		game.displaySplashScreen();
	}

}
