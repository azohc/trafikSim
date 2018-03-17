package model;

public class NewBikeEvent extends NewVehicleEvent {

	
	
	public NewBikeEvent(Integer time, String id, String[] it, Integer maxSpeed, String type) {
		super(time, id, maxSpeed, it);
	}


	public void execute(RoadMap map, int time){
	
	}

}
