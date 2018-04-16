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
	
	public void switchLights() {
		
		if(_greenLightIndex == -1) { 					// no green lights
			_greenLightIndex++;
			_incRoads.get(_greenLightIndex).setGreen(true);	//set first light to green
		}
		
		else if(((IncRoadWithTimeSlice) _incRoads.get(_greenLightIndex)).getUsedTimeUnits() == ((IncRoadWithTimeSlice) _incRoads.get(_greenLightIndex)).getTimeSlice()) {
						
			if(((IncRoadWithTimeSlice) _incRoads.get(_greenLightIndex)).isFullyUsed())
				((IncRoadWithTimeSlice) _incRoads.get(_greenLightIndex))._timeSlice = Math.min(((IncRoadWithTimeSlice) _incRoads.get(_greenLightIndex)).getTimeSlice() + 1, _maxTimeSlice);
			
			else if(!((IncRoadWithTimeSlice) _incRoads.get(_greenLightIndex))._used)
				((IncRoadWithTimeSlice) _incRoads.get(_greenLightIndex))._timeSlice = Math.max(((IncRoadWithTimeSlice) _incRoads.get(_greenLightIndex)).getTimeSlice() - 1, _minTimeSlice);
			
			((IncRoadWithTimeSlice) _incRoads.get(_greenLightIndex))._usedTimeUnits = 0;
			
			_incRoads.get(_greenLightIndex++).setGreen(false);			//set current light to red
			
			if(_greenLightIndex == _incRoads.size())					//if next light is == size (out of range)
				_greenLightIndex = 0;									//set index to first element
			
			_incRoads.get(_greenLightIndex).setGreen(true);			//set next light to green
						
		}
	}
	
	
	
	public IncomingRoad createIncomingRoadQueue(Road road) {
		IncRoadWithTimeSlice irts = new IncRoadWithTimeSlice(road);	
		irts.setTimeSlice(_maxTimeSlice);
		return irts;
	}
	
	
	protected void fillReportDetails(IniSection is) {
		super.fillReportDetails(is);
		is.setValue("type", "rr");
	}

}
