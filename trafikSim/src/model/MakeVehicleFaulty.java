package model;

public class MakeVehicleFaulty extends Event {
	
	protected String[] _vehID;
	protected int _duration;
	
	public MakeVehicleFaulty(int time, String[] vehID, Integer duration) {
		super(time);
		_vehID = vehID;
		_duration = duration;
	}
	
	@Override
	public void execute(RoadMap map, Integer time) {
		
		for(int i = 0; i < _vehID.length; i++)
			map.getVehicle(_vehID[i]).makeFaulty(_duration);
		
	}

public String toString(){
	
	String out = "";
	
	for(int i = 0; i < _vehID.length; i++)
		out += "Vehicle " + _vehID[i] + " made faulty for " + _duration  + " ticks. \n";

		return out;
	}

}
