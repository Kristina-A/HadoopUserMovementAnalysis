package topNAltitude;

import java.io.File;
import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import utils.*;
import java.util.ArrayList;
import java.util.Date;

public class TopNMapper extends Mapper<LongWritable, Text, Text,DoubleWritable>{
	private Text _key=new Text();
	private DoubleWritable _value=new DoubleWritable();
	LabelsMetadata metadata;
	ArrayList<String> types;
	public static final Date dateStart=DateTimeHandler.parseDate("2008-09-01");
	public static final Date dateEnd=DateTimeHandler.parseDate("2008-09-15");
	
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
				String dateTime=DateTimeHandler.parseDateTime(array[5], array[6]);
				
				//check if it is bike
				if(metadata.transports.get(0).IsMode(dateTime) && Double.parseDouble(array[3])!=-777){
					_key.set(array[5]);
					_value.set(Double.parseDouble(array[3])*0.3048);//convert feet to m
					context.write(_key, _value);
				}
			}
		}
	}
	
	@Override
	public void setup(Context context) throws IOException,InterruptedException{
		types=new ArrayList<String>();
		types.add("bike");
		
		metadata=new LabelsMetadata(types);
		metadata.populate(new File("labels.txt"));
	}

}
