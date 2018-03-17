package control;

import ini.IniSection;
import model.Event;
import model.NewCarEvent;

public class NewCarEventBuilder extends EventBuilder {

	public Event parse(IniSection section) {
		if( !section.getTag().equals("new_vehicle"))
			return null;
		
		return new NewCarEvent(
				EventBuilder.parseNonNegInt(section, "time", 0),
				EventBuilder.validId(section, "id"),
				EventBuilder.validIds(section, "itinerary"),
				EventBuilder.parsePosInt(section, "max_speed", 0),
				EventBuilder.validId(section, "type"),
				EventBuilder.parsePosInt(section, "resistance", 0),
				EventBuilder.parseNonNegDouble(section, "faulty_probability", 0),
				EventBuilder.parsePosInt(section, "max_faulty_duration", 0),
				EventBuilder.parsePosLong(section, "seed", 1)
				);
	}

}
