package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;

import model.Event;
import model.RoadMap;
import model.TrafficSimulator;
import control.Controller;
import control.SimulatorError;

public class MainFrame extends JFrame implements TrafficSimulatorObserver{

	
	public static Border defaultBorder = BorderFactory.createLineBorder(Color.black, 2);
	
	// SUPERIOR PANEL
	static private final String[] columnIdEventos = { "#", "Time", "Type" };
	
	
	private Controller _ctrl;
	private TrafficSimulator _model;
	
	private JTextArea _eventEditor;
	private JTextArea _reportArea;
	
	
	
	public MainFrame(Controller ctrl, TrafficSimulator model){
		super("Traffic Simulator");
		
		_ctrl = ctrl;
		_model = model;
		
		initGUI();
		model.addObserver(this);
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);
		
		_eventEditor = new JTextArea(5,10);
		mainPanel.add(_eventEditor);
		
		
		//MENU BAR
		this.setJMenuBar(createMenuBar());
		
		//TOOLBAR
		Toolbar tb = new Toolbar(_Mod contrl);
		mainPanel.add(tb.createJToolBar(), BorderLayout.PAGE_START);
		
		//TABLES
		VehiclesTable vt = new VehiclesTable(_model);
		mainPanel.add(vt);
		
		
		//BUTTONS
		JButton step = new JButton("Step");
		mainPanel.add(step);
		
		step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				_ctrl.run(1);
			}
		});
		
		JButton load = new JButton("Load");
		mainPanel.add(load);
		
		load.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				_ctrl.loadEvents(new ByteArrayInputStream(_eventEditor.getText().getBytes())); //Check
			}
		}
		);

		setSize(200,200);
		setVisible(true);
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
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
	
	
}
