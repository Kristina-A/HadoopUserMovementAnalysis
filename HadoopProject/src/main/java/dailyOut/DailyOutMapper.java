package dailyOut;

import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class DailyOutMapper extends Mapper<LongWritable, Text, Text,Text> {
	private Text _key=new Text();
	private Text _value=new Text();

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line=value.toString();
		if(line.contains("trajectory") || line.contains("WGS") || line.contains("Altitude") ||
				line.contains("Reserved") || line.contains("My") || line.equals("0"))//skip first six lines
			return;
		else {
			String[] array=line.split(",");
			
			_key.set(array[5]);
			_value.set(array[6]);
			
			context.write(_key, _value);
		}
	}

}
