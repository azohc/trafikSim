package control;

import ini.IniSection;
import model.Event;
import model.NewJunctionEvent;

public class NewJunctionEventBuilder extends EventBuilder {

	@Override
	public Event parse(IniSection section) {
		if (!section.getTag().equals("new_junction") || section.getValue("type") != null)
			return null;	
		
		return new NewJunctionEvent(
				EventBuilder.parseNonNegInt(section, "time", 0), 
				EventBuilder.validId(section, "id"));			
	}

	
@Override
public String template() {
		
		return "[new_junction]" + System.getProperty("line.separator") +
				"time = " +
				"id = " + System.getProperty("line.separator");
	}
}
