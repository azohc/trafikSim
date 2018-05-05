package model;

import control.SimulatorError;

public class NewRoadEvent extends Event {

	protected String _id;
	protected int _length;
	protected int _maxSpeed;
	protected String _start;
	protected String _end;
	
	
	public NewRoadEvent(int time, String id, int length, int maxSpeed, String start, String end) {
		super(time);
		_id = id;
		_length = length;
		_maxSpeed = maxSpeed;
		_start = start;
		_end = end;
		
	}

	@Override
	public void execute(RoadMap map, Integer time) {
		if(map.getRoad(_id) != null)
			throw new SimulatorError(dupeObj);
		
		if(map.getJunction(_start) == null)
			throw new SimulatorError("Failed to add new road: reference to inexistent junction " + _start);
		
		if(map.getJunction(_end) == null)
			throw new SimulatorError("Failed to add new road: reference to inexistent junction " + _end);
			
		map.addRoad(new Road(_id, _length, _maxSpeed, map.getJunction(_start), map.getJunction(_end)));
	}

	@Override
	public String toString(){
		
		return "New Road " + _id;
	}
}
