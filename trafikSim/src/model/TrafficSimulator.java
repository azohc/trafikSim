package model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import control.SimulatorError;

public class TrafficSimulator {

	private RoadMap _map;
	private List<Event> _events;
	private int _time;
	private OutputStream _outStream;
	
	public TrafficSimulator(OutputStream outStream){
		_outStream = outStream;
		_time = 0;
		_map = new RoadMap();
		_events = new LinkedList<Event>();
		
	}
	
	public void reset(){
		_map.clear();
		_events.clear();
		_time = 0;
	}
	
	public void setOutputStream(OutputStream outStream){
		
		_outStream = outStream;
		
	}
	
	public void addEvent(Event e) throws Exception{
		if(e.getTime() >= _time){
			_events.add(e);
			_events.sort(null);
		}
		else
			throw new SimulatorError("Invalid event time: lower than the simulator's time");	
	}
	
	public void run(int ticks){
		int limit = _time + ticks - 1;
		
		while (_time <= limit) {
			try {
				
				for (int i = 0; i < _events.size(); i++)
					if(_time == _events.get(i).getTime())					
						_events.get(i).execute(_map, _time);
				
				for(int i = 0; i < _map.getRoad().size(); i++)
					_map.getRoad().get(i).advance();
				
				for(int i = 0; i < _map.getJunction().size(); i++)
					_map.getJunction().get(i).advance();
				
				_time++;
			
			
				if(_map.generateReport(_time).getBytes() != null)
					_outStream.write(_map.generateReport(_time).getBytes());
			} catch (IOException e) {
				System.out.println("Output error when writing the report for time: " + _time);
			}
			catch (SimulatorError f) {	//most errors should come here
				System.out.println(f.getMessage());
				_time++;
			}
		}
	
	}	
}
