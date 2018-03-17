package control;

import ini.IniSection;
import model.Event;
import model.NewLanesRoadEvent;

public class NewLanesRoadEventBuilder extends EventBuilder {

	@Override
	public Event parse(IniSection section) {
		if( !section.getTag().equals("new_road"))
			return null;
		
		return new NewLanesRoadEvent(
				EventBuilder.parseNonNegInt(section, "time", 0),
				EventBuilder.validId(section, "id"),
				EventBuilder.validId(section, "src"),
				EventBuilder.validId(section, "dest"),
				EventBuilder.parsePosInt(section, "max_speed", 1),
				EventBuilder.parsePosInt(section, "length", 1),
				EventBuilder.validId(section, "type"),
				EventBuilder.parsePosInt(section, "lanes", 1)				
				);
	}

}
