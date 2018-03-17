package control;

import ini.IniSection;
import model.Event;

public class NewRoundRobinEventBuilder extends EventBuilder {

	@Override
	public Event parse(IniSection section) {
		if( !section.getTag().equals("new_junction"))
			return null;
		
		return new NewRoundRobinEvent(
				
				
				
				);				
	}

}
