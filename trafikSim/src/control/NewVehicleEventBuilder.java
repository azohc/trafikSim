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

@Override
public String template() {
		
		return "[new_vehicle]" + System.getProperty("line.separator") +
				"time = " + System.getProperty("line.separator") +
				"id = " + System.getProperty("line.separator") +
				"itenerary = " + System.getProperty("line.separator") +
				"max_speed = " + System.getProperty("line.separator");
	}
}
