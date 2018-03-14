package model;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import ini.IniSection;

public class Road extends SimulatedObject {

	private int _length;
	private int _maxSpeed;
	private List<Vehicle> _vehList;	// sort by veh location, descending order: in case of == location, FIFO
	private Junction _source;
	private Junction _destination;
	
	public Road(String id, int length, int maxSpeed, Junction start, Junction end) { //
		super(id);
		_length = length;
		_maxSpeed = maxSpeed;
		_source = start;
		_destination = end;
		
		_vehList = new ArrayList<Vehicle>(); // always sorted array list by location
		
		//change to sorted array list at the end
	}

	protected void fillReportDetails(IniSection i) {
	
		if(_vehList.isEmpty())
			i.setValue("vehicles", null); 
		else
			i.setValue("state", _vehList);
	}

	protected String getReportSectionTag() {
		return "road_report";
	}

	protected int calculateBaseSpeed()
	{
		
		return Math.min(_maxSpeed, (_maxSpeed / Math.max(_vehList.size(), 1)) + 1);
	}
	
	protected int reduceSpeedFactor(int i) // adding a parameter
	{
		int faultyCounter = 0;
		int reductionFactor = 1;
		
		for(int j = i + 1; j < _vehList.size(); j++) {
			if(_vehList.get(j).getFaultyTime() > 0) faultyCounter++; 		   //check if the current vehicle v has a faulty vehicle in front of it. if so, red_factors[i?] = 2, if not, = 1
		}
			
		if(faultyCounter > 0) reductionFactor = 2;
		
		return reductionFactor;	
	}

	void advance() {
		
		if(_vehList.isEmpty())
			return;
		
		for(int i = 0; i < _vehList.size() - 1; i++) {
			
			// set v._currentSpeed to base_speed / reduction_factor
			_vehList.get(i).setSpeed(calculateBaseSpeed() / reduceSpeedFactor(i));
			
			// ask veh to advance
			_vehList.get(i).advance();
		}
		
		_vehList.get(_vehList.size() - 1).setSpeed(calculateBaseSpeed()); 
		
		_vehList.sort(null); // check that vehicles in same position are ordered by order of arrival
								
		//sort list
		
	}
	
	void enter(Vehicle v)
	{
		_vehList.add(v);
//		v.setLocation(0);
		_vehList.sort(null);
	}
	//KEEP LIST SORTED
	
	void exit(Vehicle v)
	{
		_vehList.remove(v);
		_vehList.sort(null);
	}

	public int getLength() {
		return _length;
	}
	
	public Junction getSource() {
		return _source;
	}
	
	public Junction getDestination() {
		return _destination;
	}
	
	public List<Vehicle> getVehicles(){
		return _vehList;
	}
}
