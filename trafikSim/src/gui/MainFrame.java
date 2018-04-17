package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;

import model.Event;
import model.RoadMap;
import model.TrafficSimulator;
import control.Controller;
import control.SimulatorError;

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
	private JTextArea _eventEditor;
	private JTextArea _reportArea;
	private TextEditor _te;
	
	// PANELS
	private JPanel mainPanel;
	private JPanel innerPanel;
	private JPanel upperPanel;
	private JPanel lowerPanel;

	static private final String[] columnIdEventos = { "#", "Time", "Type" };
		
	
	
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
		this.setContentPane(mainPanel);
		
		//TOOLBAR
		_tb = new Toolbar(_model, _ctrl);
		mainPanel.add(_tb.createJToolBar(), BorderLayout.PAGE_START);
		
		//PANELS
		innerPanel = new JPanel();
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));	//check
		mainPanel.add(innerPanel, BorderLayout.CENTER);
		
		upperPanel = new JPanel();
		upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.X_AXIS));
		
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.X_AXIS));
		
		
		innerPanel.add(upperPanel);
		innerPanel.add(lowerPanel);
		
		upperPanel.add(new TextEditor(_model,_ctrl));
		
		
		
	
		
		//MENU BAR
		this.setJMenuBar(createFileMenuBar());
		
		
		//TABLES
		VehiclesTable vt = new VehiclesTable(_model);
		lowerPanel.add(vt);
		
		
		
		
		

		setSize(200,200);
		setVisible(true);
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		
//		//BUTTONS
//		JButton step = new JButton("Step");
//		mainPanel.add(step);
//		
//		step.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				_ctrl.run(1);
//			}
//		});
//		
//		JButton load = new JButton("Load");
//		mainPanel.add(load);
//		
//		load.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent arg0) {
//				_ctrl.loadEvents(new ByteArrayInputStream(_eventEditor.getText().getBytes())); //Check
//			}
//		}
//		);
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
		_reportArea.setText(map.generateReport(time)); 
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
//		if (LOAD.equals(e.getActionCommand())) mega todo
//			loadFile();
//		else if (SAVE.equals(e.getActionCommand()))
//			saveFile();
//		else if (CLEAR.equals(e.getActionCommand()))
//			textArea.setText("");
//		else if (QUIT.equals(e.getActionCommand()))
//			System.exit(0);
	}

}
