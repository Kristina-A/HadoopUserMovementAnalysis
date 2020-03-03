package differentLocations;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class DifferentLocationsReducer extends Reducer<Text,Text, Text, Text>{
	Text _value=new Text();
	
	@Override
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException{
		ArrayList<String> dates=new ArrayList<String>();
		for(Text t:values){
			if(!dates.contains(t.toString())){
				dates.add(t.toString());
			}
		}
		
		_value.set(dates.toString());
		
		context.write(key, _value);
	}

}
