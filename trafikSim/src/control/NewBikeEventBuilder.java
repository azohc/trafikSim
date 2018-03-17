package control;

import ini.IniSection;
import model.Event;
import model.NewBikeEvent;

public class NewBikeEventBuilder extends EventBuilder {

	@Override
	public Event parse(IniSection section) {
		if( !section.getTag().equals("new_vehicle"))
			return null;
		
		return new NewBikeEvent(
				EventBuilder.parseNonNegInt(section, "time", 0),
				EventBuilder.validId(section, "id"),
				EventBuilder.validIds(section, "itinerary"),
				EventBuilder.parsePosInt(section, "max_speed", 1),
				EventBuilder.validId(section, "type")								
				);
	}

}
