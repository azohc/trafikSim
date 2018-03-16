package control;

import ini.IniSection;
import model.Event;
import model.NewCarEvent;

public class NewCarEventBuilder extends EventBuilder {

	@Override
	public Event parse(IniSection section) {
		if( !section.getTag().equals("new_car"))
			return null;
		
		return new NewCarEvent(
				EventBuilder.parseNonNegInt(section, "time", 0),
				EventBuilder.validId(section, "id"),
				EventBuilder.validIds(section, "itinerary"),
				EventBuilder.parseNonNegInt(section, "max_speed", 0),
				EventBuilder.validId(section, "type"),
				EventBuilder.validId(section, "resistance"),
				EventBuilder.validId(section, "faulty_probability"),
				EventBuilder.validId(section, "max_faulty_duration"),
				EventBuilder.parseNonNegInt(section, "seed", 1)
				)
	}

}
