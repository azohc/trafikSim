package model;

import gui.Observer;
import gui.TrafficSimulatorObserver;

import java.io.IOException;

import control.SortedArrayList;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import control.SimulatorError;

public class TrafficSimulator implements Observer<TrafficSimulatorObserver> {

	private RoadMap _map;
	private List<Event> _events;
	private int _time;
	private OutputStream _outStream;
	private List<TrafficSimulatorObserver> _obsList;
	
	public TrafficSimulator(OutputStream outStream){
		_outStream = outStream;
		_time = 0;
		_map = new RoadMap();
		_events = new SortedArrayList<Event>(new Event.EventComparator());	
		_obsList = new ArrayList<>();
	}
	
	public void reset(){
		_map.clear();
		_events.clear();
		_time = 0;
		notifyReset();
	}
	
	public void setOutputStream(OutputStream outStream){
		_outStream = outStream;
	}
	
	public void addEvent(Event e) throws Exception{
		if(e.getTime() >= _time){
			_events.add(e);
			notifyNewEvent();
		}
		else{
			SimulatorError err = new SimulatorError("Invalid event time: lower than the simulator's time");
			notifyError(err);
			throw err;	
		}
	}
	
	private void notifyNewEvent() {
		for(TrafficSimulatorObserver o : this._obsList)
			o.addEvent(_time, _map, _events);
	}
	
	private void notifyError(SimulatorError e){
		for(TrafficSimulatorObserver o : this._obsList)
			o.addSimError(_time, _map, _events, e);
	}
	
	private void notifyReset(){
		for(TrafficSimulatorObserver o : this._obsList)
			o.addReset(_time, _map, _events);
	}
	
	private void notifyStep(){
		for(TrafficSimulatorObserver o : this._obsList)
			o.addStep(_time, _map, _events);		
	}
	
	private void notifyNewObs(TrafficSimulatorObserver o){	
		o.registered(_time, _map, _events);		
	}
	

	public void run(int ticks){
		int limit = _time + ticks - 1;
		
		while (_time <= limit) {
			
				
			for (int i = 0; i < _events.size(); i++)
				if(_time == _events.get(i).getTime())	{				
					try {
						_events.get(i).execute(_map, _time);
					} catch (SimulatorError f) {
						System.out.println(f.getMessage());
					}
				}
			
			for(Road r : _map.getRoad()) 
				r.advance();
			
			for(Junction j : _map.getJunction())
				j.advance();
			
			_time++;
			
			//notifications go here -> iterate through classes to notify a step is taken
			notifyStep();
			
			if(_outStream != null){
				try {
					if(_map.generateReport(_time).getBytes() != null)
						_outStream.write(_map.generateReport(_time).getBytes());
				} catch (IOException e) {
					System.out.println("Output error when writing the report for time: " + _time);
				}
			}
		}
	}

	public void addObserver(TrafficSimulatorObserver o) {
		if (o != null && !_obsList.contains(o)) 
			_obsList.add(o);
		
		notifyNewObs(o);
	}
	
	public void removeObserver(TrafficSimulatorObserver o) {
		if (o != null && _obsList.contains(o)) 
			_obsList.remove(o);
	}
}

