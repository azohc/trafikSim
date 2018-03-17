package model;

import ini.IniSection;

public class LanesRoad extends Road {

	protected int _lanes;
	
	public LanesRoad(String id, int length, int maxSpeed, Junction start, Junction end, int lanes) {
		super(id, length, maxSpeed, start, end);
		_lanes = lanes;
	}
	
	protected int calculateBaseSpeed()
	{
		return Math.min(_maxSpeed, ((_lanes*_maxSpeed)/Math.max(_vehList.size(), 1)) + 1);
	}
	
	protected int reduceSpeedFactor(int i) {
		int faultyCounter = 0;
		int reductionFactor = 1;

		for(int j =  i - 1; j >= 0; j--)
			if(_vehList.get(i).get_location() < _vehList.get(j).get_location() &&
				_vehList.get(j).getFaultyTime() > 0)
					faultyCounter++;
	
	
		if(_lanes <= faultyCounter) reductionFactor = 2;
		
		return reductionFactor;	
	}


	protected void fillReportDetails(IniSection i) {
	
		i.setValue("type", "lanes");
		super.fillReportDetails(i);
	}
}
