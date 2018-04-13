package gui;

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
import control.Controller;
import control.SimulatorError;

public class MainFrame extends JFrame implements TrafficSimulatorObserver{

	
	public static Border defaultBorder = BorderFactory.createLineBorder(Color.black, 2);
	
	// SUPERIOR PANEL
	static private final String[] columnIdEventos = { "#", "Tiempo", "Tipo" };
	
	// BUTTON
	private JButton _runBt;
	
	private Controller _ctrl;

	private JTextArea _eventEditor;

	
	
	
	// check. pedir uml gui a samir
	public MainFrame(String str){
		super(str);
		
		setLayout( new FlowLayout());
		
		_runBt = new JButton("Run");
		add(_runBt);
		
		
		setSize(200,200);
		setVisible(true);
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);
		
		_eventEditor = new JTextArea(5,10);
		mainPanel.add(_eventEditor);
		
		JButton step = new JButton("Step");
		mainPanel.add(step);
		step.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				_ctrl.run(1);
				
			}
			
		});
		
		JButton load = new JButton("Load");
		mainPanel.add(load);
		step.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				_ctrl.loadEvents(new ByteArrayInputStream(_eventEditor.getText())); // falta meter algo
				
			}
			
		});
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
		// TODO Auto-generated method stub
		reportArea_.setText();
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addReset(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
		
	}
	
	vehicleTable(){
		always keep latest version of roadmap here
		
	}

	
}
