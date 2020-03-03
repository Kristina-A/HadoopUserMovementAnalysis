package topNAltitude;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

import utils.TopNWritable;

public class TopNReducer extends Reducer<Text, DoubleWritable, Text, TopNWritable>{
	private Text _key=new Text();
	private TopNWritable _value=new TopNWritable();
	private HashMap<Double, String> altitudes=new HashMap<Double, String>();
	private final static int n=5;
	
	@Override
	public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException{
		
		for(DoubleWritable d:values){
			//if altitude doesn't exist in map, add it
			if(!altitudes.containsKey(d.get())){
				altitudes.put(d.get(), key.toString());
			}
		}
	}
	
	protected void cleanup(Context context) throws IOException, InterruptedException{
		TreeMap<Double, String> sorted=new TreeMap<Double, String>(Collections.reverseOrder());//sort map by key in descending order
		sorted.putAll(altitudes);
		
		Set<Entry<Double, String>> set=sorted.entrySet();
		Iterator<Entry<Double, String>> iterator=set.iterator();
		
		String k="";//string of altitudes
		String v="";//string of dates
		//get first n entries
		for(int i=0;i<n;i++){
			if(iterator.hasNext()){
				Map.Entry<Double,String> mEntry=iterator.next();
				k+=mEntry.getKey().toString()+",";
				v+=mEntry.getValue().toString()+",";
			}
		}
		_key.set("Top "+ Integer.toString(n) +" slogova:");
		_value.set(k,v);
		
		context.write(_key, _value);
	 }


}
