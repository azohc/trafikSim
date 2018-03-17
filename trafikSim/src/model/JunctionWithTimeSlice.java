package model;

public abstract class JunctionWithTimeSlice extends Junction {

	
	public class IncRoadWithTimeSlice extends IncomingRoad 
	{
		protected int _timeSlice;
		protected int _usedTimeUnits;
		protected boolean _fullyUsed; 
		protected boolean _used;

		protected IncRoadWithTimeSlice(Road road) {
			super(road);
			_usedTimeUnits = 0;
			_used = false;
			_fullyUsed = true;
		}
		
		protected void setGreen(boolean green) {
			_green = green;
			_used = false;
			_fullyUsed = true;
		}
		
		
		public int getTimeSlice() {
			return _timeSlice;
		} 
		
		protected void setTimeSlice(int timeSlice) {
			_timeSlice = timeSlice;
		} 
		
		public int getUsedTimeUnits() {
			return _usedTimeUnits;
		} 
		
		protected void setUsedTimeUnits(int usedTimeUnits) {
			_usedTimeUnits = usedTimeUnits;
		} 
		public boolean isFullyUsed() { 
			return _fullyUsed;
		} 
		
		protected void setFullyUsed(boolean fullyUsed) {
			_fullyUsed = fullyUsed;
		} 
		
		public boolean isUsed() { 
			return _used;
		} 
		
		protected void setUsed(boolean used) { 
			_used = used;
		}
		
		protected void advanceFirstVehicle() { 
		
			//advance first veh in queue
			if(!_queue.isEmpty()) {
				_used = true;
				super.advanceFirstVehicle();
			}
			else 	//queue is empty
				_fullyUsed = false;
			
			_usedTimeUnits++;
		}
		
		public String toString() { 

			String out = "(";
			out += _road.getId() + ",";
			out += (_green) ? "green:" : "red:";
			out += _timeSlice + ",";
			
			if(_queue.isEmpty())
				return out += "])";
				
			//CHECK
			
			for(int i = 0 ; i < _queue.size() - 1; i++)
				out += _queue.get(i).getId() + ",";
			
			out += _queue.get(_queue.size() - 1).getId() + "]";
			
			return out + ")";
		}		
	}

	
	public JunctionWithTimeSlice(String id) {
		super(id);
	}
	

}
