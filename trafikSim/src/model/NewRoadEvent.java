package model;

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
		map.addRoad(new Road(_id, _length, _maxSpeed, map.getJunction(_start), map.getJunction(_end)));

	}

	public String toString(){
		
		return "New Road " + _id;
	}
}