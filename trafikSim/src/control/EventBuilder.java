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
		if(validId(section, string) != null)
			if(Integer.parseInt(section.getValue(string)) >= 0)
				return Integer.parseInt(section.getValue(string));
			else {
				System.out.println("THROW EXCEPTION");//throw exception TODO
				return 21;//throw exception TODO
			}
		else
			return i;
	}
	
	public static double parseNonNegDouble(IniSection section, String string, int i) {
		if(validId(section, string) != null)
			if(Double.parseDouble(section.getValue(string)) >= 0)
				return Double.parseDouble(section.getValue(string));
			else {
				System.out.println("THROW EXCEPTION");//throw exception TODO
				return 21;//throw exception TODO
			}
		else
			return i;
	}
	public static int parsePosInt(IniSection section, String string, int i) { 
		if(validId(section, string) != null) {
			if(Integer.parseInt(section.getValue(string)) > 0) 
				return Integer.parseInt(section.getValue(string));
			else  {
				System.out.println("THROW EXCEPTION");//throw exception TODO
				return 21;//throw exception TODO
			}
		}
		else
			return i;
	}
	
	public static long parsePosLong(IniSection section, String string, long l) {
		if(validId(section, string) != null) {
			if(Integer.parseInt(section.getValue(string)) > 0) 
				return Long.parseLong(section.getValue(string));
			else {
				System.out.println("THROW EXCEPTION");//throw exception TODO
				return 21;//throw exception TODO	
			}
		}
		else
			return l;
	}

	public static String[] validIds(IniSection section, String string) {		

		if(section.getKeysMap().containsKey(string))
			return section.getKeysMap().get(string).split(",");				
		
		else 
			return null;
	}
}
