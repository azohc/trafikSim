package model;

import ini.IniSection;

public class LanesRoad extends Road {

	protected int _lanes;
	
	public LanesRoad(String id, int length, int maxSpeed, Junction start, Junction end, int lanes) {
		super(id, length, maxSpeed, start, end);
		_lanes = lanes;
	}
	
	@Override
	protected int calculateBaseSpeed()
	{
		return Math.min(_maxSpeed, ((_lanes*_maxSpeed)/Math.max(_vehList.size(), 1)) + 1);
	}
	
	@Override
	protected int reduceSpeedFactor(int faultyCounter) {
		int reductionFactor = 1;
		
		if(_lanes <= faultyCounter) reductionFactor = 2;
		
		return reductionFactor;	
	}


	@Override
	protected void fillReportDetails(IniSection i) {
	
		i.setValue("type", "lanes");
		super.fillReportDetails(i);
	}
}
