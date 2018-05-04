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

	
public String template() {
		
		return "[new_road]" + System.getProperty("line.separator") +
				"time = " + System.getProperty("line.separator") +
				"id = " + System.getProperty("line.separator") +
				"src = " + System.getProperty("line.separator") +
				"dest = " + System.getProperty("line.separator") +
				"max_speed = " + System.getProperty("line.separator") +
				"length = " + System.getProperty("line.separator");
				
	}
}
