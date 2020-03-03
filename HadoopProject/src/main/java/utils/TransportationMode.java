package utils;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class TransportationMode {
	public ArrayList<String> starts;
	public ArrayList<String> ends;
	public String mode;
	
	public TransportationMode(String m, String s, String e) {
		starts=new ArrayList<String>();
		ends=new ArrayList<String>();
		mode=m;
		starts.add(s);
		ends.add(e);
	}
	
	public void AddStartEnd(String s, String e) {
		starts.add(s);
		ends.add(e);
	}
	
	public boolean IsMode(String dt) {
		for(int i=0;i<starts.size();i++){
			String[] dtime1=dt.split(" ");
			String[] dtime2=starts.get(i).split(" ");
			String[] dtime3=ends.get(i).split(" ");
			
			Date date1=DateTimeHandler.parseDate(dtime1[0]);
			Time time1=DateTimeHandler.parseTime(dtime1[1]);
			
			Date date2=DateTimeHandler.parseDate(dtime2[0]);
			Time time2=DateTimeHandler.parseTime(dtime2[1]);
			
			Date date3=DateTimeHandler.parseDate(dtime3[0]);
			Time time3=DateTimeHandler.parseTime(dtime3[1]);
			
			if(date1.compareTo(date2)>=0 && time1.compareTo(time2)>=0 && date1.compareTo(date3)<=0 && time1.compareTo(time3)<=0)
				return true;
		}
		return false;
	}
}
