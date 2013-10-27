package com.twilio.report.SMS;

import java.math.BigDecimal;

/**
 * This class provides util methods
 * for generating the SMS Report
 * 
 * @author ktoraskar
 *
 */

public class SMSUsageReport {
	
	public String price;
	public int usage;
	public String country;
	public String averageRateperMin;
		
	/**
	 * Constructor to set SMSUsageReport
	 * 
	 * @param country
	 * @param price
	 */
	
	public SMSUsageReport(String country, String price) {
		// TODO Auto-generated constructor stub
		this.price = price;
		this.country = country;
		incrementSMSCount();
		setAverageRateperMin();
	}

	/**
	 * 
	 * @return price
	 */
	
	public String getPrice(){
		return price;
	}
	
	/**
	 * 
	 * @return usage
	 */
	
	public int getUsage(){
		return usage;
	}
	
	/**
	 * 
	 * @return country
	 */
	
	public String getCountry(){
		return country;
	}
	
	/**
	 * Set the price
	 * 
	 * @param price
	 */
	
	public void setPrice(String price){
		this.price = price;
	}
	
	/**
	 * Set the usage
	 * 
	 * @param usage
	 */
	
	public void setUsage(int usage){
		this.usage = usage;
	}
	
	/**
	 * Set the country
	 * 
	 * @param country
	 */
	
	public void setCountry(String country){
		this.country = country;
	}
	
	/**
	 * This method calculates the averageRateperMin
	 * 
	 */
	
	public void setAverageRateperMin(){
		float priceBD = new Float(price);
		float usageBD = (float)usage;
		float resultFlt = priceBD/usageBD;
		BigDecimal resultBD = new BigDecimal(Float.toString(resultFlt));
		resultBD = resultBD.setScale(2, BigDecimal.ROUND_UP);
		this.averageRateperMin = String.valueOf(resultBD);			
	}
	
	/**
	 * Returns the averageRateperMin
	 * 
	 * @return
	 */
	
	public String getAverageRateperMin(){
		return averageRateperMin;
	}
	
	/**
	 * Increment the SMS Count
	 * Set it back on the SMSUsageReport
	 */
	
	public void incrementSMSCount(){
		int currentUsage = usage;
		currentUsage++;
		setUsage(currentUsage);
	}	
}
