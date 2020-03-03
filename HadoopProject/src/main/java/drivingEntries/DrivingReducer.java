package drivingEntries;

import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class DrivingReducer extends Reducer<Text,IntWritable, Text, IntWritable>{

	@Override
	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException{
		int sum = 0;
		for (IntWritable v:values) {
	         sum += v.get();
       }
		context.write(key, new IntWritable(sum));
	}
}
