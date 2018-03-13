package model;

import java.util.ArrayList;
import java.util.List;

import ini.IniSection;

public class Vehicle extends SimulatedObject implements Comparable<Vehicle>
{

	private int _maxSpeed;
	private int _currentSpeed;			// speed of the vehicle should always be 0 for vehicles that are in
			// a faulty state, are waiting at a junction, or have arrived at their destination.
	private Road _road;
	private int _kilometrage;
	private int _location;				// location on current road
	private List<Junction> _itinerary;	// roads to cover by the v
	private boolean _atJunction;			// vehicle at junction => true => speed = 0
	private int _faulty;	// vehicle faulty for 'faulty' ticks
	private int _itIndex; // itinerary index to check at what junction the vehicle is at
	private boolean _arrived;
	
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
		i.setValue("kilometrage", _kilometrage);
		i.setValue("faulty", _faulty);
		
		//falta asignar el atributo ROAD a VEHICLE nada más empezar la simulación. En la siguiente linea _road.getId (de this vehicle) es null
		//en los ini.eout, siempre salen una de las dos opciones de abajo, o arrived o la carretera y su location
		//me da a mi que si itIndex == 0 => tirar un moveToNextRoad
		
		i.setValue("location", (_atJunction) ? "arrived" : "(" + _road.getId() + "," + _location + ")");
		
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
			if(_location + _currentSpeed > _road.getLength()) {
				_kilometrage += (_road.getLength() - _location);
				_location = _road.getLength();
				
				//enter the queue of the corresponding junction
				_road.getDestination().enter(this);	//	check
				_atJunction = true;				
				_currentSpeed = 0;
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
			
			_road = _itinerary.get(_itIndex).roadTo(_itinerary.get(_itIndex + 1));	//esto devuelve _road = null. trabajar la funcion roadTo y el mapa _outGoingRoad
			_road.enter(this);
			_itIndex++;

		}
		
		else if(_itIndex == _itinerary.size() - 1){ // check size
			_arrived = true;
		}
		else {
			
			_road.exit(this);
			_road = _itinerary.get(_itIndex).roadTo(_itinerary.get(_itIndex + 1));
			_itIndex++;
			_road.enter(this);
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
	}

	void setSpeed(int speed)
	{
		if(speed <= _maxSpeed && _faulty == 0)
			_currentSpeed = speed;
	}
	
	@Override
	public int compareTo(Vehicle o) {
		return Integer.valueOf(this._location).compareTo(o._location);
	}
	
//	void setLocation(int location) {
//		_location = location;
//	}
	
}
