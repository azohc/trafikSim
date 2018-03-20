package tp.examples.swing.misc;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ButtonExample extends JFrame implements ActionListener {

	// Definition of global values and items that are part of the GUI.

	static int i = 0;
	int redScoreAmount = 0;
	int blueScoreAmount = 0;

	JPanel titlePanel, scorePanel, buttonPanel;
	JLabel redLabel, blueLabel, redScore, blueScore;
	JButton redButton, blueButton, resetButton;

	public ButtonExample() {
		super("[=] JButton Scores! [=]");
		initGUI();
	}

	public void initGUI() {

		// We create a bottom JPanel to place everything on.
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setOpaque(true);

		// Creation of a Panel to contain the title labels
		titlePanel = new JPanel();
		titlePanel.setLayout(null);
		titlePanel.setLocation(10, 0);
		titlePanel.setSize(250, 30);
		mainPanel.add(titlePanel);

		redLabel = new JLabel("Red Team");
		redLabel.setLocation(0, 0);
		redLabel.setSize(120, 30);
		redLabel.setHorizontalAlignment(0);
		redLabel.setForeground(Color.red);
		titlePanel.add(redLabel);

		blueLabel = new JLabel("Blue Team");
		blueLabel.setLocation(130, 0);
		blueLabel.setSize(120, 30);
		blueLabel.setHorizontalAlignment(0);
		blueLabel.setForeground(Color.blue);
		titlePanel.add(blueLabel);

		// Creation of a Panel to contain the score labels.
		scorePanel = new JPanel();
		scorePanel.setLayout(null);
		scorePanel.setLocation(10, 40);
		scorePanel.setSize(260, 30);
		mainPanel.add(scorePanel);

		redScore = new JLabel("" + redScoreAmount);
		redScore.setLocation(0, 0);
		redScore.setSize(120, 30);
		redScore.setHorizontalAlignment(0);
		scorePanel.add(redScore);

		blueScore = new JLabel("" + blueScoreAmount);
		blueScore.setLocation(130, 0);
		blueScore.setSize(120, 30);
		blueScore.setHorizontalAlignment(0);
		scorePanel.add(blueScore);

		// Creation of a Panel to contain all the JButtons.
		buttonPanel = new JPanel();
		buttonPanel.setLayout(null);
		buttonPanel.setLocation(10, 80);
		buttonPanel.setSize(280, 70);
		mainPanel.add(buttonPanel);

		// We create a button and manipulate it using the syntax we have
		// used before. Now each button has an ActionListener which posts
		// it's action out when the button is pressed.

		redButton = new JButton("Red Score!");
		redButton.setLocation(0, 0);
		redButton.setSize(120, 30);
		redButton.addActionListener(this);
		buttonPanel.add(redButton);

		blueButton = new JButton("Blue Score!");
		blueButton.setLocation(150, 0);
		blueButton.setSize(120, 30);
		blueButton.addActionListener(this);
		buttonPanel.add(blueButton);

		resetButton = new JButton("Reset Score");
		resetButton.setLocation(0, 40);
		resetButton.setSize(250, 30);
		resetButton.addActionListener(this);
		buttonPanel.add(resetButton);

		this.setContentPane(mainPanel);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(280, 190);
		this.setVisible(true);

	}

	// This is the new ActionPerformed Method.
	// It catches any events with an ActionListener attached.
	// Using an if statement, we can determine which button was pressed
	// and change the appropriate values in our GUI.

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == redButton) {
			redScoreAmount = redScoreAmount + 1;
			redScore.setText("" + redScoreAmount);
		} else if (e.getSource() == blueButton) {
			blueScoreAmount = blueScoreAmount + 1;
			blueScore.setText("" + blueScoreAmount);
		} else if (e.getSource() == resetButton) {
			redScoreAmount = 0;
			blueScoreAmount = 0;
			redScore.setText("" + redScoreAmount);
			blueScore.setText("" + blueScoreAmount);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ButtonExample();
			}
		});
	}
}
