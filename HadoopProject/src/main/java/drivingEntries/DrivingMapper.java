package drivingEntries;

import java.io.File;
import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import utils.*;
import java.util.ArrayList;
import java.util.Date;

public class DrivingMapper extends Mapper<LongWritable, Text, Text,IntWritable>{
	private Text _key=new Text();
	private final static IntWritable one = new IntWritable(1);
	LabelsMetadata metadata;
	ArrayList<String> types;
	public static final Date dateStart=DateTimeHandler.parseDate("2008-08-20");
	public static final Date dateEnd=DateTimeHandler.parseDate("2008-08-31");
	public static final double coord1 = 39.99;
	public static final double coord2 = 116.33;

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line=value.toString();
		if(line.contains("trajectory") || line.contains("WGS") || line.contains("Altitude") ||
				line.contains("Reserved") || line.contains("My") || line.equals("0"))//skip first six lines
			return;
		else {
			String[] array=line.split(",");
			String k="("+String.valueOf(coord1)+","+String.valueOf(coord2)+") "+ dateStart.toString()+" to "+dateEnd.toString();
			_key.set(k);
			
			//test the condition for time and place
			if(DateTimeHandler.parseDate(array[5]).compareTo(dateStart)>=0 && DateTimeHandler.parseDate(array[5]).compareTo(dateEnd)<=0 &&
					Math.round(Double.parseDouble(array[0])*100.0)/100.0==coord1 && Math.round(Double.parseDouble(array[1])*100.0)/100.0==coord2){
				String dateTime=DateTimeHandler.parseDateTime(array[5], array[6]);
				
				int i=0;
				
				//check if it is driving
				while (i<metadata.transports.size() && !metadata.transports.get(i).IsMode(dateTime))
					i++;
				
				if(i!=metadata.transports.size()){
					context.write(_key, one);
				}
			}
		}	
	}
	
	@Override
	public void setup(Context context) throws IOException,InterruptedException{
		types=new ArrayList<String>();
		types.add("bus");
		types.add("car");
		types.add("taxi");
		types.add("subway");
		types.add("train");
		types.add("airplane");
		types.add("boat");
		types.add("motorcycle");
		
		metadata=new LabelsMetadata(types);
		metadata.populate(new File("labels.txt"));
	}
}
