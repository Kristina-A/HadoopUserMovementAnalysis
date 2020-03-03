package utils;

import java.io.*;
import java.util.ArrayList;

public class LabelsMetadata {
	public ArrayList<TransportationMode> transports;
	private ArrayList<String> types;//which types of transport are of interest
	
	public LabelsMetadata(ArrayList<String> t) {
		transports=new ArrayList<TransportationMode>();
		types=t;
	}

	public void populate(File file) throws IOException, NumberFormatException{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		
		try {
			while ((line = reader.readLine()) != null) {
				if(!line.contains("Start")){
					String[] data = line.split("\t");
					String mode=data[2];
					
					//process only listed modes
					if(types.contains(mode)){
					
						String[] dateStart=data[0].split(" ");
						String[] dateFinish=data[1].split(" ");
						
						String dTimeStart=DateTimeHandler.parseDateTime(dateStart[0].replaceAll("/", "-"),dateStart[1]);
						String dTimeFinish=DateTimeHandler.parseDateTime(dateFinish[0].replaceAll("/", "-"),dateFinish[1]);
						
						int ind=HasMode(mode);//if mode is already added in list
						if(ind!=-1){
							transports.get(ind).AddStartEnd(dTimeStart, dTimeFinish);
						}
						else {
							transports.add(new TransportationMode(mode, dTimeStart, dTimeFinish));
						}
					}
				}
			}
		} 
		catch (NumberFormatException e) {
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				} 
				catch (IOException e) {
				}
			}
		}
	}
	
	public int HasMode(String mode) {
		for(int i=0;i<transports.size();i++){
			if(transports.get(i).mode.equals(mode))
				return i;
		}
		return -1;
	}
}
