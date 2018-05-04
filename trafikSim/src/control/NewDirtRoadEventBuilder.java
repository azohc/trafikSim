package control;

import ini.IniSection;
import model.Event;
import model.NewDirtRoadEvent;

public class NewDirtRoadEventBuilder extends EventBuilder {
	@Override
	public Event parse(IniSection section) {
		if(!section.getTag().equals("new_road"))
			return null;
		
		if(section.getKeys().contains("type") && section.getValue("type").equals("dirt"))
				return new NewDirtRoadEvent(
					EventBuilder.parseNonNegInt(section, "time", 0),
					EventBuilder.validId(section, "id"),
					EventBuilder.validId(section, "src"),
					EventBuilder.validId(section, "dest"),
					EventBuilder.parsePosInt(section, "max_speed", 1),
					EventBuilder.parsePosInt(section, "length", 1),
					EventBuilder.validId(section, "type")
					);
		else return null;
	}

public String template() {
		
		return "[new_road]" + System.getProperty("line.separator") +
				"time = " + System.getProperty("line.separator") +
				"id = " + System.getProperty("line.separator") +
				"src = " + System.getProperty("line.separator") +
				"dest = " + System.getProperty("line.separator") +
				"max_speed = " + System.getProperty("line.separator") +
				"length = " + System.getProperty("line.separator") +
				"type = dirt" + System.getProperty("line.separator");
	}

}

