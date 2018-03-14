package control;

import ini.IniSection;
import model.Event;
import model.NewRoadEvent;

public class NewRoadEventBuilder extends EventBuilder {

	@Override
	public Event parse(IniSection section) {
		if ( !section.getTag().equals("new_road") )
			return null;	
		
		return new NewRoadEvent(
				EventBuilder.parseNonNegInt(section, "time", 0), 
				EventBuilder.validId(section, "id"),			
				EventBuilder.parseNonNegInt(section, "length", 0),
				EventBuilder.parseNonNegInt(section, "max_speed", 0),
				EventBuilder.validId(section, "src"),
				EventBuilder.validId(section, "dest"));
	}

}
//public NewRoadEvent(int time, String id, int length, int maxSpeed, String start, String end) {
