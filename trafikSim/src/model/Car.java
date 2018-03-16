package model;

import java.util.List;
import java.util.Random;


public class Car extends Vehicle {
	
	protected int _resistance;
	protected int _kmSinceFaulty;
	protected double _faultProbability;
	protected int _maxFaultDuration;
	protected long _seed = System.currentTimeMillis();
	protected Random _rnd;

	public Car(String id, int maxSpeed, int resistance, double faultProbability, int maxFaultDuration, long seed, List<Junction> it) {
		super(id, maxSpeed, it);
		_resistance = resistance;
		_faultProbability = faultProbability;
		_maxFaultDuration = maxFaultDuration;
		_seed = seed;
	}

	void advance(int time){
		
		if(getFaultyTime() > 0)
			makeFaulty(getFaultyTime() - 1);
		
		else if(_kmSinceFaulty >= _resistance){
			
			_kmSinceFaulty = _kmSinceFaulty - _resistance;
			
			_rnd = new Random(_seed);
			if(_rnd.nextFloat() < _faultProbability)
				makeFaulty(_rnd.nextInt(_maxFaultDuration) + 1);
		}
		super.advance();
		
		_kmSinceFaulty += getKilometrage();
	}
	
	
}
