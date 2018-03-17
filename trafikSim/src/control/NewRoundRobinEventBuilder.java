package control;

import ini.IniSection;
import model.Event;
import model.NewRoundRobinJunctionEvent;

public class NewRoundRobinEventBuilder extends EventBuilder {

	@Override
	public Event parse(IniSection section) {
		if( !section.getTag().equals("new_junction"))
			return null;
		
		return new NewRoundRobinJunctionEvent(
				EventBuilder.parseNonNegInt(section, "time", 0),
				EventBuilder.validId(section, "id"),
				EventBuilder.validId(section, "type"),
				EventBuilder.parsePosInt(section, "max_time_slice", 1),
				EventBuilder.parsePosInt(section, "min_time_slice", 1)
				);				
	}

}
