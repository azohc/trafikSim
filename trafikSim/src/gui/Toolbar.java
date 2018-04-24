package gui;
import javax.swing.*;

import control.Controller;
import model.TrafficSimulator;






import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


@SuppressWarnings("serial")
public class Toolbar extends JToolBar implements ActionListener {

	private final String LOAD = "load";
	private final String SAVE = "save";
	private final String CLEAR = "clear";
	private final String QUIT = "quit";
	private final String EVLOAD = "load events";
	private final String RUNSTEPS = "run steps";
	private final String RESET = "reset";
	
	
	
	private TrafficSimulator _model;
	private Controller _ctrl;
	
	JSpinner _steps;
	JTextField _time;
	
	private JFileChooser _fc;	
	private JTextArea _txtArea;


	public Toolbar(TrafficSimulator model, Controller ctrl, JTextArea txtArea) {
		_model = model;
		_ctrl = ctrl;
		_txtArea = txtArea;
		initGUI();
	}

	private void initGUI() {
		
		_fc = new JFileChooser();
		
		createEventEditorPanelButtons();
		addSeparator();
		createControlButtons();

		
		add(new JLabel(" Steps: ")); 
		_steps = new JSpinner(new SpinnerNumberModel(5, 1, 1000, 1));
		_steps.setMaximumSize(new Dimension(70, 30));
		add(_steps);
		add(new JLabel(" Time: "));
		_time = new JTextField("0", 5);
		_time.setMaximumSize(new Dimension(70, 30));
		add(_time);
		
		
		
		
		
		addSeparator();
		createExitButton();
	}

	public void setTextArea(JTextArea txtArea) {
		_txtArea = txtArea;
	}
	
	public void createEventEditorPanelButtons() {
		
		JButton load = new JButton();
		load.setActionCommand(LOAD);
		load.setToolTipText("Load a file");
		load.addActionListener(this);
		load.setIcon(new ImageIcon(loadImage("cv_docs/icons/open.png")));
		this.add(load);

		JButton save = new JButton();
		save.setActionCommand(SAVE);
		save.setToolTipText("Save a file");
		save.addActionListener(this);
		save.setIcon(new ImageIcon(loadImage("cv_docs/icons/save.png")));
		this.add(save);		
		
		JButton clear = new JButton();
		clear.setActionCommand(CLEAR);
		clear.setToolTipText("Clear Text");
		clear.addActionListener(this);
		clear.setIcon(new ImageIcon(loadImage("cv_docs/icons/clear.png")));
		this.add(clear);
	
	}
	
	public void createControlButtons(){
		
		JButton loadFromEventsEditor = new JButton();
		loadFromEventsEditor.setActionCommand(EVLOAD);
		loadFromEventsEditor.setToolTipText("Load events from event editor");
		loadFromEventsEditor.addActionListener(this);
		loadFromEventsEditor.setIcon(new ImageIcon(loadImage("cv_docs/icons/events.png")));
		this.add(loadFromEventsEditor);
		
		JButton runSteps = new JButton();
		runSteps.setActionCommand(RUNSTEPS);
		runSteps.setToolTipText("Run selected amount of steps");
		runSteps.addActionListener(this);
		runSteps.setIcon(new ImageIcon(loadImage("cv_docs/icons/play.png")));
		this.add(runSteps);
		
		JButton reset = new JButton();
		reset.setActionCommand(RESET);
		reset.setToolTipText("Reset simulator");
		reset.addActionListener(this);
		reset.setIcon(new ImageIcon(loadImage("cv_docs/icons/reset.png")));
		this.add(reset);
		
	}
	
	public void createExitButton(){
		JButton quit = new JButton();
		quit.setActionCommand(QUIT);
		quit.setToolTipText("Exit simulator");
		quit.addActionListener(this);
		quit.setIcon(new ImageIcon(loadImage("cv_docs/icons/exit.png")));
		this.add(quit);
	}

	public void actionPerformed(ActionEvent e) {
		if (LOAD.equals(e.getActionCommand())) 
			loadFile();
		else if (SAVE.equals(e.getActionCommand()))
			saveFile();
		else if (CLEAR.equals(e.getActionCommand()))
			_txtArea.setText("");
		else if (QUIT.equals(e.getActionCommand()))
			System.exit(0);
		else if (EVLOAD.equals(e.getActionCommand()))
			injectEvents();
		else if (RUNSTEPS.equals(e.getActionCommand()))
			_model.run((int)_steps.getValue());	//check
		else if (RESET.equals(e.getActionCommand()))
			_model.reset();
	
	}

	private void injectEvents() {	// TODO read events from event editor, and inject to simulatoR
		_ctrl.loadEvents(new ByteArrayInputStream(_txtArea.getText().getBytes()));
	}

	private void saveFile() {			
		int returnVal = _fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = _fc.getSelectedFile();
			writeFile(file, _txtArea.getText());
		}
	}

	private void loadFile() {
		int returnVal = _fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = _fc.getSelectedFile();
			String s = readFile(file);
			_txtArea.setText(s);
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

	private static Image loadImage(String path) {
		return Toolkit.getDefaultToolkit().createImage(path);
	}
}