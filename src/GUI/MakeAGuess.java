package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ClueGame;
import clueGame.HumanPlayer;
import clueGame.Solution;
import clueGame.Solution.typeSolution;

public class MakeAGuess extends JDialog{
	public Solution.typeSolution type;
	public JComboBox<String> personDisp;
	public JComboBox<String> weaponDisp;
	public JComboBox<String> roomDispAccu;
	public JTextArea roomDispSugg;
	public Solution solution;
	public HumanPlayer human; 
	
	public MakeAGuess(Board board, Solution.typeSolution type, HumanPlayer human){
		setModal(true);
		this.human = human;
		this.type=type;
		setLayout(new GridLayout(4,2));
		setSize(new Dimension(300,300));
		//Set font size
		Font font = new Font("Times New Roman", Font.BOLD, 20);
		
		JTextArea roomLabel =new JTextArea();
		roomLabel.setEditable(false);
		roomLabel.setFont(font);
		if (type==Solution.typeSolution.SUGGESTION){
			roomLabel.setText("Your room");
		}else if (type==Solution.typeSolution.ACCUSATION){
			roomLabel.setText("Room");
		}
		JTextArea personLabel=new JTextArea();
		personLabel.setFont(font);
		personLabel.setText("Person");
		personLabel.setEditable(false);
		JTextArea weaponLabel=new JTextArea();
		weaponLabel.setFont(font);
		weaponLabel.setEditable(false);
		weaponLabel.setText("Weapon");
		
		roomDispSugg=new JTextArea();
		roomDispSugg.setFont(font);
		roomDispSugg.setBorder(new TitledBorder (new EtchedBorder(), ""));
		BoardCell cell = board.getCellAt(board.getHuman().getRow(), board.getHuman().getCol());
		roomDispSugg.setText(board.rooms.get(cell.getInitial()));
		
		roomDispAccu=new JComboBox<String>();
		roomDispAccu.setFont(font);
		roomDispSugg.setEditable(false);
		personDisp=new JComboBox<String>();
		personDisp.setFont(font);
		weaponDisp=new JComboBox<String>();
		weaponDisp.setFont(font);
		JButton submit=new JButton("Submit");
		submit.setFont(font);
		submit.addActionListener(e->submitButtonPressed(board));
		JButton cancel=new JButton("Cancel");
		cancel.setFont(font);
		cancel.addActionListener(e->cancelButtonPressed());
		
		ArrayList<Card> updatedDeck=board.getDeck();
		for (Card card:board.getHuman().getHand()){
			updatedDeck.remove(card);
		}
		
		for (Card card:updatedDeck){
			if (card.getCardType()==CardType.PERSON){
				personDisp.addItem(card.getCardName());
			}else if (card.getCardType()==CardType.ROOM){
				roomDispAccu.addItem(card.getCardName());
			}else if (card.getCardType()==CardType.WEAPON){
				weaponDisp.addItem(card.getCardName());
			}
		}
		
		add(roomLabel);
		if (type==Solution.typeSolution.SUGGESTION){
			add(roomDispSugg);
		}else if (type==Solution.typeSolution.ACCUSATION){
			add(roomDispAccu);
		}
		add(personLabel);
		add(personDisp);
		add(weaponLabel);
		add(weaponDisp);
		add(submit);
		add(cancel);
		setVisible(true);
	}
	
	public void submitButtonPressed(Board board)
	{
		Card person=new Card(CardType.PERSON,(String)personDisp.getSelectedItem());
		Card weapon=new Card(CardType.WEAPON,(String)weaponDisp.getSelectedItem());
		Card room;
		if (type==Solution.typeSolution.SUGGESTION){
			room=new Card(CardType.ROOM,roomDispSugg.getText());
		}else {
			room=new Card(CardType.ROOM,(String)roomDispAccu.getSelectedItem());
		}		
		solution=new Solution (weapon,person,room, type);
		setVisible(false);
		if(type==typeSolution.SUGGESTION)
		{
		human.submitSuggestion(solution);
		} else {
			board.acceptSolution(solution);
		}
	}
	
	public void cancelButtonPressed(){
		setVisible(false);
	}
	
//	public static void main(String[] args) {
//		Board board = Board.getInstance();
//		board.initialize();
//		MakeAGuess pane = new MakeAGuess(board, Solution.typeSolution.ACCUSATION);
//		pane.setVisible(true);
//	}
}
