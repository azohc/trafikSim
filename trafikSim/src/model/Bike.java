package model;

import java.util.List;

import ini.IniSection;

public class Bike extends Vehicle {

	public Bike(String id, int maxSpeed, List<Junction> it) {
		super(id, maxSpeed, it);
	}

	
	@Override
	protected void fillReportDetails(IniSection i) 
	{
		i.setValue("type", "bike");
		super.fillReportDetails(i);
	}
	
	@Override
	public void makeFaulty(int ticks) {
		
		if(_currentSpeed > _maxSpeed/2)
			super.makeFaulty(ticks);
		
	}
}
