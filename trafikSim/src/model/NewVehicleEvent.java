package model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {
	
	protected String _id;
	protected Integer _maxSpeed;
	protected String[] _itinerary;
	
	public NewVehicleEvent(Integer time, String id, Integer maxSpeed, String[] it) {
		super(time);
		_id = id;
		_maxSpeed = maxSpeed;
		_itinerary = it;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(RoadMap map, Integer time) {
		
		List<Junction> _it = new ArrayList<Junction>();
		
		for(int i = 0; i < _itinerary.length ; i++)
			_it.add(new Junction(_itinerary[i]));
				
		map.addVehicle(new Vehicle(_id, _maxSpeed, _it));

	}

public String toString(){
		
		return "New Vehicle " + _id;
	}


}
