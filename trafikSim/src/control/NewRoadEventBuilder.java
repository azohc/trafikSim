package control;

import ini.IniSection;
import model.Event;
import model.NewRoadEvent;

public class NewRoadEventBuilder extends EventBuilder {

	@Override
	public Event parse(IniSection section) {
		if (!section.getTag().equals("new_road") || section.getValue("type") != null)
			return null;	
		
		return new NewRoadEvent(
				EventBuilder.parseNonNegInt(section, "time", 0), 
				EventBuilder.validId(section, "id"),			
				EventBuilder.parsePosInt(section, "length", 1),
				EventBuilder.parsePosInt(section, "max_speed", 1),
				EventBuilder.validId(section, "src"),
				EventBuilder.validId(section, "dest"));
	}

}
