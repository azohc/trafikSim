package model;

import ini.IniSection;

public class DirtRoad extends Road {
	
	public DirtRoad(String id,  int maxSpeed, int length, Junction src, Junction dest) {
		super(id, length, maxSpeed, src, dest);
	}
	
	@Override
	protected int reduceSpeedFactor(int faultyCounter) 
	{
		int reductionFactor = 1;
	
		reductionFactor += faultyCounter;
		
		return reductionFactor;	
	}
	
	@Override
	protected int calculateBaseSpeed()
	{
		return _maxSpeed;
	}
	
	@Override
	protected void fillReportDetails(IniSection i) 
	{
		i.setValue("type", "dirt");
		super.fillReportDetails(i);
	}

}
