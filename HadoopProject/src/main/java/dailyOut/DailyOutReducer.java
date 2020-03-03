package dailyOut;

import java.io.IOException;
import java.sql.Time;
import org.apache.hadoop.io.*; 
import utils.*;
import org.apache.hadoop.mapreduce.Reducer;

public class DailyOutReducer extends Reducer<Text, Text, Text, Text> {
	private Text _value=new Text();

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		Time min=DateTimeHandler.parseTime(values.iterator().next().toString());
		Time max=min;
		
		for(Text val:values){
			Time time=DateTimeHandler.parseTime(val.toString());
			
			if(min.compareTo(time)>0)
				min=time;
			if(max.compareTo(time)<0)
				max=time;
		}
		
		long duration=max.getTime()-min.getTime();
		int seconds=(int) (duration/1000);
		int hours=(int) (seconds/3600);
		int totalminutes=(int) (seconds/60);
		int minutes=totalminutes%60;
		
		String time= Integer.toString(hours)+"h "+Integer.toString(minutes)+"min";
		_value.set(time);
		
		context.write(key, _value);
		
	}

}
