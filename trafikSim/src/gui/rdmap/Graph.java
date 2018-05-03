package gui.rdmap;

import java.util.ArrayList;
import java.util.List;

import model.Junction;
import model.Road;

public class Graph {
	
	
	private List<Road> _roads;
	private List<Junction> _junctions;
	
	public Graph() {
		
		_roads = new ArrayList<>();
		_junctions = new ArrayList<>();
			
	}
	
	public void addRoad(Road r) {
		_roads.add(r);
	}
	
	public void addJunction(Junction j) {
		_junctions.add(j);
	}
	
	public List<Junction> getJunctions() {
		return _junctions;
	}
	
	public List<Road> getRoads() {
		return _roads;
	}
}
