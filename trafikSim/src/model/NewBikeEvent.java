package model;

import java.util.ArrayList;
import java.util.List;

public class NewBikeEvent extends NewVehicleEvent {

	public NewBikeEvent(Integer time, String id, String[] it, Integer maxSpeed, String type) {
		super(time, id, maxSpeed, it);
	}
	
	@Override
	public void execute(RoadMap map, Integer time) {

		List<Junction> _it = new ArrayList<Junction>();
		
		for(int i = 0; i < _itinerary.length ; i++)
			_it.add(map.getJunction(_itinerary[i]));
		
		map.addVehicle(new Bike(_id, _maxSpeed, _it));
	
	}

	public String toString() {
		return "New Bike " + _id;
	}
}
