package differentLocations;

import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import utils.*;
import java.util.Date;

public class DifferentLocationsMapper extends Mapper<LongWritable, Text, Text,Text>{
	private Text _key=new Text();
	private Text _value=new Text();
	public static final Date dateStart=DateTimeHandler.parseDate("2008-11-01");
	public static final Date dateEnd=DateTimeHandler.parseDate("2008-11-29");
	
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line=value.toString();
		if(line.contains("trajectory") || line.contains("WGS") || line.contains("Altitude") ||
				line.contains("Reserved") || line.contains("My") || line.equals("0"))//skip first six lines
			return;
		else {
			String[] array=line.split(",");
			
			//check date condition
			if(DateTimeHandler.parseDate(array[5]).compareTo(dateStart)>=0 && DateTimeHandler.parseDate(array[5]).compareTo(dateEnd)<=0){
				double coord1=Math.round(Double.parseDouble(array[0])*100.0)/100.0;
				double coord2=Math.round(Double.parseDouble(array[1])*100.0)/100.0;
				
				String k="("+String.valueOf(coord1)+","+String.valueOf(coord2)+")";
				_key.set(k);
				_value.set(array[5]);
				
				context.write(_key, _value);
			}
		}	
	}
}
