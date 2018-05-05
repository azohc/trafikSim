package model;

import ini.IniSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList; //linked list o list? quereos queue
import java.util.List;
import java.util.Map;

public class Junction extends SimulatedObject 
{
	
	protected List<IncomingRoad> _incRoads;
	protected Map<Junction, Road> _outgoingRoads;
	protected int _greenLightIndex;		// -1 when no light is green

	public static class IncomingRoad{
		
		protected Road _road; //road that leads to this junction
		protected List<Vehicle> _queue;
		protected boolean _green;
		
		protected IncomingRoad(Road road) {
			_road = road;
			_queue = new LinkedList<Vehicle>();
			_green = false;
		}
		
		public Road getRoad() {
			return _road;
		}
		
		public boolean hasGreenLight(){
			return _green;
		}
		
		protected void setGreen(boolean green) {
			_green = green;
		}
		
		protected void advanceFirstVehicle() {
			if(!_queue.isEmpty()) {
				_queue.get(0).moveToNextRoad();
				_queue.remove(0);
			}
		}
		
		protected void addVehicle(Vehicle v) {
			_queue.add(v);
		}
		
		@Override
		public String toString() {
	
			String out = "(";
			out += _road.getId() + ",";
			out += (_green) ? "green,[" : "red,[";
			
			if(_queue.isEmpty())
				return out += "])";
				
			
			for(int i = 0 ; i < _queue.size() - 1; i++)
				out += _queue.get(i).getId() + ",";
			
			out += _queue.get(_queue.size() - 1).getId() + "]";
			
			return out + ")";
		}
	}
	
	public Junction(String id) 
	{
		super(id);
		_incRoads = new ArrayList<IncomingRoad>();
		_outgoingRoads = new HashMap<Junction, Road>();
		_greenLightIndex = -1;
	}
	

	@Override
	public void advance() 
	{
		if(!_incRoads.isEmpty()) {
			if(_greenLightIndex != -1)//advance first vehicle of incRoad number greenLightIndex
			{
				_incRoads.get(_greenLightIndex).advanceFirstVehicle();
				_incRoads.get(_greenLightIndex).getRoad().getVehicles().sort(new Vehicle.VehicleComparator());
				switchLights(); //update traffic light i to red, and i + 1 to green
			}
			else
				switchLights();
		}
	}

	public Road roadTo(Junction junc) {
		return 	_outgoingRoads.get(junc);
	}
	

	
	public List<IncomingRoad> getRoadsInfo() {
		return _incRoads;
	}
	
	void addIncomingRoad(Road road) {
		_incRoads.add(createIncomingRoadQueue(road));
	}
	//USE MAP: this will help in MoveToNextRoad
	void addOutGoingRoad(Road road) {
		_outgoingRoads.put(road.getDestination(), road);
	}
	
	void enter(Vehicle vehicle) {
		int i = 0;
		while((i < _incRoads.size()) && (vehicle.getRoad() !=  _incRoads.get(i).getRoad())) {
			i++;
		}
		
		_incRoads.get(i).addVehicle(vehicle);
	}
	
	protected IncomingRoad createIncomingRoadQueue(Road road) {
		return new IncomingRoad(road);
	}
	
	protected void switchLights() {
		
		
		if(_greenLightIndex == _incRoads.size() - 1) {
			_incRoads.get(_greenLightIndex).setGreen(false);	//turn last road's light to red
			_greenLightIndex = 0;
			_incRoads.get(_greenLightIndex).setGreen(true);		//turn first road's light to green
		}
		
		else {
			if(_greenLightIndex != -1)
				_incRoads.get(_greenLightIndex).setGreen(false);	//turn current green light to red
			
			_greenLightIndex++;
			_incRoads.get(_greenLightIndex).setGreen(true);		//turn next road's light to green
		}
	
	}
	
	@Override
	protected String getReportSectionTag() {
		 return "junction_report";
	}
	
	@Override
	protected void fillReportDetails(IniSection is) {
		String o = "";
		if(_incRoads.isEmpty())
			is.setValue("queues", o);
		
		else
		{
			for(int i = 0; i < _incRoads.size() - 1; i++)
				 o += _incRoads.get(i).toString() + ",";
			
			o +=  _incRoads.get(_incRoads.size() - 1).toString();		
			
			is.setValue( "queues", o);
		}
	}


	public int getGreenLightIndex() {
		return _greenLightIndex;
	}


	public void setGreenLightIndex(int _greenLightIndex) {
		this._greenLightIndex = _greenLightIndex;
	}

}
