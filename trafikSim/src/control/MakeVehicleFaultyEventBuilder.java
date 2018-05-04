package control;

import ini.IniSection;
import model.Event;
import model.MakeVehicleFaulty;

public class MakeVehicleFaultyEventBuilder extends EventBuilder {

	@Override
	public Event parse(IniSection section) {
		if (!section.getTag().equals("make_vehicle_faulty"))
			return null;	
		
		return new MakeVehicleFaulty(
				EventBuilder.parseNonNegInt(section, "time", 0), 
				EventBuilder.validIds(section, "vehicles"),			
				EventBuilder.parsePosInt(section, "duration", 1));
	}		

	
public String template() {
		
		return "[make_vehicle_faulty]" + System.getProperty("line.separator") +
				"time = " + System.getProperty("line.separator") +
				"vehicles = " + System.getProperty("line.separator") +
				"duration = " + System.getProperty("line.separator");
	}
}

