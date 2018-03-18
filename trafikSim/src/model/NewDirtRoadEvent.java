package model;

import control.SimulatorError;

public class NewDirtRoadEvent extends NewRoadEvent {
	
	
	public NewDirtRoadEvent(int time, String id, String src, String dest, int maxSpeed, int length, String type) {
		super(time, id, length, maxSpeed, src, dest);

	}
	
	@Override
	public void execute(RoadMap map, Integer time) {
		if(map.getRoad(_id) != null)
			throw new SimulatorError(dupeObj);
		
		if(map.getJunction(_start) == null || map.getJunction(_end) == null)
			throw new SimulatorError("Reference to inexistent junction");
		
		map.addRoad(new DirtRoad(_id, _maxSpeed, _length, map.getJunction(_start), map.getJunction(_end)));
	}
	
	public String toString() {
		
		return "New Dirt Road " + _id;
	}
}
