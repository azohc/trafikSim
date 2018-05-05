package model;

import java.util.ArrayList;
import java.util.List;

import control.SimulatorError;

public class NewBikeEvent extends NewVehicleEvent {

	public NewBikeEvent(Integer time, String id, String[] it, Integer maxSpeed, String type) {
		super(time, id, maxSpeed, it);
	}
	
	@Override
	public void execute(RoadMap map, Integer time) {

		if(map.getVehicle(_id) != null)
			throw new SimulatorError(dupeObj);
		
		List<Junction> _it = new ArrayList<Junction>();
		
		for(int i = 0; i < _itinerary.length ; i++) {
			
			if(map.getJunction(_itinerary[i]) == null)
				throw new SimulatorError("Failed to add new bike: reference to inexistent junction " + _itinerary[i]);
			
			_it.add(map.getJunction(_itinerary[i]));
		}
		
		map.addVehicle(new Bike(_id, _maxSpeed, _it));
	
	}

	@Override
	public String toString() {
		return "New Bike " + _id;
	}
}
