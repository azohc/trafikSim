package model;

import control.SimulatorError;

public class NewJunctionEvent extends Event {

	protected String _id;
	
	public NewJunctionEvent(int time, String id){
		super(time);
		_id = id;
	}
	
	@Override
	public void execute(RoadMap map, Integer time) {
		if(map.getJunction(_id) != null)
			throw new SimulatorError(dupeObj);
		map.addJunction(new Junction(_id));
		
	}

	@Override
	public String toString(){
		
		return "New Junction " + _id;
	}
}
