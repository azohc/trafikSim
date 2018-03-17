package model;

public class NewBikeEvent extends NewVehicleEvent {

	protected String _type;
	
	public NewBikeEvent(Integer time, String id, String[] it, Integer maxSpeed, String type) {
		super(time, id, maxSpeed, it);
		_type = type;
	}


	public void execute(RoadMap map, int time){
	
		if(_type == "bike")
			super.execute(map, time);
	}

	public String toString() {
		return "New Bike " + _id;
	}
}
