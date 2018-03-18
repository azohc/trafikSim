package control;

import ini.Ini;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import model.Event;
import model.TrafficSimulator;

public class Controller {
	
	protected TrafficSimulator _sim;
	EventBuilder[] _eventBuilders = {};
	
	public Controller(TrafficSimulator sim){
		_sim = sim;
	}
	
	public void setEventBuilders(EventBuilder[] eventBuilders){
		_eventBuilders = eventBuilders;
	}
	
	public void reset(){
		_sim.reset();
	}
	
	public void setOutputStream(OutputStream outStream) { 
		_sim.setOutputStream(outStream);
	}
	
	public void run(int ticks) {  
		_sim.run(ticks);
	}
	
	public void loadEvents(InputStream inStream) { 
		
		try {
			Ini x = new Ini(inStream);
			Event e = null;
			boolean parsed = false;
			
			for(int j = 0; j < x.getSections().size(); j++) {
				parsed = false;
				
				for (int i = 0; i < _eventBuilders.length; i++){	
					
					e = _eventBuilders[i].parse(x.getSections().get(j));
					
					try {
						if(e != null) {		//add event to the simulator
							_sim.addEvent(e);	
							parsed = true;
						}
						
						else if(i == _eventBuilders.length - 1 && !parsed)	//report unrecognised events
							throw new SimulatorError("Unrecognized event");
						
					}catch (SimulatorError f) {	//most errors should come here
						System.out.println(f.getMessage());
					}
				}
			}
		} 
		catch (IOException e) {		//for x = new Ini
			System.out.println("Input error during ini parsing");
		} 
		catch (Exception g) {	//for whatever other errors may arise
			g.printStackTrace();
			System.out.println("Unknown error during event loading");
		}
		
		
	}
	
}
