package com.twilio.report.Calls;

import com.twilio.report.Util.Constants;

public class CallUsageReport {
	
	public CallUsageReport(String country, String price, String usage) {
		// TODO Auto-generated constructor stub
		this.price = price;
		this.usage = usage;
		this.country = country;
	}
	
	public String price;
	
	public String usage;
	
	public String country;
	
	public String averagepermin;
	
	public String getPrice(){
		return price;
	}
	
	public String getUsage(){
		return usage;
	}
	
	public String getAveragePerMin(){
		return averagepermin;
	}
	
	public void setAveragePerMin(String averagepermin){
		this.averagepermin = averagepermin;
	}
	
	public String getCountry(){
		return country;
	}
	
	public void setPrice(String price){
		this.price = price;
	}
	
	public void setUsage(String usage){
		this.usage = usage;
	}
	
	public void setCountry(String country){
		this.country = country;
	}
}
