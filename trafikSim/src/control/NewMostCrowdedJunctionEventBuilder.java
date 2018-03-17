package control;

import ini.IniSection;
import model.Event;
import model.NewMostCrowdedJunctionEvent;

public class NewMostCrowdedJunctionEventBuilder extends EventBuilder {

	@Override
	public Event parse(IniSection section) {
		if( !section.getTag().equals("new_junction"))
			return null;
		
		return new NewMostCrowdedJunctionEvent(
				EventBuilder.parseNonNegInt(section, "time", 0), 
				EventBuilder.validId(section, "id"),
				EventBuilder.validId(section, "type")
				);	
			
	}

}
