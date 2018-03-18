package model;

import java.util.ArrayList;
import java.util.List;

import control.SimulatorError;

public class NewVehicleEvent extends Event {
	
	protected String _id;
	protected Integer _maxSpeed;
	protected String[] _itinerary;
	
	public NewVehicleEvent(Integer time, String id, Integer maxSpeed, String[] it) {
		super(time);
		_id = id;
		_maxSpeed = maxSpeed;
		_itinerary = it;
	}

	@Override
	public void execute(RoadMap map, Integer time) {
		
		if(map.getVehicle(_id) != null)
			throw new SimulatorError(dupeObj);
		
		List<Junction> _it = new ArrayList<Junction>();
		
		for(int i = 0; i < _itinerary.length ; i++) {
			
			if(map.getJunction(_itinerary[i]) == null)
				throw new SimulatorError("Failed to add new vehcile: reference to inexistent junction " + _itinerary[i]);
			
			_it.add(map.getJunction(_itinerary[i]));
		}
				
		map.addVehicle(new Vehicle(_id, _maxSpeed, _it));

	}

	public String toString(){
		
		return "New Vehicle " + _id;
	}


}
