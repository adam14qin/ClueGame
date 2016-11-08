package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class MyCards extends JPanel {
	
	public MyCards(Board board){
		JPanel main=new JPanel(new GridLayout(6,1));
		JPanel people=new JPanel();
		JPanel rooms=new JPanel();
		JPanel weapons=new JPanel();
		main.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		rooms.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		weapons.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		
		JTextArea playerCard=new JTextArea();
		JTextArea roomCard=new JTextArea();
		JTextArea weaponCard=new JTextArea();
		playerCard.setFont(new java.awt.Font("Courier New", 1, 20));
		playerCard.setWrapStyleWord(true);
		roomCard.setFont(new java.awt.Font("Courier New", 1, 20));
		roomCard.setWrapStyleWord(true);
		weaponCard.setFont(new java.awt.Font("Courier New", 1, 20));
		weaponCard.setWrapStyleWord(true);
		
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
			person+=card.getCardName()+"\n";
		}
		for (Card card:handRooms){
			room+=card.getCardName()+"\n";
		}
		for (Card card:handWeapons){
			weapon+=card.getCardName()+"\n";
		}
		
		playerCard.setText(person);
		roomCard.setText(room);
		weaponCard.setText(weapon);
		
		people.add(playerCard);
		rooms.add(roomCard);
		weapons.add(weaponCard);
		
		main.add(people);
		main.add(rooms);
		main.add(weapons);
	}
}
