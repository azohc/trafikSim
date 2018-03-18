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
	}
	

	public void advance() 
	{
		int i = 0;
		
		//for the first incomingRoad with a green light
		while((i < _incRoads.size()) && !(_incRoads.get(i).hasGreenLight())) 
			i++;
		
		if(i == _incRoads.size() || _incRoads.isEmpty())
			i = -1; //if no road has a green light, or there is no incoming roads
		
		else {
			_incRoads.get(i).advanceFirstVehicle();	//advance first vehicle, if any
			_incRoads.get(i).getRoad().getVehicles().sort(new Vehicle.VehicleComparator());
		}
			
		if(!_incRoads.isEmpty())//as long as there is incoming roads
			switchLights(i); //update traffic light i to red, and i + 1 to green
		
	}

	public Road roadTo(Junction junc) {
		return 	_outgoingRoads.get(junc);
	}
	
	//public Road roadFrom(Junction junc) {
		//roadFrom = _road of IncomingRoad where _road.destination == junc
		//how to distinguish between them? maybe 2 or 3 roads meet the condition above
		//return null;		
	//}
	
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
	
	protected void switchLights(int pos) {
		
		
		if(pos != -1 && pos != _incRoads.size() - 1) { 		//for lights 0 to size - 2
			_incRoads.get(pos).setGreen(false);			//set current light to red
			_incRoads.get(pos + 1).setGreen(true);			//set next light to green
		}
		
		else 		// either no green lights or light (size - 1) == green
		{
			_incRoads.get(_incRoads.size() - 1).setGreen(false);
			_incRoads.get(0).setGreen(true);	
		}
	}
	
	protected String getReportSectionTag() {
		 return "junction_report";
	}
	
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

}
