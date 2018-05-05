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

@Override
public String toString(){
	
	String out = "Break Vehicles [";
	
	for(int i = 0; i < _vehID.length; i++)
		out += _vehID[i] + ",";
	
	out = out.substring(0, out.length() - 1) + "]";
		return out;
	}

}
