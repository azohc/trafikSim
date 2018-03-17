package model;

import ini.IniSection;

public class DirtRoad extends Road {
	
	public DirtRoad(String id,  int maxSpeed, int length, Junction src, Junction dest) {
		super(id, length, maxSpeed, src, dest);
	}
	
	protected int reduceSpeedFactor(int i) 
	{
		int faultyCounter = 0;
		int reductionFactor = 1;

		for(int j =  i - 1; j >= 0; j--)
			if(_vehList.get(i).get_location() < _vehList.get(j).get_location() &&
				_vehList.get(j).getFaultyTime() > 0)
					faultyCounter++;
	
	
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
