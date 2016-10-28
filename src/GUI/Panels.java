package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Panels extends JPanel {
	public Panels () {
		
		JPanel topRow = new JPanel();
		topRow.setLayout(new GridLayout(0,3));
				JPanel bothLayers = new JPanel();
		bothLayers.setLayout(new GridLayout(2,0));
		
		
		
		JLabel nameLabel1 = new JLabel("Whose turn?");
		JPanel whosTurn = new JPanel();
		whosTurn.setLayout(new GridLayout(2,0));
		whosTurn.add(nameLabel1);
		JTextField turn = new JTextField();
		turn.setEnabled(false);
		whosTurn.add(turn);	
		
		
	
		topRow.add(whosTurn);
				
		JButton nextP = new JButton("Next Player");
		topRow.add(nextP);	
		topRow.add(new JButton("Make an accusation"));
		
		bothLayers.add(topRow, BorderLayout.NORTH);
		add(topRow);
		dieBox(bothLayers);
		
	}
	
	public void dieBox (JPanel bothLayers) {

		JPanel botRow = new JPanel();
		botRow.setLayout(new GridLayout(0,3));
		JLabel rollLable = new JLabel("Roll");
		JPanel diePanel = new JPanel();
		diePanel.setLayout(new GridLayout(0,2));
		diePanel.add(rollLable);
		JTextField roll = new JTextField();
		roll.setEnabled(false);
		diePanel.add(roll);
		botRow.add(diePanel);
		bothLayers.add(botRow, BorderLayout.SOUTH);
		add(botRow);
		setBorder(new TitledBorder (new EtchedBorder(), "Die"));

	}
}
