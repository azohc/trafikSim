package model;

import java.util.Comparator;

public abstract class Event {
	
	protected Integer _time;
	protected final static String dupeObj = "An object with this identifier already exists";
	
	public static class EventComparator implements Comparator<Event> {

		@Override
		public int compare(Event o1, Event o2) {
			return -1 * Integer.signum(o1._time - o2._time);
		}
		
	}
	public Event(int time){
		
		_time = time;
	}
	
	public int getTime(){
	
		return _time;
	}

	public abstract void execute(RoadMap map, Integer time);

}
