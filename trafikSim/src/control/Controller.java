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
			
			
			for(int j = 0; j < x.getSections().size(); j++) {
				for (int i = 0; i < _eventBuilders.length; i++){	
					e = _eventBuilders[i].parse(x.getSections().get(j));
					if(e != null)		//add event to the simulator
						_sim.addEvent(e);						//CHECKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK
					else{	//report unrecognised events
						//throw exception?????????????????????????????????????
					}
				}
			}
		} catch (IOException e) { //for x = new Ini
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception f) {	//for _sim.addEvent
			// TODO Auto-generated catch block
			f.printStackTrace();
		}
		
		
	}
	
}
