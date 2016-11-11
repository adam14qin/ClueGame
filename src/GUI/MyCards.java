package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.Card;
import javafx.scene.layout.Border;
import oracle.jrockit.jfr.JFR;

public class MyCards extends JPanel {
	
	public MyCards(Board board){
		setLayout(new GridLayout(6,1));
		
		JPanel people=new JPanel(new GridLayout(0, 1));
		JPanel rooms=new JPanel(new GridLayout(0, 1));
		JPanel weapons=new JPanel(new GridLayout(0, 1));
		setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		rooms.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		weapons.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		
		JTextArea playerCard=new JTextArea();
		JTextArea roomCard=new JTextArea();
		JTextArea weaponCard=new JTextArea();
		playerCard.setFont(new java.awt.Font("Courier New", 1, 15));
		playerCard.setWrapStyleWord(true);
		playerCard.setEditable(false);
		roomCard.setFont(new java.awt.Font("Courier New", 1, 15));
		roomCard.setWrapStyleWord(true);
		roomCard.setEditable(false);
		weaponCard.setFont(new java.awt.Font("Courier New", 1, 15));
		weaponCard.setWrapStyleWord(true);
		weaponCard.setEditable(false);
		
		ArrayList<Card> humanHand=board.getHuman().getHand();
		ArrayList<Card> handPeople=new ArrayList<Card>();
		ArrayList<Card> handRooms=new ArrayList<Card>();
		ArrayList<Card> handWeapons=new ArrayList<Card>();
		for (Card card:humanHand){
			switch(card.getCardType())
			{
			case PERSON: 
			{
				handPeople.add(card);
				break;
			}
			case ROOM: 
			{
				handRooms.add(card);
				break;
			}
			case WEAPON: 
			{
				handWeapons.add(card);
				break;
			}
			}
		}
		
		String person="";
		String weapon="";
		String room="";
		for (Card card:handPeople){
			person+=card.getCardName()+"        \n";
		}
		for (Card card:handRooms){
			room+=card.getCardName()+"         \n";
		}
		for (Card card:handWeapons){
			weapon+=card.getCardName()+"           \n";
		}
		
		people.add(playerCard);
		rooms.add(roomCard);
		weapons.add(weaponCard);
		
		playerCard.setText(person);
		roomCard.setText(room);
		weaponCard.setText(weapon);
		
		
		
		add(people);
		add(rooms);
		add(weapons);
	}
	
//	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		Board board = Board.getInstance(); 
//		board.setConfigFiles("SSAL_ClueLayout.csv", "SSAL_ClueLegend.txt", "SSAL_Weapons.txt", "SSAL_Players.txt");
//		board.initialize();
//		frame.add(new MyCards(board), BorderLayout.EAST);
//		frame.setVisible(true);
//	}
}
