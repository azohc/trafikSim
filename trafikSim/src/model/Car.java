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

	public Car(String id, int maxSpeed, List<Junction> it,  int resistance, double faultProbability, int maxFaultDuration, long seed) {
		super(id, maxSpeed, it);
		_resistance = resistance;
		_faultProbability = faultProbability;
		_maxFaultDuration = maxFaultDuration;
		_seed = seed;
		_rnd = new Random(_seed);
	}

	void advance(int time){
				
		if(_faulty == 0 && _kmSinceFaulty >= _resistance)
		
		else if(_kmSinceFaulty >= _resistance){
			_kmSinceFaulty = _kmSinceFaulty - _resistance;
			
			
			if(_rnd.nextDouble() < _faultProbability)
				makeFaulty(_rnd.nextInt(_maxFaultDuration) + 1);
		}
		
		int kmAdvanced = getKilometrage();
		super.advance();
		kmAdvanced = getKilometrage() - kmAdvanced;
		
		_kmSinceFaulty += kmAdvanced;
	}
	
	
}
