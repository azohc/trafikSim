package gui;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.State;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import model.Event;
import model.RoadMap;
import model.TrafficSimulator;
import control.Controller;
import control.SimulatorError;


@SuppressWarnings("serial")
public class Toolbar extends JToolBar implements ActionListener, TrafficSimulatorObserver{

	private final String LOAD = "load";
	private final String SAVE = "save";
	private final String CLEAR = "clear";
	private final String QUIT = "quit";
	private final String EVLOAD = "load events";
	private final String RUNSTEPS = "run steps";
	private final String STOP = "stop";
	private final String RESET = "reset";
	private final String GENERATE = "generate reports";
	private final String CLEARREP = "clear reports";
	private final String SAVEREP = "save reports";
	
	private TrafficSimulator _model;
	private Controller _ctrl;
	private EventEditor _eventEditor;
	
	private JFileChooser _fc;	
	private JLabel _status;
		
	private JSpinner _steps;
	private JSpinner _delay;
	private JTextField _time;
	
	private JButton load;
	private JButton save;
	private JButton clear;
	private JButton inject;
	private JButton runSteps;
	private JButton stop;
	private JButton reset;
	private JButton quit;
	private JButton saveRep;
	private JButton clearRep;
	private JButton generate;
	
	boolean interruptExecution = false;
	boolean running = false;
	
	private JTextArea _eventEditorTxtArea;
	private JTextArea _reportTxtArea;
	
	private JMenuBar _menuBar;

	public Toolbar(TrafficSimulator model, Controller ctrl, EventEditor eventEditor,
			ReportTextArea reportArea, JLabel status, JMenuBar menuBar) {
		_model = model;
		_eventEditor = eventEditor;
		_ctrl = ctrl;
		_eventEditorTxtArea = eventEditor.getTextArea();
		_reportTxtArea = reportArea.getTxtArea();
		_status = status;
		_menuBar = menuBar;
		initGUI();
		_model.addObserver(this);
	}

	private void initGUI() {
		
		
		createEventEditorPanelButtons();
		addSeparator();
		createControlButtons();

		_fc = new JFileChooser();	
		_fc.setCurrentDirectory(new File("./cv_docs"));

		add(new JLabel(" Delay: ")); 
		_delay = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));	
		_delay.setMaximumSize(new Dimension(70, 30));
		add(_delay);
		
		add(new JLabel(" Steps: ")); 
		_steps = new JSpinner(new SpinnerNumberModel(5, 1, 1000, 1));
		_steps.setMaximumSize(new Dimension(70, 30));
		add(_steps);
		
		add(new JLabel(" Time: "));
		_time = new JTextField("0", 5);
		_time.setEditable(false);
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
		
		load = new JButton();
		load.setActionCommand(LOAD);
		load.setToolTipText("Load a file");
		load.addActionListener(_eventEditor);
		load.setIcon(new ImageIcon(loadImage("cv_docs/icons/open.png")));
		this.add(load);

		save = new JButton();
		save.setActionCommand(SAVE);
		save.setToolTipText("Save a file");
		save.addActionListener(_eventEditor);
		save.setIcon(new ImageIcon(loadImage("cv_docs/icons/save.png")));
		this.add(save);		
		
		clear = new JButton();
		clear.setActionCommand(CLEAR);
		clear.setToolTipText("Clear Text");
		clear.addActionListener(this);
		clear.setIcon(new ImageIcon(loadImage("cv_docs/icons/clear.png")));
		this.add(clear);
	
	}
	
	public void createControlButtons(){
		
		inject = new JButton();
		inject.setActionCommand(EVLOAD);
		inject.setToolTipText("Load events from event editor");
		inject.addActionListener(this);
		inject.setIcon(new ImageIcon(loadImage("cv_docs/icons/events.png")));
		add(inject);
		
		runSteps = new JButton();
		runSteps.setActionCommand(RUNSTEPS);
		runSteps.setToolTipText("Run selected amount of steps");
		runSteps.addActionListener(this);
		runSteps.setIcon(new ImageIcon(loadImage("cv_docs/icons/play.png")));
		this.add(runSteps);

		stop = new JButton();
		stop.setActionCommand(STOP);
		stop.setToolTipText("Stop simulator");
		stop.addActionListener(this);
		stop.setIcon(new ImageIcon(loadImage("cv_docs/icons/stop.png")));
		add(stop);	
				
		reset = new JButton();
		reset.setActionCommand(RESET);
		reset.setToolTipText("Reset simulator");
		reset.addActionListener(this);
		reset.setIcon(new ImageIcon(loadImage("cv_docs/icons/reset.png")));
		add(reset);
		
	}
	
	public void createExitButton(){
		quit = new JButton();
		quit.setActionCommand(QUIT);
		quit.setToolTipText("Exit simulator");
		quit.addActionListener(this);
		quit.setIcon(new ImageIcon(loadImage("cv_docs/icons/exit.png")));
		this.add(quit);
	}
	
	public void createReportButton(){
		generate = new JButton();
		generate.setActionCommand(GENERATE);
		generate.setToolTipText("Generate Reports");
		generate.addActionListener(this);
		generate.setIcon(new ImageIcon(loadImage("cv_docs/icons/report.png")));
		this.add(generate);
		
		clearRep = new JButton();
		clearRep.setActionCommand(CLEARREP);
		clearRep.setToolTipText("Clear Reports");
		clearRep.addActionListener(this);
		clearRep.setIcon(new ImageIcon(loadImage("cv_docs/icons/delete_report.png")));
		this.add(clearRep);
		
		saveRep = new JButton();
		saveRep.setActionCommand(SAVEREP);
		saveRep.setToolTipText("Save Reports");
		saveRep.addActionListener(this);
		saveRep.setIcon(new ImageIcon(loadImage("cv_docs/icons/save_report.png")));
		this.add(saveRep);
		
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
	
		if (CLEAR.equals(e.getActionCommand())){
			_eventEditorTxtArea.setText("");
			_status.setText("The reports area has been cleared!");
		}
		else if (QUIT.equals(e.getActionCommand()))
			System.exit(0);
		else if (EVLOAD.equals(e.getActionCommand())){
			_status.setText("Events have been loaded!");
			injectEvents();
		}
		
		else if (RUNSTEPS.equals(e.getActionCommand())){
			
			Thread runThread = new Thread(){	
				public void run(){
					
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							setButtons(false);
						}
					});
					
					int i = 0;
					running = true;
					while(i < (int)(_steps.getValue()) && !interruptExecution) { 
						_model.run(1);			
						try {
							Thread.sleep((int)(_delay.getValue()));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						i++;
					}
					
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							setButtons(true);
						}
					});
					
					interruptExecution = false;
					running = false;
				}
			};
			runThread.start();
		
		}
		
		else if (STOP.equals(e.getActionCommand())){
			if(running) {
				interruptExecution = true;
				_status.setText("Simulator execution stopped.");
			}
		}
		
		else if (RESET.equals(e.getActionCommand())){
			_model.reset();
			_status.setText("Simulator has been reset!");
			_status.getSize();
		}	
		else if(GENERATE.equals(e.getActionCommand())){
			_reportTxtArea.setText(_model.getMap().generateReport(_model.getTime()));
			_status.setText("Reports have been generated!");
		}
		else if(CLEARREP.equals(e.getActionCommand())){
			_reportTxtArea.setText("");
			_status.setText("Reports area has been cleared!");
		}
		else if(SAVEREP.equals(e.getActionCommand())){
			saveFile();
			_status.setText("Reports have been saved!");
		}
	
	}
	
	private void setButtons(boolean b){
		//buttons
		for(int i = 0; i < getComponentCount(); i++)
			if(!getComponent(i).equals(stop))
				getComponent(i).setEnabled(b);

		//menus
		for(int i = 0; i < _menuBar.getComponentCount(); i++)
			_menuBar.getComponent(i).setEnabled(b);
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

	private void injectEvents() {	
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
		_status.setText(e.getMessage());
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