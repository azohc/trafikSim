package gui.rdmap;

import java.util.ArrayList;
import java.util.List;

import model.Vehicle;

public class Edge {
	private String _id;
	private Node _source;
	private Node _target;
	private int _length;
	private List<Dot> _dots;
	
	public Edge(String id, Node source, Node target, int length, List<Vehicle> vehicles) {
		_source = source;
		_target = target;
		_id = id;
		_length = length;
		_dots = new ArrayList<>();
		
		for(Vehicle v: vehicles){
			_dots.add(new Dot(v.getId(), v.get_location()));
		}
	}
	
	public void addDot(Dot e) {
		_dots.add(e);
	}
	public String getId() {
		return _id;
	}
	
	public Node getSource() {
		return _source;
	}
	
	public Node getTarget() {
		return _target;
	}

	public int getLength() {
		return _length;
	}
	
	public List<Dot> getDots() {
		return _dots;
	}
}
