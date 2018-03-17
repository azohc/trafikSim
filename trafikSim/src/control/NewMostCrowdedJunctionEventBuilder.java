package control;

import ini.IniSection;
import model.Event;
import model.NewMostCrowdedJunctionEvent;

public class NewMostCrowdedJunctionEventBuilder extends EventBuilder {

	@Override
	public Event parse(IniSection section) {
		if(!section.getTag().equals("new_junction"))
			return null;
		
		if(section.getKeys().contains("type") && section.getValue("type").equals("mc"))
			return new NewMostCrowdedJunctionEvent(
					EventBuilder.parseNonNegInt(section, "time", 0), 
					EventBuilder.validId(section, "id"),
					EventBuilder.validId(section, "type")
					);	
		else return null;
	}

}
