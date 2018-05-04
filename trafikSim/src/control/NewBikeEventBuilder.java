package control;

import ini.IniSection;
import model.Event;
import model.NewBikeEvent;

public class NewBikeEventBuilder extends EventBuilder {

	@Override
	public Event parse(IniSection section) {
		if(!section.getTag().equals("new_vehicle"))
			return null;
		
		if(section.getKeys().contains("type") && section.getValue("type").equals("bike"))
			return new NewBikeEvent(
				EventBuilder.parseNonNegInt(section, "time", 0),
				EventBuilder.validId(section, "id"),
				EventBuilder.validIds(section, "itinerary"),
				EventBuilder.parsePosInt(section, "max_speed", 1),
				EventBuilder.validId(section, "type")								
				);
		else 
			return null;
	}

public String template() {
		
		return "[new_vehicle]" + System.getProperty("line.separator") +
				"time = " + System.getProperty("line.separator") +
				"id = " + System.getProperty("line.separator") +
				"itenerary = " + System.getProperty("line.separator") +
				"max_speed = " + System.getProperty("line.separator") +
				"type = bike" + System.getProperty("line.separator");
	}

}
