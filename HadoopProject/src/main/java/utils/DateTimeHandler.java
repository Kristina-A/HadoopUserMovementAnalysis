package utils;

import java.sql.Time;
//import java.time.*;
import java.util.*;

public class DateTimeHandler {
	public static Time parseTime(String time){
		String[] array=time.split(":");
		return new Time(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]));
	}
	
	public static Date parseDate(String date) {
		String[] array=date.split("-");
		return new Date(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]));
	}
	
	public static String parseDateTime(String d, String t) {
		return d+" "+t;
	}

}
