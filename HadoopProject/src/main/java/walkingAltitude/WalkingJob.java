package walkingAltitude;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import utils.ExtremesWritable;

public class WalkingJob extends Configured implements Tool{

	public int run(String[] args) throws Exception {
		Job job = new Job(getConf());
		job.setJarByClass(getClass());
		job.setJobName(getClass().getSimpleName());
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(DoubleWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(ExtremesWritable.class);
		
		job.setMapperClass(WalkingMapper.class);
		job.setReducerClass(WalkingReducer.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
				
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		return job.waitForCompletion(true)? 0:1;
	}
	
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new WalkingJob(), args);
		System.exit(res);
	}

}
