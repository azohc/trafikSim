package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.TrafficSimulator;
import control.Controller;

public class TextEditor extends JPanel { 	//this is a JPanel object -> with initGUI config from constructor call, we create a object

	private JFileChooser fc;
	private JTextArea textArea;
	private TrafficSimulator _model;
	private Controller _ctrl;

	public TextEditor(TrafficSimulator model, Controller ctrl) {
		_model = model;
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		this.setLayout( new BorderLayout() );
		textArea = new JTextArea("");
		textArea.setEditable(true);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane area = new JScrollPane(textArea);
		this.add(area, BorderLayout.CENTER);

	}

}
