package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.TrafficSimulator;
import control.Controller;

@SuppressWarnings("serial")
public class EventEditor extends JPanel { 	//this is a JPanel object -> with initGUI 1config from constructor call, we create a object

	private JTextArea textArea;
	private TrafficSimulator _model;
	private Controller _ctrl;
	private TitledBorder _titledBrd;

	public static Border defaultBorder = BorderFactory.createLineBorder(Color.black, 2);
	
	
	public EventEditor(TrafficSimulator model, Controller ctrl) {
		_model = model;
		_ctrl = ctrl;
		initGUI();
	}

	public JTextArea getTextArea() {
		return textArea;
	}
	
	private void initGUI() {
		_titledBrd = new TitledBorder(defaultBorder, "Events");
		this.setBorder(_titledBrd);
		this.setLayout(new BorderLayout());
		textArea = new JTextArea("");
		textArea.setEditable(true);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane area = new JScrollPane(textArea);
		this.add(area, BorderLayout.CENTER);
	}
	
	public TitledBorder getBorder(){
		return _titledBrd;
	}
	
	public void setBorder(String s){
		_titledBrd.setTitle(s);
		repaint();
	}
	

}
