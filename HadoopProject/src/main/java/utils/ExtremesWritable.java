package utils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class ExtremesWritable implements Writable{
	private DoubleWritable _min;
	private DoubleWritable _max;
	private DoubleWritable _average;
	private Text _minDate;
	private Text _maxDate;
	
	public ExtremesWritable() {
		_min=new DoubleWritable();
		_max=new DoubleWritable();
		_average=new DoubleWritable();
		_minDate=new Text();
		_maxDate=new Text();
	}
	
	public void set(double min, double max, double avg, String minD, String maxD) {
		_min.set(min);
		_max.set(max);
		_average.set(avg);
		_minDate.set(minD);
		_maxDate.set(maxD);
	}

	public void readFields(DataInput dataInput) throws IOException {
		_min.readFields(dataInput);
		_minDate.readFields(dataInput);
		_max.readFields(dataInput);
		_maxDate.readFields(dataInput);
		_average.readFields(dataInput);
	}

	public void write(DataOutput dataOutput) throws IOException {
		_min.write(dataOutput);
		_minDate.write(dataOutput);
		_max.write(dataOutput);
		_maxDate.write(dataOutput);
		_average.write(dataOutput);	
	}
	
	public String toString() {
		return _min.toString()+"\t"+_minDate.toString()+","+_max.toString()+"\t"+_maxDate.toString()+","+_average.toString();
	}

}
