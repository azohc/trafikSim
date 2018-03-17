package model;

import ini.IniSection;

public class RoundRobinJunction extends JunctionWithTimeSlice {

	protected int _maxTimeSlice;
	protected int _minTimeSlice; 
	
	public RoundRobinJunction(String id, int minTimeSlice, int maxTimeSlice) {
		super(id);
		_maxTimeSlice = maxTimeSlice;
		_minTimeSlice = minTimeSlice;
		
	}
	
	public void switchLights(int pos) {
		
		if(pos == -1) 					// no green lights
			_incRoads.get(0).setGreen(true);	//set first light to green
		else if(((IncRoadWithTimeSlice) _incRoads.get(pos)).getUsedTimeUnits() == ((IncRoadWithTimeSlice) _incRoads.get(pos)).getTimeSlice()) {
						
			if(((IncRoadWithTimeSlice) _incRoads.get(pos)).isFullyUsed())
				((IncRoadWithTimeSlice) _incRoads.get(pos))._timeSlice = Math.min(((IncRoadWithTimeSlice) _incRoads.get(pos)).getTimeSlice() + 1, _maxTimeSlice);
			
			else if(((IncRoadWithTimeSlice) _incRoads.get(pos))._used)
				((IncRoadWithTimeSlice) _incRoads.get(pos))._timeSlice = Math.max(((IncRoadWithTimeSlice) _incRoads.get(pos)).getTimeSlice() - 1, _minTimeSlice);
			
			((IncRoadWithTimeSlice) _incRoads.get(pos))._usedTimeUnits = 0;
			
			_incRoads.get(pos).setGreen(false);			//set current light to red
			if(pos != _incRoads.size() - 1) 		//for lights 0 to size - 2
				_incRoads.get(pos + 1).setGreen(true);			//set next light to green
			
			else 		//light (size - 1) == green
				_incRoads.get(0).setGreen(true);					
		}
	}
	
	
	
	public IncomingRoad createIncomingRoadQueue(Road road) {
		IncRoadWithTimeSlice irts = new IncRoadWithTimeSlice(road);	
		irts.setTimeSlice(_maxTimeSlice);
		return irts;
	}
	
	
	protected void fillReportDetails(IniSection is) {
		is.setValue("type", "rr");
		super.fillReportDetails(is);
	}

}
