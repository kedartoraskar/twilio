package com.twilio.report.Calls;

import java.util.HashMap;

import com.twilio.report.Util.Constants;
import com.twilio.report.Util.Util;
import com.twilio.sdk.resource.instance.Call;

public class CallUtil {
	
	public static String handleInCorrectUSNumbers(Call call, HashMap<String, String> countryLookupMap) {
	
		String country = null;
		// get call direction
		//System.out.println("\nTESTING for United States Outbound");
		String countryTst = Constants.UNITED_STATES;
	    String calldirection = call.getDirection();
	    String calldurationTst = call.getDuration();
	    StringBuffer sb = new StringBuffer();
	    sb.append(countryTst);
	    sb.append("-");
	    sb.append(calldirection);
	    countryTst = sb.toString();
	   
		String priceforthiscall = call.getPrice();
		String priceforthiscallTest = priceforthiscall.replace('-',' ');
		priceforthiscallTest.trim();
		String calldurationinMinsTest = Util.convertCallDurationToMins(calldurationTst);
		String callsid = call.getSid();
		
		// get the price per minute for the calls
		
		double priceTest = Double.parseDouble(priceforthiscallTest);
		double usageTest = Double.parseDouble(calldurationinMinsTest);
    	
		double averagerateperCallTest = priceTest/usageTest;
		 //System.out.println("\nPrice on this call: "+priceTest+ " usageTest: "+usageTest);
	   // System.out.println("AverageCallRate Test: "+averagerateperCallTest);

		double twocentcall = 0.02;
		int res = Double.compare(averagerateperCallTest, twocentcall);
		//System.out.println("Phonenumber:  "+call.getTo());
	    //System.out.println("ToNumber Country Updated: "+countryTst);

		if(res > 0) {
			System.out.println("US: price: "+priceTest+ " Call SID: "+callsid);
			System.out.println("****US  > 2c: "+priceTest);	
			String alaskaNoTest = call.getTo();
			//remove the '+' from the phone number
	    	if(alaskaNoTest != null && !alaskaNoTest.isEmpty())
	    		alaskaNoTest = alaskaNoTest.substring(1, alaskaNoTest.length()); 
				alaskaNoTest.trim();
			String alaskaNoTestSub = alaskaNoTest.substring(0,4);
			System.out.println("AlaskaTest: "+alaskaNoTestSub);
			if(!alaskaNoTestSub.equalsIgnoreCase(Constants.TWILIO_ALASKA_PREFIX))
			{	System.out.println("This is a non US number -- Will be rerun");
			    String resubmitNumber = call.getTo().substring(2,call.getTo().length());
			    System.out.println("Number for resubmit: "+resubmitNumber);
				country = Util.getCountryforthisPhnNumber(resubmitNumber, countryLookupMap);
			}
			
		}
		else if(res < 0){
			if(!calldirection.contains("inbound")){
			System.out.println("US: price: "+priceTest+ " Call SID: "+callsid);
			System.out.println("####US  < 2c: "+priceTest);
			}
		}
	
		return country;
	}

}
