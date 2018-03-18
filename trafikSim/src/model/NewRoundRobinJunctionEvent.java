package model;

import control.SimulatorError;

public class NewRoundRobinJunctionEvent extends NewJunctionEvent {

	protected int _maxTs;
	protected int _minTs;
	
	public NewRoundRobinJunctionEvent(int time, String id, String type, int maxTs, int minTs) {
		super(time, id);
		_maxTs = maxTs;
		_minTs = minTs;
	}
	
	@Override
	public void execute(RoadMap map, Integer time) {
		if(map.getJunction(_id) != null)
			throw new SimulatorError(dupeObj);
		map.addJunction(new RoundRobinJunction(_id, _minTs, _maxTs));
	}
	
public String toString(){
		
		return "New Round Robin Junction " + _id;
	}
}
