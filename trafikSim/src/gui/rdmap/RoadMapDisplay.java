package gui.rdmap;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;

import control.SimulatorError;
import gui.TrafficSimulatorObserver;
import model.Event;
import model.Junction;
import model.Road;
import model.RoadMap;
import model.TrafficSimulator;

@SuppressWarnings("serial")
public class RoadMapDisplay extends JPanel implements TrafficSimulatorObserver{

	private GraphComponent _graphComp;
	private Graph _g;
    private RoadMap _map;
    
	public RoadMapDisplay(TrafficSimulator model) {
		_map = null;
		initGUI();
		model.addObserver(this);
	}

	private void initGUI() {
		_graphComp = new GraphComponent();
		
		add(_graphComp, BorderLayout.CENTER);
				
		_g = new Graph();
		
		setVisible(true);
	}
		

	@Override
	public void addSimError(int time, RoadMap map, List<Event> events, SimulatorError e) {
		_map = map;		
	}

	@Override
	public void addStep(int time, RoadMap map, List<Event> events) {
		_map = map;
		createGraph();
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {
		_map = map;
		createGraph();	
	}

	@Override
	public void addReset(int time, RoadMap map, List<Event> events) {
		_map = map;
		createGraph();
	}

	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
		_map = map;
		createGraph();
	}
	
	void createGraph() {
		Graph g = new Graph();
		
		for(Road r : _map.getRoads())
			g.addRoad(r);
		
		for(Junction j : _map.getJunctions())
			g.addJunction(j);
		
		_graphComp.setGraph(g);
	}

}
