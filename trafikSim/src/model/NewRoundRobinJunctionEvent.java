package model;

public class NewRoundRobinJunctionEvent extends NewJunctionEvent {

	protected String _type;
	protected int _maxTs;
	protected int _minTs;
	
	public NewRoundRobinJunctionEvent(int time, String id, String type, int maxTs, int minTs) {
		super(time, id);
		_type = type;
		_maxTs = maxTs;
		_minTs = minTs;
	}
	
	public void execute(RoadMap map, Integer time) {
		if(_type == "rr")
			map.addJunction(new RoundRobinJunction(_id, _minTs, _maxTs));
	}
	
public String toString(){
		
		return "New Round Robin Junction " + _id;
	}
}
