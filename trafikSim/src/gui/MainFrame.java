package gui;

import java.awt.Color;
import java.awt.FlowLayout;
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
		
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addReset(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
		
	}

	
}
