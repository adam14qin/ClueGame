package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.sun.org.apache.bcel.internal.generic.SWITCH;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;

public class detectiveNotes extends JPanel {
	
	public detectiveNotes(){
		//Initialize board and get inputs of cards
		Board board= Board.getInstance();
		board.setConfigFiles("SSAL_ClueLayout.csv", "SSAL_ClueLegend.txt", "SSAL_Weapons.txt", "SSAL_Players.txt");
		board.initialize();

		//Create three rows and two columns in the detective notes
		setLayout(new GridLayout(3,2));
		JComboBox<String> personGuess=new JComboBox<String>();
		JComboBox<String> roomGuess=new JComboBox<String>();
		JComboBox<String> weaponGuess=new JComboBox<String>();
		ArrayList<Card> deck = board.getDeck();
		ArrayList<Card> person=new ArrayList<Card>();
		ArrayList<Card> weapon=new ArrayList<Card>();
		ArrayList<Card> room=new ArrayList<Card>();
		
		for (Card card:deck){
			if (card.getCardType()==CardType.PERSON){
				personGuess.addItem(card.getCardName());
				person.add(card);
			}else if (card.getCardType()==CardType.ROOM){
				roomGuess.addItem(card.getCardName());
				room.add(card);
			}else if (card.getCardType()==CardType.WEAPON){
				weaponGuess.addItem(card.getCardName());
				weapon.add(card);
			}
		}
		//Set font size
		Font font = new Font("Times New Roman", Font.BOLD, 20);
		//setFont(font);
		
		//First row -->people
		JPanel People=new JPanel(new GridLayout(3,2));
		for (Card card:person){
			People.add(new JCheckBox(card.getCardName()));
		}
		People.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		JPanel PersonCombo=new JPanel(new GridLayout(1,2));
		JPanel Blank1=new JPanel();
		PersonCombo.add(personGuess);
		PersonCombo.add(Blank1);
		PersonCombo.setBorder(new TitledBorder (new EtchedBorder(), "Peson Guess"));
		add(People);
		add(PersonCombo);
		
		//Second row -->room
		JPanel Rooms=new JPanel(new GridLayout(5,2));
		for (Card card:room){
			Rooms.add(new JCheckBox(card.getCardName()));
		}
		Rooms.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		JPanel RoomCombo=new JPanel(new GridLayout(1,2));
		RoomCombo.add(roomGuess);
		JPanel Blank2=new JPanel();
		RoomCombo.add(Blank2);
		RoomCombo.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		add(Rooms);
		add(RoomCombo);
		
		//Third row -->weapon
		JPanel Weapons=new JPanel(new GridLayout(3,2));
		for (Card card:weapon){
			Weapons.add(new JCheckBox(card.getCardName()));
		}
		Weapons.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		JPanel WeaponCombo=new JPanel(new GridLayout(1,2));
		WeaponCombo.add(weaponGuess);
		JPanel Blank3=new JPanel();
		WeaponCombo.add(Blank3);
		WeaponCombo.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		add(Weapons);
		add(WeaponCombo);
	}
	
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,600);	
		// Create the JPanel and add it to the JFrame
		detectiveNotes notes = new detectiveNotes();
		frame.add(notes, BorderLayout.CENTER);
		// Now let's view it
		frame.setVisible(true);
		frame.setEnabled(true);
	}
}
