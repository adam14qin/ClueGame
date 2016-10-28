package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ControlGui extends JFrame{

	public ControlGui() {
		setTitle("SSAL Clue Game");
		setSize(800, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setResizable(false);
		createLayout();
	}

	private void createLayout() {
		
		/*JLabel nameLabel = new JLabel("Whose turn?");
		setLayout(new GridLayout(2,1));
		JPanel whosTurn = new JPanel();
		whosTurn.setLayout(new GridLayout(2,1));
		whosTurn.add(nameLabel);
		JTextField turn = new JTextField();
		turn.setEnabled(false);
		whosTurn.add(turn);		


		JPanel topRow = new JPanel();
		
		topRow.setLayout(new FlowLayout());
		topRow.add(whosTurn);
		JButton nextP = new JButton("Next Player");
		nextP.setSize(100, 100);
		
		topRow.add(nextP);
		topRow.add(new JButton("Make an accusation"));

		add(topRow);*/
		Panels panels = new Panels();
		add(panels);

	}

	/*private void createLayout() {
		// Create a JPanel
		JPanel panel = new JPanel();		
		JLabel nameLabel = new JLabel("Name");
		//myName = new JTextField(20);
		add(nameLabel, BorderLayout.NORTH);
		add(myName, BorderLayout.CENTER);
		JButton nameButton = new JButton("OK");
		add(nameButton, BorderLayout.SOUTH);
		//nameButton.addActionListener(new ButtonListener());

	}*/


	public static void main(String[] args) {
		ControlGui gui = new ControlGui();
		gui.setVisible(true);


	}

}
