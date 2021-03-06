package model;

import java.util.ArrayList;
import java.util.List;

import control.SimulatorError;

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
	
		if(map.getVehicle(_id) != null)
			throw new SimulatorError(dupeObj);
		
		List<Junction> _it = new ArrayList<Junction>();
		
		for(int i = 0; i < _itinerary.length ; i++) {
			
			if(map.getJunction(_itinerary[i]) == null)
				throw new SimulatorError("Failed to add new car: reference to inexistent junction " + _itinerary[i]);
			
			_it.add(map.getJunction(_itinerary[i]));
		}
		
		map.addVehicle(new Car(_id, _maxSpeed, _it, _resistance, _faultProb, _maxFaultDur, _seed));;
	
	}

	
	@Override
	public String toString(){
		return "New Car " + _id;
	}
}
