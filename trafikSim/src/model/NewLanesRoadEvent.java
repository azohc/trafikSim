package model;

public class NewLanesRoadEvent extends NewRoadEvent {
	
	protected String _type;
	protected int _lanes;
	
	public NewLanesRoadEvent(int time, String id, String start, String end, int length, int maxSpeed, String type, int lanes) {
		super(time, id, length, maxSpeed, start, end);
		_type = type;
		_lanes = lanes;
	}

	public void execute(RoadMap map, int time){
		if(_type == "lanes")
			map.addRoad(new LanesRoad(_id, _length, _maxSpeed, map.getJunction(_start),map.getJunction(_end), _lanes));
	}
	
	public String toString() {
		return "New Lanes Road " + _id;
	}
}
