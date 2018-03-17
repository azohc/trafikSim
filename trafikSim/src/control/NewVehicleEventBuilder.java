package control;

import model.Event;
import model.NewVehicleEvent;
import ini.IniSection;


public class NewVehicleEventBuilder extends EventBuilder {

	@Override
	public Event parse(IniSection section) {
		if (!section.getTag().equals("new_vehicle") || section.getValue("type") != null)
			return null;	
		

		return new NewVehicleEvent(
				EventBuilder.parseNonNegInt(section, "time", 0), 
				EventBuilder.validId(section, "id"),			
				EventBuilder.parsePosInt(section, "max_speed", 1),
				EventBuilder.validIds(section, "itinerary"));
	}		


}
