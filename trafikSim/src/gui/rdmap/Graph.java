package gui.rdmap;

import java.util.ArrayList;
import java.util.List;

import model.Junction;
import model.Road;
import model.RoadMap;

public class Graph {
	
	
	private List<Edge> _edges;
	private List<Node> _nodes;
	
	public Graph(RoadMap map) {
		
		_edges = new ArrayList<>();
		
		for(Road r: map.getRoads()){
			_edges.add(new Edge(r.getId(),new Node(r.getSource().getId()),new Node(r.getDestination().getId()),r.getLength(), r.getVehicles()));
		}
		
		_nodes = new ArrayList<>();
	
		for(Junction j: map.getJunctions()){
			_nodes.add(new Node(j.getId()));
		}
		
	}
	
	public void addEdge(Edge e) {
		_edges.add(e);
	}
	
	public void addNode(Node n) {
		_nodes.add(n);
	}
	
	public List<Edge> getEdges() {
		return _edges;
	}
	
	public List<Node> getNodes() {
		return _nodes;
	}
}
