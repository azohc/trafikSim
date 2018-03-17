package model;

import java.util.ArrayList;
import java.util.List;

public class NewCarEvent extends NewVehicleEvent {
	
	protected int _resistance;
	protected double _faultProb;
	protected int _maxFaultDur;
	protected long _seed;
	
	public NewCarEvent(Integer time, String id, String[] it, Integer maxSpeed, String type, int resistance,
			double faultProbability, int maxFaultDuration, long seed) {
		
		super(time, id, maxSpeed, it);
		_resistance = resistance;
		_faultProb = faultProbability;
		_maxFaultDur = maxFaultDuration;
		_seed = seed;	
	}

	@Override
	public void execute(RoadMap map, Integer time) {
	
		List<Junction> _it = new ArrayList<Junction>();
		
		for(int i = 0; i < _itinerary.length ; i++)
			_it.add(map.getJunction(_itinerary[i]));
		
		map.addVehicle(new Car(_id, _maxSpeed, _it, _resistance, _faultProb, _maxFaultDur, _seed));;
	
	}

	
	public String toString(){
		return "New Car " + _id;
	}
}
