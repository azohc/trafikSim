package model;

public class NewCarEvent extends NewVehicleEvent {

	public NewCarEvent(Integer time, String id, Integer maxSpeed, String[] it) {
		super(time, id, maxSpeed, it);
	}


	public void execute(RoadMap map, int time){
				
	}

	
	public String toString(){
		return "New Car " + _id;
	}
}
