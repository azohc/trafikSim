package model;

public class NewMostCrowdedJunctionEvent extends NewJunctionEvent {

	
	public NewMostCrowdedJunctionEvent(int time, String id, String type) {
		super(time, id);
	}
	
	@Override
	public void execute(RoadMap map, Integer time) {

			map.addJunction(new MostCrowdedJunction(_id));
	}
	
	public String toString() {
		
		return "New Most Crowded Junction " + _id;
	}
}
