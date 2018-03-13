package control;

import ini.IniSection;
import model.Event;

public abstract class EventBuilder {

	public EventBuilder(){
		
	}
	
	public abstract Event parse(IniSection section);
	
	public String template(){
		
		return "";
	}

	public static String validId(IniSection section, String string) {
		
		if(section.getKeysMap().containsKey(string))
				return section.getKeysMap().get(string);
		else
			return null;
	}

	public static int parseNonNegInt(IniSection section, String string, int i) {
		if(section.getKeysMap().containsKey(string))
			if(Integer.parseInt(section.getValue(string)) > 0)
				return Integer.parseInt(section.getValue(string));
			else
				return i;
		else
			return i;
	}

	public static String[] validIds(IniSection section, String string) {		//CHECK!

		if(section.getKeysMap().containsKey(string))
		{
			String aux = section.getKeysMap().get(string); //esto que value devuelve? j1,j2?
			
			return aux.split(",");				//si devuelve j1,j2 => split(",") devuelve array 0: j1, 1: j2
		}
		
		else 
			return null;
	}
}
