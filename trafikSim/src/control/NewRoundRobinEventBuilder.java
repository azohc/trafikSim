package control;

import ini.IniSection;
import model.Event;
import model.NewRoundRobinJunctionEvent;

public class NewRoundRobinEventBuilder extends EventBuilder {

	@Override
	public Event parse(IniSection section) {
		if(!section.getTag().equals("new_junction"))
			return null;
		
		if(section.getKeys().contains("type") && section.getValue("type").equals("rr"))
			return new NewRoundRobinJunctionEvent(
					EventBuilder.parseNonNegInt(section, "time", 0),
					EventBuilder.validId(section, "id"),
					EventBuilder.validId(section, "type"),
					EventBuilder.parsePosInt(section, "max_time_slice", 1),
					EventBuilder.parsePosInt(section, "min_time_slice", 1)
					);			
		else return null;
	}

	public String template(){
		
		return "[new_junction]" + System.getProperty("line.separator") +
				"time = " + System.getProperty("line.separator") +
				"id = " + System.getProperty("line.separator") +
				"max_time_slice = " + System.getProperty("line.separator") +
				"min_time_slice = " + System.getProperty("line.separator") +
				"type = rr";
	}
}
