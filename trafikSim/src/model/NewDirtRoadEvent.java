package model;

public class NewDirtRoadEvent extends NewRoadEvent {
	
	protected String _type;
	
	public NewDirtRoadEvent(int time, String id, String src, String dest, int maxSpeed, int length, String type) {
		super(time, id, length, maxSpeed, src, dest);
		_type = type;
	}
	
	public void execute(RoadMap map, int time) {
		if(_type == "dirt")
			map.addRoad(new DirtRoad(_id, _length, _maxSpeed, map.getJunction(_start), map.getJunction(_end)));
	}
	
	public String toString() {
		
		return "New Dirt Road " + _id;
	}
}
