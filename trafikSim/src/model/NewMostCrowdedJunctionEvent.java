package model;

public class NewMostCrowdedJunctionEvent extends NewJunctionEvent {

	protected String _type;
	
	public NewMostCrowdedJunctionEvent(int time, String id, String type) {
		super(time, id);
		_type = type;
	}
	
	public void execute(RoadMap map, Integer time) {
		if(_type == "mc")
			map.addJunction(new MostCrowdedJunction(_id));
	}
	
	public String toString() {
		
		return "New Most Crowded Junction " + _id;
	}
}
