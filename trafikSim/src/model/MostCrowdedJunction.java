package model;

import ini.IniSection;

public class MostCrowdedJunction extends JunctionWithTimeSlice {

	public MostCrowdedJunction(String id) {
		 super(id);
		 }
	
	protected IncomingRoad createIncomingRoadQueue(Road road) {
		return new IncRoadWithTimeSlice(road);
	}
	protected void switchLights(int pos) {
		
		int indexMostVehicles = 0;
		int mostVehicles = 0;
		
		if(pos != -1 && pos != _incRoads.size() - 1) {
			if(((IncRoadWithTimeSlice) _incRoads.get(pos)).getUsedTimeUnits() == ((IncRoadWithTimeSlice) _incRoads.get(pos)).getTimeSlice()){
				_incRoads.get(pos).setGreen(false); // set current green light to red
			
				for(int i = 0; i < _incRoads.size(); i++)  // find road with most vehicles
					if(mostVehicles < _incRoads.get(i)._queue.size()) { 
						mostVehicles = _incRoads.get(i)._queue.size();
						indexMostVehicles = i;
					}
				_incRoads.get(indexMostVehicles).setGreen(true); // set the road with most vehicles to green
				((IncRoadWithTimeSlice) _incRoads.get(indexMostVehicles)).setTimeSlice(Math.max(mostVehicles / 2, 1 ));
				((IncRoadWithTimeSlice) _incRoads.get(indexMostVehicles)).setUsedTimeUnits(0);	
			
			}
		}
		else {
			
			for(int i = 0; i < _incRoads.size(); i++)  // find road with most vehicles
				if(mostVehicles < _incRoads.get(i)._queue.size()) { 
					mostVehicles = _incRoads.get(i)._queue.size();
					indexMostVehicles = i;
				}
			_incRoads.get(indexMostVehicles).setGreen(true); // set the road with most vehicles to green
			((IncRoadWithTimeSlice) _incRoads.get(indexMostVehicles)).setTimeSlice(Math.max(mostVehicles / 2, 1 ));
			((IncRoadWithTimeSlice) _incRoads.get(indexMostVehicles)).setUsedTimeUnits(0);	
			
		}
		
	}
	protected void fillReportDetails(IniSection is) {
		is.setValue("type", "mc");
		super.fillReportDetails(is);
	}

	
}
