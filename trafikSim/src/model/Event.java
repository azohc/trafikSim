package model;

public abstract class Event implements Comparable<Event> {
	
	protected Integer _time;
	
	public Event(int time){
		
		_time = time;
	}
	
	public int getTime(){
	
		return _time;
	}

	public abstract void execute(RoadMap map, Integer time);

	public int compareTo(Event e){
		
		return Integer.valueOf(this._time).compareTo(e._time);
	}
}
