package control;

import ini.IniSection;
import model.Event;
import model.NewCarEvent;

public class NewCarEventBuilder extends EventBuilder {

	public Event parse(IniSection section) {
		if(!section.getTag().equals("new_vehicle"))
			return null;
		
		if(section.getKeys().contains("type") && section.getValue("type").equals("car"))
			return new NewCarEvent(
					EventBuilder.parseNonNegInt(section, "time", 0),
					EventBuilder.validId(section, "id"),
					EventBuilder.validIds(section, "itinerary"),
					EventBuilder.parsePosInt(section, "max_speed", 1),
					EventBuilder.validId(section, "type"),
					EventBuilder.parsePosInt(section, "resistance", 1),
					EventBuilder.parseNonNegDouble(section, "fault_probability", 0),
					EventBuilder.parsePosInt(section, "max_fault_duration", 1),
					EventBuilder.parsePosLong(section, "seed", System.currentTimeMillis())
					);
		else return null;
	}
	
public String template() {
		
		return "[new_vehicle]" + System.getProperty("line.separator") +
				"time = " + System.getProperty("line.separator") +
				"id = " + System.getProperty("line.separator") +
				"itenerary = " + System.getProperty("line.separator") +
				"max_speed = " + System.getProperty("line.separator") +
				"resistance = " + System.getProperty("line.separator") +
				"fault_probability = " + System.getProperty("line.separator") +
				"max_fault_duration = " + System.getProperty("line.separator") +
				"seed = " + System.getProperty("line.separator") +
				"type = car" + System.getProperty("line.separator");
	}
}
