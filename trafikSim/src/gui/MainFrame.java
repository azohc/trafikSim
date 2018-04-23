package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.Border;

import model.Event;
import model.RoadMap;
import model.TrafficSimulator;
import control.Controller;
import control.SimulatorError;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements TrafficSimulatorObserver, ActionListener{

	private final String LOAD = "load";
	private final String SAVE = "save";
	private final String CLEAR = "clear";
	private final String QUIT = "quit";
	
	
	//OUTER BORDER
	public static Border defaultBorder = BorderFactory.createLineBorder(Color.black, 2);
	
	private Controller _ctrl;
	private TrafficSimulator _model;
	
	// MENU AND TOOL BAR
	private JFileChooser fc;
	private Toolbar _tb;
	
	// TEXT AREAS
	private EventEditor _eventEditor;
	private ReportTextArea _reportArea;
	
	// TABLES
	private EventQueueTable _eventTable;
	private VehiclesTable _vehTable;
	private RoadsTable _rdTable;
	private JunctionsTable _juncTable;
	
	// PANELS
	private JPanel mainPanel;
	private JPanel innerPanel;
	private JPanel upperPanel;
	private JPanel lowerPanel;
	private JPanel lowLeftPanel;

	
	public MainFrame(Controller ctrl, TrafficSimulator model){
		super("Traffic Simulator");
		
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
		
		
		//EVENT EDITOR
		_eventEditor = new EventEditor(_model,_ctrl);
		upperPanel.add(_eventEditor);
		
		
		//EVENT QUEUE
		_eventTable = new EventQueueTable(_model);
		upperPanel.add(_eventTable);
		
		//REPORT AREA
		_reportArea = new ReportTextArea(_model, _ctrl);
		upperPanel.add(_reportArea);
		
		//MENU BAR
		setJMenuBar(createFileMenuBar());
		
		
		//TABLES
		_vehTable = new VehiclesTable(_model);
		lowLeftPanel.add(_vehTable);
		
		_rdTable = new RoadsTable(_model);
		lowLeftPanel.add(_rdTable);
		
		_juncTable = new JunctionsTable(_model);
		lowLeftPanel.add(_juncTable);
		
		//TOOLBAR
		_tb = new Toolbar(_model, _ctrl, _eventEditor.getTextArea());
		mainPanel.add(_tb, BorderLayout.PAGE_START);

		
		setSize(200,200);
		setVisible(true);
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		
	}
	
	@Override
	public void addSimError(int time, RoadMap map, List<Event> events,
			SimulatorError e) {
		// TODO Auto-generated method stub
		
	}
//show roadmap report in text field
//run button calls controller run(1)
	@Override
	public void addStep(int time, RoadMap map, List<Event> events) {
//		_reportArea.setText(map.generateReport(time)); 
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {

	}

	@Override
	public void addReset(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
		
	}
	
	
	public JMenuBar createFileMenuBar() {
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

		file.add(load);
		file.add(save);
		file.addSeparator();
		file.add(clear);
		file.addSeparator();
		file.add(quit);

		return menuBar;
	}

	public void actionPerformed(ActionEvent e) {
		if (CLEAR.equals(e.getActionCommand()))
			_eventEditor.getTextArea().setText("");
		else if (QUIT.equals(e.getActionCommand()))
			System.exit(0);
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

	
	private void loadFile() {
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String s = readFile(file);
			_eventEditor.getTextArea().setText(s);
		}
	}

}
