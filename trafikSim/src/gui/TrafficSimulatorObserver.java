package gui;

import java.util.List;

import model.RoadMap;
import control.SimulatorError;
import model.Event;

public interface TrafficSimulatorObserver {
	
	// notifica errores
	public void addSimError(int time, RoadMap map, List<Event> events, SimulatorError e);
	
	// notifica el avance de los objetos de simulación
	public void addStep(int time, RoadMap map, List<Event> events);
	
	// notifica que se ha generado un nuevo evento
	public void addEvent(int time, RoadMap map, List<Event> events);
	
	// notifica que la simulación se ha reiniciado
	public void addReset(int time, RoadMap map, List<Event> events);	
}
