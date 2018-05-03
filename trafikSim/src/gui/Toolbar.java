package gui;
import javax.swing.*;

import control.Controller;
import control.SimulatorError;
import model.Event;
import model.RoadMap;
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
import java.util.List;
import java.util.Scanner;


@SuppressWarnings("serial")
public class Toolbar extends JToolBar implements ActionListener, TrafficSimulatorObserver{

	private final String LOAD = "load";
	private final String SAVE = "save";
	private final String CLEAR = "clear";
	private final String QUIT = "quit";
	private final String EVLOAD = "load events";
	private final String RUNSTEPS = "run steps";
	private final String RESET = "reset";
	private final String GENERATE = "generate reports";
	private final String CLEARREP = "clear reports";
	private final String SAVEREP = "save reports";
	
	private TrafficSimulator _model;
	private Controller _ctrl;
	private EventEditor _eventEditor;
	
	private JFileChooser _fc;	
	private JLabel _statusBar;
		
	JSpinner _steps;
	JTextField _time;
	
	private JTextArea _eventEditorTxtArea;
	private JTextArea _reportTxtArea;

	public Toolbar(TrafficSimulator model, Controller ctrl, EventEditor eventEditor, ReportTextArea reportArea, JPanel statusBar) {
		_model = model;
		_eventEditor = eventEditor;
		_ctrl = ctrl;
		_eventEditorTxtArea = eventEditor.getTextArea();
		_reportTxtArea = reportArea.getTxtArea();
		_statusBar = (JLabel) statusBar.getComponent(0);
		initGUI();
	}

	private void initGUI() {
		
		
		createEventEditorPanelButtons();
		addSeparator();
		createControlButtons();

		_fc = new JFileChooser();	

		add(new JLabel(" Steps: ")); 
		_steps = new JSpinner(new SpinnerNumberModel(5, 1, 1000, 1));
		_steps.setMaximumSize(new Dimension(70, 30));
		add(_steps);
		
		add(new JLabel(" Time: "));
		_time = new JTextField("0", 5);
		_time.setMaximumSize(new Dimension(70, 30));
		add(_time);
		
		addSeparator();
		createReportButton();
		addSeparator();
		createExitButton();
	}

	public void setTextArea(JTextArea txtArea) {
		_eventEditorTxtArea = txtArea;
	}
	
	public void createEventEditorPanelButtons() {
		
		JButton load = new JButton();
		load.setActionCommand(LOAD);
		load.setToolTipText("Load a file");
		load.addActionListener(_eventEditor);
		load.setIcon(new ImageIcon(loadImage("cv_docs/icons/open.png")));
		this.add(load);

		JButton save = new JButton();
		save.setActionCommand(SAVE);
		save.setToolTipText("Save a file");
		save.addActionListener(_eventEditor);
		save.setIcon(new ImageIcon(loadImage("cv_docs/icons/save.png")));
		this.add(save);		
		
		JButton clear = new JButton();
		clear.setActionCommand(CLEAR);
		clear.setToolTipText("Clear Text");
		clear.addActionListener(_eventEditor);
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
	
	public void createReportButton(){
		JButton generate = new JButton();
		generate.setActionCommand(GENERATE);
		generate.setToolTipText("Generate Reports");
		generate.addActionListener(this);
		generate.setIcon(new ImageIcon(loadImage("cv_docs/icons/report.png")));
		this.add(generate);
		
		JButton clearRep = new JButton();
		clearRep.setActionCommand(CLEARREP);
		clearRep.setToolTipText("Clear Reports");
		clearRep.addActionListener(this);
		clearRep.setIcon(new ImageIcon(loadImage("cv_docs/icons/delete_report.png")));
		this.add(clearRep);
		
		JButton saveRep = new JButton();
		saveRep.setActionCommand(SAVEREP);
		saveRep.setToolTipText("Save Reports");
		saveRep.addActionListener(this);
		saveRep.setIcon(new ImageIcon(loadImage("cv_docs/icons/save_report.png")));
		this.add(saveRep);
		
		
	}

	public void actionPerformed(ActionEvent e) {
	
		if (CLEAR.equals(e.getActionCommand())){
			_eventEditorTxtArea.setText("");
			_statusBar.setText("The reports area has been cleared!");
		}
		else if (QUIT.equals(e.getActionCommand()))
			System.exit(0);
		else if (EVLOAD.equals(e.getActionCommand())){
			injectEvents();
			_statusBar.setText("Events have been loaded!");
		}
		else if (RUNSTEPS.equals(e.getActionCommand())){
			_model.run((int)_steps.getValue());	//check
			_statusBar.setText("Simulator Advanced!");
		}
		else if (RESET.equals(e.getActionCommand())){
			_model.reset();
			_statusBar.setText("Simulator has been reset!");
		}	
		else if(GENERATE.equals(e.getActionCommand())){
			_reportTxtArea.setText(_model.getMap().generateReport(_model.getTime()));
			_statusBar.setText("Reports have been generated!");
		}
		else if(CLEARREP.equals(e.getActionCommand())){
			_reportTxtArea.setText("");
			_statusBar.setText("Reports area has been cleared!");
		}
		else if(SAVEREP.equals(e.getActionCommand())){
			saveFile();
			_statusBar.setText("Reports have been saved!");
		}
	
	}
	
	private void saveFile() {
		int returnVal = _fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = _fc.getSelectedFile();
			writeFile(file, _reportTxtArea.getText());
		}
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

	private void injectEvents() {	// TODO read events from event editor, and inject to simulatoR
		_ctrl.loadEvents(new ByteArrayInputStream(_eventEditorTxtArea.getText().getBytes()));
	}

	

	private static Image loadImage(String path) {
		return Toolkit.getDefaultToolkit().createImage(path);
	}
	
	public int getSteps(){
		
		return (int)_steps.getValue();
	}

	@Override
	public void addSimError(int time, RoadMap map, List<Event> events,
			SimulatorError e) {
		
	}

	@Override
	public void addStep(int time, RoadMap map, List<Event> events) {
		_time.setText("" + time);
		
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {
		_time.setText("" + time);
		
	}

	@Override
	public void addReset(int time, RoadMap map, List<Event> events) {
		_time.setText("" + time);
		
	}

	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
		_time.setText("" + time);
		
	}
}