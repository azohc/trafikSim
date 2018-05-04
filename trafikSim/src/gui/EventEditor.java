package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import model.NewRoundRobinJunctionEvent;
import model.TrafficSimulator;
import control.Controller;
import control.NewMostCrowdedJunctionEventBuilder;
import control.NewRoundRobinEventBuilder;

@SuppressWarnings("serial")
public class EventEditor extends JPanel implements ActionListener{ 	//this is a JPanel object -> with initGUI 1config from constructor call, we create a object

	private final String LOAD = "load";
	private final String SAVE = "save";
	
	private JScrollPane _scrollPane;
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
		_fc.setCurrentDirectory(new File("./cv_docs"));
		
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
		
		_scrollPane = new JScrollPane(_textArea);
		add(_scrollPane, BorderLayout.CENTER);
		createPopupMenu();
	}
	
	public TitledBorder getBorder(){
		return _titledBrd;
	}
	
	public void setBorder(String s){
		_titledBrd.setTitle(s);
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (LOAD.equals(e.getActionCommand())) 
			loadFile();
		else if (SAVE.equals(e.getActionCommand()))
			saveFile();	
		
		else if("RRJUNCTION".equals(e.getActionCommand()))
			_textArea.setText(_textArea.getText() + 
					System.getProperty("line.separator") + System.getProperty("line.separator") +
							new NewRoundRobinEventBuilder().template());
		
		else if("MCJUNCTION".equals(e.getActionCommand()))
			_textArea.setText(_textArea.getText() +
					System.getProperty("line.separator") + System.getProperty("line.separator") +
							new NewMostCrowdedJunctionEventBuilder().template());
		
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
	
	public void createPopupMenu() {	//TODO : menu de p23 pdf ana
		
		JPopupMenu menu = new JPopupMenu();
		
		JMenu templates = new JMenu("Add Template");
		
		JMenuItem rrJunction = new JMenuItem("New RR Junction");
		templates.add(rrJunction);
		rrJunction.setActionCommand("RRJUNCTION");
		rrJunction.addActionListener(this);
		
		
		JMenuItem mcJunction = new JMenuItem("New MC Junction");
		templates.add(mcJunction);
		mcJunction.setActionCommand("MCJUNCTION");
		mcJunction.addActionListener(this);
		
		
		JMenuItem load = new JMenuItem("Load");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem clear = new JMenuItem("Clear");
		
		menu.add(templates);
		menu.addSeparator();
		menu.add(load);
		menu.add(save);
		menu.add(clear);
			
		class MouseClickListener extends MouseAdapter{
			
			public void mousePressed(MouseEvent e){
		        if (e.isPopupTrigger())
		            doPop(e);
		    }

		    public void mouseReleased(MouseEvent e){
		        if (e.isPopupTrigger())
		            doPop(e);
		    }

		    private void doPop(MouseEvent e){
		        menu.show(e.getComponent(), e.getX(), e.getY());
		    }
		}
		
		_textArea.addMouseListener(new MouseClickListener());
	
	}
	

}
