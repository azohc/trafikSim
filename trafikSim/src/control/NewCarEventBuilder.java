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

}
