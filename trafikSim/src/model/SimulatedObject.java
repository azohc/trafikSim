package model;

import ini.IniSection;

public abstract class SimulatedObject {
		
	private String _id;	//unique identifier for the object
		
		public SimulatedObject(String s)
		{
			_id = s;
		}
		
		public String getId() 
		{
			return _id;
	
		}
		
		public String toString()
		{
			//TD
			return _id;
		}
		
		public String generateReport(int time)
		{
			IniSection is = new IniSection(getReportSectionTag());
			is.setValue("id", _id);
			is.setValue("time",time);
			fillReportDetails(is);
		
			
			return is.toString();
		}
		
		protected abstract void fillReportDetails(IniSection i);
		
		protected abstract String getReportSectionTag();
		
		abstract void advance();
}
