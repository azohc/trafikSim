package model;

import java.util.List;
import java.util.Random;

import ini.IniSection;


public class Car extends Vehicle {
	
	protected int _resistance;
	protected double _faultProbability;
	protected int _maxFaultDuration;
	protected Random _rnd;

	public Car(String id, int maxSpeed, List<Junction> it,  int resistance, double faultProbability, int maxFaultDuration, long seed) {
		super(id, maxSpeed, it);
		_resistance = resistance;
		_faultProbability = faultProbability;
		_maxFaultDuration = maxFaultDuration;
		_rnd = new Random(seed);
	}

	@Override
	void advance(){
				
		if(_faulty == 0) {
			if (_kilometrage - _lastFaultyKm >= _resistance)
				if(_rnd.nextDouble() < _faultProbability) 
					makeFaulty(_rnd.nextInt(_maxFaultDuration) + 1);
		} 
		else {
			_lastFaultyKm = _kilometrage;
		}
				
			
		super.advance();
	}
	
	@Override
	protected void fillReportDetails(IniSection i) 
	{
		i.setValue("type", "car");
		super.fillReportDetails(i);
	}
	
	
}
