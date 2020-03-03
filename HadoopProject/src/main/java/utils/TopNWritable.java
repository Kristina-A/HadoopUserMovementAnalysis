package utils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class TopNWritable implements Writable{
	private ArrayList<Text> dates;
	private ArrayList<DoubleWritable> altitudes;
	
	public TopNWritable() {
		dates=new ArrayList<Text>();
		altitudes=new ArrayList<DoubleWritable>();
	}
	
	public void set(String alt, String dat) {
		String[] arrayDates=dat.split(",");
		String[] arrayAltitudes=alt.split(",");
		
		for(int i=0;i<arrayDates.length;i++){
			dates.add(new Text(arrayDates[i]));
			altitudes.add(new DoubleWritable(Double.parseDouble(arrayAltitudes[i])));
		}
	}

	public void readFields(DataInput dataInput) throws IOException {
		dates=new ArrayList<Text>();
		altitudes=new ArrayList<DoubleWritable>();
		int size=dataInput.readInt();
		DoubleWritable alt=new DoubleWritable();
		Text dat=new Text();
		
		for(int i=0;i<size;i++){
			alt.readFields(dataInput);
			dat.readFields(dataInput);
			altitudes.add(alt);
			dates.add(dat);
		}
	}

	public void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeInt(altitudes.size());
		
		for(int i=0;i<altitudes.size();i++){
			altitudes.get(i).write(dataOutput);
			dates.get(i).write(dataOutput);
		}
	}

	public String toString() {
		String s="\n";
		
		for(int i=0;i<dates.size();i++){
			s+=altitudes.get(i).toString()+"\t"+dates.get(i).toString()+"\n";
		}
		
		return s;
	}
}
