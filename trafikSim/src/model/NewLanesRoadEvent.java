package model;

import control.SimulatorError;

public class NewLanesRoadEvent extends NewRoadEvent {
	
	protected int _lanes;
	
	public NewLanesRoadEvent(int time, String id, String start, String end, int maxSpeed, int length, String type, int lanes) {
		super(time, id, length, maxSpeed, start, end);
		_lanes = lanes;
	}

	@Override
	public void execute(RoadMap map, Integer time) {
		if(map.getRoad(_id) != null)
			throw new SimulatorError(dupeObj);
		
		if(map.getJunction(_start) == null || map.getJunction(_end) == null)
			throw new SimulatorError("Reference to inexistent junction");
		
		map.addRoad(new LanesRoad(_id, _length, _maxSpeed, map.getJunction(_start),map.getJunction(_end), _lanes));
	}
	
	public String toString() {
		return "New Lanes Road " + _id;
	}
}
