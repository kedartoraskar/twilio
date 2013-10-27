package com.twilio.report.Util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoadPricingCSV {
	
	/**
	 * This method will load the pricing csv 
	 * into a usable hashmap<country, countryprefixes>
	 * example: <United States, 1;1801;1901> 
	 * The country prefixes are seperated by ";"
	 * 
	 * @return countrywiselookup hashmap
	 *         
	 */
	
	public static HashMap<String, String> loadcountrylookupcsv(String csvFile) {
		  
	// lookup in the csv file
	//String csvFile = "/Users/ktoraskar/Documents/DevLibs/PricingSheet/twilio-international-voice-rates.csv";
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
	HashMap<String, String> countrymap = new HashMap<String, String>();
		 
	try {
		br = new BufferedReader(new FileReader(csvFile));
		while ((line = br.readLine()) != null) {

			String[] country = line.split(cvsSplitBy);
			String countryname = country[0];
			String countryprefixes = country[1];
			
			if(countrymap.containsKey(countryname))
			{
				String prefixes = countrymap.get(countryname);
				StringBuffer sb = new StringBuffer();
				sb.append(countryprefixes);
				sb.append(";");  //seperator between prefixes
				sb.append(prefixes);
				countryprefixes = sb.toString();
				countrymap.remove(countryname);
			}
			countrymap.put(countryname, countryprefixes);
		}
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	return countrymap;
  }   

}
