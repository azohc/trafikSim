package model;

import ini.IniSection;

public class DirtRoad extends Road {
	
	public DirtRoad(String id,  int maxSpeed, int length, Junction src, Junction dest) {
		super(id, length, maxSpeed, src, dest);
	}
	
	protected int reduceSpeedFactor(int faultyCounter) 
	{
		int reductionFactor = 1;
	
		reductionFactor += faultyCounter;
		
		return reductionFactor;	
	}
	
	protected int calculateBaseSpeed()
	{
		return _maxSpeed;
	}
	
	protected void fillReportDetails(IniSection i) 
	{
		i.setValue("type", "dirt");
		super.fillReportDetails(i);
	}

}
