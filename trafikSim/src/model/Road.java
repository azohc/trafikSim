package model;

import java.util.List;

import control.SortedArrayList;

import java.lang.Math;

import ini.IniSection;

public class Road extends SimulatedObject {

	private int _length;
	protected int _maxSpeed;
	protected List<Vehicle> _vehList;	// sort by veh location, descending order: in case of == location, FIFO
	private Junction _source;
	private Junction _destination;
	
	public Road(String id, int length, int maxSpeed, Junction start, Junction end) { //
		super(id);
		_length = length;
		_maxSpeed = maxSpeed;
		_source = start;
		_destination = end;
		_vehList = new SortedArrayList<Vehicle>(new Vehicle.VehicleComparator()); // always sorted array list by location
		//change to sorted array list at the end
	}

	@Override
	protected void fillReportDetails(IniSection i) {
		
		String o = "";
		if(_vehList.isEmpty())
			i.setValue("state", o);
		
		else
		{
			for(int a = 0 ; a < _vehList.size() - 1; a++)
				if(!_vehList.get(a).isArrived())
					o += "(" + _vehList.get(a).getId() + "," + _vehList.get(a).getLocation() + "),";
			
			if(!_vehList.get(_vehList.size() - 1).isArrived())
				o += "(" + _vehList.get(_vehList.size() - 1).getId() + "," + _vehList.get(_vehList.size() - 1).getLocation() + ")";
			
			i.setValue("state", o);
		}
	}

	@Override
	protected String getReportSectionTag() {
		return "road_report";
	}

	protected int calculateBaseSpeed()
	{
		return Math.min(_maxSpeed, (_maxSpeed / Math.max(_vehList.size(), 1)) + 1);
	}
	
	
	protected int reduceSpeedFactor(int faultyCounter) 
	{
		int reductionFactor = 1;
	
		if(faultyCounter > 0) reductionFactor = 2;
		
		return reductionFactor;	
	}
	

	@Override
	void advance() {		
		
		int faultyCounter = 0;
		if(_vehList.isEmpty())
			return;
	
		for(int i = _vehList.size() - 1; i > 0; i--){
			
			for(int j =  i - 1; j >= 0; j--)
				if(_vehList.get(i).getLocation() < _vehList.get(j).getLocation() &&
					_vehList.get(j).getFaultyTime() > 0)
						faultyCounter++;
			
			_vehList.get(i).setSpeed(calculateBaseSpeed() / reduceSpeedFactor(faultyCounter));
		}
		
		_vehList.get(0).setSpeed(calculateBaseSpeed());
		
		for(Vehicle v : _vehList)
			v.advance();
		
		_vehList.sort(new Vehicle.VehicleComparator()); 
	}
	
	void enter(Vehicle v)
	{
		_vehList.add(v);
	}
	
	void exit(Vehicle v)
	{
		_vehList.remove(v);
		_vehList.sort(new Vehicle.VehicleComparator());
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

	public int getMaxSpeed() {
		return _maxSpeed;
	}

	public void set_maxSpeed(int _maxSpeed) {
		this._maxSpeed = _maxSpeed;
	}
}