package model;

import ini.IniSection;

public class MostCrowdedJunction extends JunctionWithTimeSlice {

	public MostCrowdedJunction(String id) {
		 super(id);
		 }
	
	protected IncomingRoad createIncomingRoadQueue(Road road) {
		return new IncRoadWithTimeSlice(road);
	}
	
	protected void switchLights() {	//mega todo
											//ahora, Junction tiene un atributo _greenLightIndex : -1 si ninguno tiene luz verde 
		int indexMostVehicles = 0;
		int mostVehicles = - 1;
		
		if(_greenLightIndex != -1) {
			if(((IncRoadWithTimeSlice) _incRoads.get(_greenLightIndex)).getUsedTimeUnits() == ((IncRoadWithTimeSlice) _incRoads.get(_greenLightIndex)).getTimeSlice()){
				_incRoads.get(_greenLightIndex).setGreen(false); // set current green light to red
			
				if(_incRoads.size() > 1) {
					for(int i = 0; i < _incRoads.size(); i++) {  // find road with most vehicles
						if(mostVehicles < _incRoads.get(i)._queue.size() && i != _greenLightIndex) { 
							mostVehicles = _incRoads.get(i)._queue.size();
							indexMostVehicles = i;
						}	
					}
				}
				_incRoads.get(indexMostVehicles).setGreen(true); // set the road with most vehicles to green
				_greenLightIndex = indexMostVehicles;
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
			_greenLightIndex = indexMostVehicles;
			((IncRoadWithTimeSlice) _incRoads.get(indexMostVehicles)).setTimeSlice(Math.max(mostVehicles / 2, 1 ));
			((IncRoadWithTimeSlice) _incRoads.get(indexMostVehicles)).setUsedTimeUnits(0);	
			
		}
		
	}
	protected void fillReportDetails(IniSection is) {
		super.fillReportDetails(is);
		is.setValue("type", "mc");
	}

	
}
