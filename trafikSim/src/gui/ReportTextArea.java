package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import control.Controller;
import control.SimulatorError;
import model.Event;
import model.RoadMap;
import model.TrafficSimulator;

@SuppressWarnings("serial")
public class ReportTextArea extends JPanel implements TrafficSimulatorObserver {
	
	private JTextArea _textArea;
	private TrafficSimulator _model;
	private Controller _ctrl;
	
	public static Border defaultBorder = BorderFactory.createLineBorder(Color.black, 2);

	public ReportTextArea(TrafficSimulator model, Controller ctrl) {
		_model = model;
		_ctrl = ctrl;
		initGUI();
		_model.addObserver(this);
	}
	
	private void initGUI() {
		this.setBorder(new TitledBorder(defaultBorder, "Reports"));
		this.setLayout(new BorderLayout());
		_textArea = new JTextArea(40, 30);
		_textArea.setEditable(false);
		_textArea.setLineWrap(true);
		_textArea.setWrapStyleWord(true);
		JScrollPane area = new JScrollPane(_textArea);
		this.add(area, BorderLayout.CENTER);
	}

	@Override
	public void addSimError(int time, RoadMap map, List<Event> events, SimulatorError e) {
		// TODO Auto-generated method stub
		
	}

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

	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
		
	}

	public void setText(String generateReport) {
		_textArea.setText(generateReport);
	}
	
	public JTextArea getTxtArea(){
		
		return _textArea;
	}

}
