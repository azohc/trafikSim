package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoadMap {
	
	private List<Vehicle> _vehicles;
	private List<Road> _roads;
	private List<Junction> _junctions;
	private Map<String, Vehicle> _vehMap;
	private Map<String, Road> _roadMap;
	private Map<String, Junction> _juncMap;
	
	RoadMap(){
		_vehicles = new ArrayList<Vehicle>();
		_roads = new ArrayList<Road>();
		_junctions = new ArrayList<Junction>();
		_vehMap = new HashMap<String, Vehicle>();
		_roadMap = new HashMap<String, Road>();
		_juncMap = new HashMap<String, Junction>();
	}
	
	public Vehicle getVehicle(String id){
		
		return _vehMap.get(id);
	}
	public Road getRoad(String id){
		
		return _roadMap.get(id);
	}
	public Junction getJunction(String id){
		
		return _juncMap.get(id);
	}
	
	public List<Vehicle> getVehicles(){
		
		return _vehicles;
	}
	
	public List<Road> getRoad(){
		
		return _roads;
	}
	
	public List<Junction> getJunction(){
		
		return _junctions;
	}
	
	void addJunction(Junction junction){
		
		_junctions.add(junction);
		_juncMap.put(junction.getId(), junction);
	
	}
	
	void addRoad(Road road){
		
		_roads.add(road);
		_roadMap.put(road.getId(), road);
		
	}
	void addVehicle(Vehicle vehicle){
		
		_vehicles.add(vehicle);
		_vehMap.put(vehicle.getId(), vehicle);
		
	}
	
	void clear() {
		
		_vehicles.clear();
		_roads.clear();
		_junctions.clear();
		_vehMap.clear();
		_roadMap.clear();
		_juncMap.clear();
	}
	
	public String generateReport(int time){
		//TODO
		return "";
	}
}
