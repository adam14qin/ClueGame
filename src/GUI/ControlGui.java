package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.ClueGame;
import javafx.scene.layout.Border;


public class ControlGui extends JPanel{
	
	public JTextField turnName = new JTextField(10); 
	public JTextField rollNum = new JTextField(5); 
	public JTextField guessLabel = new JTextField(15); 
	public JTextField guessResultLabel = new JTextField(10); 
	public JButton nextPlayerButton = new JButton("Next Player");
	public JButton makeAccusationButton = new JButton("Make Accusation");
	
	public ControlGui() {
		setName("SSAL Clue Game");
		add(createLayout());
	}

	private JPanel createLayout() {
		JPanel wholePanel = new JPanel(new GridLayout(2, 0));
		
		JPanel top = new JPanel(new GridLayout(0,3));
		
		JPanel turnPanel = new JPanel(new GridLayout(2,1));
		
		JLabel turnLabel = new JLabel("Whose turn?"); 
		
		turnLabel.setHorizontalAlignment(JLabel.CENTER);
		turnPanel.add(turnLabel);
		
		JPanel wrapper =new JPanel();
		wrapper.add(turnPanel);
		
		turnName.setHorizontalAlignment(JTextField.CENTER);
		turnName.setEnabled(false);
		turnName.setEditable(false);
		turnPanel.add(turnName);
		
		top.add(wrapper);
		
		top.add(nextPlayerButton);
		top.add(makeAccusationButton);
		wholePanel.add(top);
		
		JPanel bottom = new JPanel(new FlowLayout()); 
		JPanel dieRoll = new JPanel(); 
		dieRoll.setBorder(new TitledBorder (new EtchedBorder(), "Die Roll"));
		rollNum.setEditable(false);
		dieRoll.add(rollNum); 
		bottom.add(dieRoll);
		
		JPanel guess = new JPanel();
		
		guessLabel.setEditable(false);
		guess.add(guessLabel); 
		guess.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		bottom.add(guess);
		
		JPanel guessResult = new JPanel();
		
		guessResultLabel.setEditable(false);
		guessResult.add(guessResultLabel); 
		guessResult.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		bottom.add(guessResult);
		wholePanel.add(bottom);
		return wholePanel; 
	}
	
	
//	public static void main(String[] args) {
//		ControlGui gui = new ControlGui();
//		gui.setVisible(true);
//	}

}
