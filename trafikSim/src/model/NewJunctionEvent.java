package model;

public class NewJunctionEvent extends Event {

	protected String _id;
	
	public NewJunctionEvent(int time, String id){
		super(time);
		_id = id;
	}
	
	@Override
	public void execute(RoadMap map, Integer time) {
		map.addJunction(new Junction(_id));
		
	}

	public String toString(){
		
		return "New Junction " + _id;
	}
}
