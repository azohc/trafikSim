package model;

import java.util.List;

import ini.IniSection;

public class Vehicle extends SimulatedObject implements Comparable<Vehicle>
{

	protected int _maxSpeed;
	protected int _currentSpeed;			// speed of the vehicle should always be 0 for vehicles that are in
			// a faulty state, are waiting at a junction, or have arrived at their destination.
	private Road _road;
	protected int _kilometrage;
	private int _location;				// location on current road
	private List<Junction> _itinerary;	// roads to cover by the v
	private boolean _atJunction;			// vehicle at junction => true => speed = 0
	protected int _faulty;	// vehicle faulty for 'faulty' ticks
	private int _itIndex; // itinerary index to check at what junction the vehicle is at
	private boolean _arrived;
	protected int _lastFaultyKm;		//to keep track of the kilometrage of last time veh was faulty
	
	public Vehicle(String id, int maxSpeed, List<Junction> it) 
	{
		super(id);
		_maxSpeed = maxSpeed;
		_itinerary = it;
		_kilometrage = 0;
		_location = 0;
		_atJunction = false;
		_faulty = 0;
		_itIndex = 0;
		_arrived = false;
		_itinerary = it;
	}

	protected void fillReportDetails(IniSection i) 
	{
		i.setValue("speed", _currentSpeed);
		i.setValue("kilometrage", _kilometrage);
		i.setValue("faulty", _faulty);
		i.setValue("location", (_arrived) ? "arrived" : "(" + _road.getId() + "," + _location + ")");
		
	}

	protected String getReportSectionTag() 
	{		
		return "vehicle_report";
	}

	void advance() 
	{
		if(_faulty > 0)
			_faulty--; 
		else if (_atJunction) {
			
		}
		else
		{
			if(_location + _currentSpeed >= _road.getLength()) {
				_kilometrage += (_road.getLength() - _location);
				_location = _road.getLength();

				if(_itIndex == _itinerary.size() - 1) // //fIX//fIX//fIX esto hay que arreglarlo. o se mira aqui o se mira en mvtonextroad
					_arrived = true;//fIX
				
				
				_currentSpeed = 0;
				_atJunction = true;	
				//enter the queue of the corresponding junction
				_road.getDestination().enter(this);	//	check
				_itIndex++;			
			}
			else {
				_kilometrage += _currentSpeed;	
				_location += _currentSpeed;
			}
		
		}
	}
	
	void moveToNextRoad()
	{
		
		if(_itIndex == 0){
			
			_road = _itinerary.get(_itIndex).roadTo(_itinerary.get(_itIndex + 1));	
			_road.enter(this);

		}
		else if(_arrived)
		{}
		else {
			
			////////////////////
			////////////////////arreglar cuando poner arrived a true y como tratar el ultimo junction
			////////////////////creo que con esto lo tenemos TODO
			_road.exit(this);
			if(_itIndex == _itinerary.size() - 1)//fIX
				_arrived = true;
			
			else{
				_road = _itinerary.get(_itIndex).roadTo(_itinerary.get(_itIndex + 1)); //fIX
				_road.enter(this);
			}
			_atJunction = false;
			_location = 0;	
		
		}
	}
	
	
	public boolean atDestination()
	{
		return _arrived;	//todo
	}

	public Road getRoad()
	{
		return _road;
	}
	
	public int getSpeed() {
		return _currentSpeed;
	}
	
	public int getMaxSpeed() {
		return _maxSpeed;
	}
	
	public int getKilometrage() {
		return _kilometrage;
	}
	
	public int getFaultyTime() {
		return _faulty;
	}
	
	public List<Junction> getItinerary()
	{
		return _itinerary;
	}
	
	void makeFaulty(int ticks)
	{
		_currentSpeed = 0;
		_faulty += ticks;
		_lastFaultyKm = _kilometrage;
	}

	void setSpeed(int speed)
	{	
		if(_faulty == 0 && !_atJunction){
			if(speed >_maxSpeed) _currentSpeed = _maxSpeed;
			else _currentSpeed = speed;
		}
	}
	
	@Override
	public int compareTo(Vehicle o) {
		return -1*Integer.valueOf(this._location).compareTo(o._location);
	}

	public int get_location() {
		return _location;
	}

	public void set_location(int _location) {
		this._location = _location;
	}

	public boolean isAtJunction() {
		return _atJunction;
	}

	public void setAtJunction(boolean _atJunction) {
		this._atJunction = _atJunction;
	}

	public boolean isArrived() {
		return _arrived;
	}

	public void setArrived(boolean _arrived) {
		this._arrived = _arrived;
	}
	
//	void setLocation(int location) {
//		_location = location;
//	}
	
}
