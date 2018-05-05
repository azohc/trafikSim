package control;

import ini.IniSection;
import model.Event;
import model.NewLanesRoadEvent;

public class NewLanesRoadEventBuilder extends EventBuilder {

	@Override
	public Event parse(IniSection section) {
		if(!section.getTag().equals("new_road"))
			return null;
		
		if(section.getKeys().contains("type") && section.getValue("type").equals("lanes"))
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
		else return null;
	}

@Override
public String template() {
		
		return "[new_road]" + System.getProperty("line.separator") +
				"time = " + System.getProperty("line.separator") +
				"id = " + System.getProperty("line.separator") +
				"src = " + System.getProperty("line.separator") +
				"dest = " + System.getProperty("line.separator") +
				"max_speed = " + System.getProperty("line.separator") +
				"length = " + System.getProperty("line.separator") +
				"lanes = " + System.getProperty("line.separator") +
				"type = lanes" + System.getProperty("line.separator");
	}
	
}
