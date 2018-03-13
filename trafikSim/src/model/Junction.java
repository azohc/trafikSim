package model;

import ini.IniSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList; //linked list o list? quereos queue
import java.util.List;
import java.util.Map;

public class Junction extends SimulatedObject 
{
	
	protected List<IncomingRoad> _roads;
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

			for(int i = 0 ; i < _queue.size() - 1; i++)
				out += _queue.get(i).getId() + ",";
			
			out += _queue.get(_queue.size()).getId() + "]";
			
			return out + ")";
		}
	}
	
	public Junction(String id) 
	{
		super(id);
		_roads = new ArrayList<IncomingRoad>();
		_outgoingRoads = new HashMap<Junction, Road>();
	}
	

	public void advance() 
	{
		int i = 0;
		
		//for the first incomingRoad with a green light
		while((i < _roads.size()) && !(_roads.get(i).hasGreenLight())) 
			i++;
		
		
		if(i <_roads.size()) i = -1; //if no road has a green light
		else 
			_roads.get(i).advanceFirstVehicle();	//advance first vehicle, if any
		
		switchLights(i); //update traffic light i to red, and i + 1 to green
		
	}

	public Road roadTo(Junction junc) {
		
		return _outgoingRoads.get(junc);
	}
	
	//public Road roadFrom(Junction junc) {
		//roadFrom = _road of IncomingRoad where _road.destination == junc
		//how to distinguish between them? maybe 2 or 3 roads meet the condition above
		//return null;		
	//}
	
	public List<IncomingRoad> getRoadsInfo() {
		
		return _roads;
	}
	
	void addIncomingRoad(Road road) {
		
		_roads.add(new IncomingRoad(road));
		
	}
	//USE MAP: this will help in MoveToNextRoad
	void addOutGoingRoad(Road road) {
		
		_outgoingRoads.put(road.getDestination(), road);
		
	}
	
	void enter(Vehicle vehicle) {
		
		int i = 0;
		while((i < _roads.size()) && (vehicle.getRoad() !=  _roads.get(i).getRoad())) {
			i++;
		}
		
		_roads.get(i).addVehicle(vehicle);
	}
	
	protected IncomingRoad createIncomingRoadQueue(Road road) {
		return new IncomingRoad(road);
	}
	
	protected void switchLights(int pos) {
		
		if(pos != -1 && pos != _roads.size() - 1) {	//for lights 0 to size - 2
			_roads.get(pos).setGreen(false);			//set current light to red
			_roads.get(pos + 1).setGreen(true);			//set next light to green
		}
		else {		//for no green lights, or light size - 1 == green
			_roads.get(_roads.size() - 1).setGreen(false);
			_roads.get(0).setGreen(true);					
		}
	}
	
	protected String getReportSectionTag() {
		
		 return "junction_report";
	}
	
	protected void fillReportDetails(IniSection is) {
		is.setValue( "queues" ,  _roads);
	}

}
