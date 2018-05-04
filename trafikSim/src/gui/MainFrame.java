package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import model.Event;
import model.RoadMap;
import model.TrafficSimulator;
import control.Controller;
import control.SimulatorError;
import gui.rdmap.RoadMapDisplay;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements TrafficSimulatorObserver, ActionListener{

	private final String LOAD = "load";
	private final String SAVE = "save";
	private final String CLEAR = "clear";
	private final String QUIT = "quit";
	private final String RUN = "run";
	private final String RESET = "reset";
	private final String REDIOUT = "redirect output";
	private final String GENERATE = "generate";
	private final String CLEARREP = "clear reports";
	
	
	private String _currentFile;
	
	// OUTER BORDER
	public static Border defaultBorder = BorderFactory.createLineBorder(Color.black, 2);
	
	private Controller _ctrl;
	private TrafficSimulator _model;
	private Toolbar _tb;
	
	// TEXT AREAS
	private EventEditor _eventEditor;
	private ReportTextArea _reportArea;
	
	// TABLES
	private EventQueueTable _eventTable;
	private VehiclesTable _vehTable;
	private RoadsTable _rdTable;
	private JunctionsTable _juncTable;
	
	// ROADMAP
	private RoadMapDisplay _rdMapDisplay;
		
	// PANELS
	private JPanel mainPanel;
	private JPanel innerPanel;
	private JPanel upperPanel;
	private JPanel lowerPanel;
	private JPanel lowLeftPanel;
	private JPanel statusPanel;

	
	// STATUSBAR
	private JLabel statusLabel;
	
	
	public MainFrame(Controller ctrl, TrafficSimulator model, String currentFile){
		super("Traffic Simulator");
		_currentFile = currentFile;
		
		_ctrl = ctrl;
		_model = model;
		
		initGUI();
		model.addObserver(this);
	}

	private void initGUI() {
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		setContentPane(mainPanel);
		
		//PANELS
		innerPanel = new JPanel();
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));	//check
		mainPanel.add(innerPanel, BorderLayout.CENTER);
		
		upperPanel = new JPanel();
		upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.X_AXIS));
		
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.X_AXIS));
		
		lowLeftPanel = new JPanel();
		lowLeftPanel.setLayout(new BoxLayout(lowLeftPanel, BoxLayout.Y_AXIS));
		
		innerPanel.add(upperPanel);
		innerPanel.add(lowerPanel);
		lowerPanel.add(lowLeftPanel);
		
		upperPanel.setMinimumSize(new Dimension(200, 150));
		lowerPanel.setMinimumSize(new Dimension(200, 250));
		
		//STATUSBAR
		statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	
		statusLabel = new JLabel();
		statusPanel.add(statusLabel);
		mainPanel.add(statusPanel, BorderLayout.SOUTH);
		
		//EVENT EDITOR
		_eventEditor = new EventEditor(_model,_ctrl, _currentFile, statusPanel);
		upperPanel.add(_eventEditor);
		
		//EVENT QUEUE
		_eventTable = new EventQueueTable(_model);
		upperPanel.add(_eventTable);
		
		//REPORT AREA
		_reportArea = new ReportTextArea(_model, _ctrl);
		upperPanel.add(_reportArea);
		
		//MENU BAR
		setJMenuBar(createMenuBar());
		
		//TABLES
		_vehTable = new VehiclesTable(_model);
		lowLeftPanel.add(_vehTable);
		
		_rdTable = new RoadsTable(_model);
		lowLeftPanel.add(_rdTable);
		
		_juncTable = new JunctionsTable(_model);
		lowLeftPanel.add(_juncTable);
		
		
		//ROADMAP
		_rdMapDisplay = new RoadMapDisplay(_model);
		lowerPanel.add(_rdMapDisplay);
		
	

	
		//TOOLBAR
		_tb = new Toolbar(_model, _ctrl, _eventEditor, _reportArea, statusPanel);
		mainPanel.add(_tb, BorderLayout.PAGE_START);
		
		
		//DIMENSIONS
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) (screenSize.height * 0.95);
		int width = (int) (screenSize.width * 0.7);
		setPreferredSize(new Dimension(width, height));
	
		Dimension mainPanelSize = mainPanel.getSize();
		upperPanel.setMinimumSize(new Dimension(mainPanelSize.width, (int)(mainPanelSize.height*0.6)));
		
		Dimension lowerPanelSize = lowerPanel.getSize();
		lowLeftPanel.setMinimumSize(new Dimension((int)(lowerPanelSize.width/2), lowerPanelSize.height));
		_rdMapDisplay.setMinimumSize(new Dimension((int)(lowerPanelSize.width/2), lowerPanelSize.height));
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}
	
	public void setStatus(String s){
		
		statusLabel.setText(s);
	}
	
	@Override
	public void addSimError(int time, RoadMap map, List<Event> events,
			SimulatorError e) {
		setStatus(e.getMessage());
	}

	@Override
	public void addStep(int time, RoadMap map, List<Event> events) {
		_reportArea.setText(map.generateReport(time)); 
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {

	}

	@Override
	public void addReset(int time, RoadMap map, List<Event> events) {
		
	}

	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
		
	}
	

	public JMenuBar createMenuBar() {
		JMenuItem load, save, clear, quit;

		JMenuBar menuBar = new JMenuBar();
			
		JMenu file = new JMenu("File");
		menuBar.add(file);
		file.setMnemonic(KeyEvent.VK_F);

		load = new JMenuItem("Load");
		load.setActionCommand(LOAD);
		load.addActionListener(this);
		load.setMnemonic(KeyEvent.VK_L);
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.ALT_MASK));

		save = new JMenuItem("Save");
		save.setActionCommand(SAVE);
		save.addActionListener(this);
		save.setMnemonic(KeyEvent.VK_S);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.ALT_MASK));

		clear = new JMenuItem("Clear");
		clear.setActionCommand(CLEAR);
		clear.addActionListener(this);
		clear.setMnemonic(KeyEvent.VK_C);
		clear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.ALT_MASK));

		quit = new JMenuItem("Quit");
		quit.setActionCommand(QUIT);
		quit.addActionListener(this);
		quit.setMnemonic(KeyEvent.VK_Q);
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				ActionEvent.ALT_MASK));

		JMenuItem run, reset, rediOutput;
		JMenu simu = new JMenu("Simulator");
		menuBar.add(simu);
		simu.setMnemonic(KeyEvent.VK_S);

		run = new JMenuItem("Run");
		run.setActionCommand(RUN);
		run.addActionListener(this);
		run.setMnemonic(KeyEvent.VK_R);
		run.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				ActionEvent.ALT_MASK));
		
		reset = new JMenuItem("Reset");
		reset.setActionCommand(RESET);
		reset.addActionListener(this);
		reset.setMnemonic(KeyEvent.VK_E);
		reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				ActionEvent.ALT_MASK));
		
		rediOutput = new JMenuItem("Redirect Output");
		rediOutput.setActionCommand(REDIOUT);
		rediOutput.addActionListener(this);
		rediOutput.setMnemonic(KeyEvent.VK_O);
		rediOutput.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.ALT_MASK));
		
		
		JMenuItem generate, clearRep;
		JMenu reports = new JMenu("Reports");
		menuBar.add(reports);
		simu.setMnemonic(KeyEvent.VK_A);

		generate = new JMenuItem("Generate");
		generate.setActionCommand(GENERATE);
		generate.addActionListener(this);
		generate.setMnemonic(KeyEvent.VK_B);
		generate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,
				ActionEvent.ALT_MASK));
		
		clearRep = new JMenuItem("Clear");
		clearRep.setActionCommand(CLEARREP);
		clearRep.addActionListener(this);
		clearRep.setMnemonic(KeyEvent.VK_J);
		clearRep.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J,
				ActionEvent.ALT_MASK));
		
		
		file.add(load);
		file.add(save);
		file.addSeparator();
		file.add(clear);
		file.addSeparator();
		file.add(quit);
		simu.add(run);
		simu.add(reset);
		//simu.add(rediOutput);
		reports.add(generate);
		reports.add(clearRep);
		
		return menuBar;
	}

	public void actionPerformed(ActionEvent e) {
		if (SAVE.equals(e.getActionCommand())){
			_eventEditor.saveFile();
			setStatus("Events have been saved!");
		}
		else if (LOAD.equals(e.getActionCommand())){
			_eventEditor.loadFile();
			setStatus("Events have been loaded!");
		}
		
		else if (CLEAR.equals(e.getActionCommand())){
			_eventEditor.getTextArea().setText("");
			setStatus("Events editor cleared!");
		}
		else if (QUIT.equals(e.getActionCommand()))
			System.exit(0);
		else if(RUN.equals(e.getActionCommand())){
			_model.run(_tb.getSteps());
			setStatus("Simulator advanced!");
		}
		else if(RESET.equals(e.getActionCommand())){
			_model.reset();
			setStatus("Simulator has been reset!");
		}
		else if(GENERATE.equals(e.getActionCommand())){
			_reportArea.setText(_model.getMap().generateReport(_model.getTime()));
			setStatus("Reports have been generated!");
		}
		else if(CLEARREP.equals(e.getActionCommand())){
			_reportArea.setText("");
			setStatus("Reports area has been cleared!");
		}
	
	
	}

	public static String readFile(File file) {
		String s = "";
		try {
			s = new Scanner(file).useDelimiter("\\A").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return s;
	}
}
