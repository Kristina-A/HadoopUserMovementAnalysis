package walkingAltitude;

import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

import utils.ExtremesWritable;

public class WalkingReducer extends Reducer<Text, DoubleWritable, Text, ExtremesWritable>{
	private Text _key=new Text();
	private ExtremesWritable _value=new ExtremesWritable();
	private double min=99999;
	private String dateMin;
	private double max=-99999;
	private String dateMax;
	double sum=0;
	int count=0;
	
	@Override
	public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException{
		for(DoubleWritable d:values){
			if(d.get()>max){
				max=d.get();
				dateMax=key.toString();
			}
			
			if(d.get()<min){
				min=d.get();
				dateMin=key.toString();
			}
			
			sum+=d.get();
			count++;
		}
	}
	
	protected void cleanup(Context context) throws IOException, InterruptedException{
		_key.set("Min, Max, srednja vrednost:");
		double average=sum/count;
		_value.set(min, max, average, dateMin, dateMax);
		
		context.write(_key, _value);
	 }


}
