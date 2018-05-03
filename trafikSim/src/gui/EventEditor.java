package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

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
public class EventEditor extends JPanel implements ActionListener{ 	//this is a JPanel object -> with initGUI 1config from constructor call, we create a object

	private final String LOAD = "load";
	private final String SAVE = "save";
	
	private JFileChooser _fc;	
	private File _currentFile;
	private JTextArea _textArea;
	private TitledBorder _titledBrd;

	public static Border defaultBorder = BorderFactory.createLineBorder(Color.black, 2);
	
	
	public EventEditor(TrafficSimulator model, Controller ctrl, String currentFile) {
		_currentFile = new File(currentFile);
		initGUI();
	}

	public JTextArea getTextArea() {
		return _textArea;
	}
	
	private void initGUI() {
		
		_fc = new JFileChooser();

		_textArea = new JTextArea(40, 30);
		_textArea.setEditable(true);
		_textArea.setLineWrap(true);
		_textArea.setWrapStyleWord(true);
		
		if(_currentFile == null)
			_titledBrd = new TitledBorder(defaultBorder, "Events");
		else {
			_titledBrd = new TitledBorder(defaultBorder, "Events: " + _currentFile.getName());
			_textArea.setText(readFile(_currentFile));
		}
			
		setBorder(_titledBrd);
		setLayout(new BorderLayout());
		
		JScrollPane area = new JScrollPane(_textArea);
		add(area, BorderLayout.CENTER);
	}
	
	public TitledBorder getBorder(){
		return _titledBrd;
	}
	
	public void setBorder(String s){	//TODO pagina 24 pdf ana
		_titledBrd.setTitle(s);
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (LOAD.equals(e.getActionCommand())) 
			loadFile();
		else if (SAVE.equals(e.getActionCommand()))
			saveFile();	
	}
	
	public void saveFile() {			
		int returnVal = _fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = _fc.getSelectedFile();
			writeFile(file, _textArea.getText());
		}
	}

	public void loadFile() {
		int returnVal = _fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = _fc.getSelectedFile();
			String s = readFile(file);
			_textArea.setText(s);
			_titledBrd = new TitledBorder(defaultBorder, "Events: " + file.getName());
			setBorder(_titledBrd);
		}
	
	}

	public static String readFile(File file) {
		String s = "";
		try {
			Scanner scan = new Scanner(file);
			s = scan.useDelimiter("\\A").next();
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return s;
	}

	public static void writeFile(File file, String content) {
		try {
			PrintWriter pw = new PrintWriter(file);
			pw.print(content);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
