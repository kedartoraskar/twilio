package com.twilio.report.SMS;

import java.util.HashMap;

import com.twilio.report.Util.Constants;
import com.twilio.report.Util.Util;
import com.twilio.sdk.resource.instance.Sms;

/**
 * SMSUtil class
 * 
 * @author ktoraskar
 * 
 */

public class SMSUtil {
	/**
	 * This method handles phonenumbers which are non-us, but the API returns
	 * the ToNumber with +1 For ex: 5071932993 - is a Peru Number However, the
	 * API returns the number as +15071932993 as it is treated as a US Number.
	 * This is a know bug.
	 * 
	 * @param sms
	 * @param countryLookupMap
	 * @return country (based on the correct prefix)
	 */

	public static String handleInCorrectUSNumbers(Sms sms,
			HashMap<String, String> countryLookupMap) {

		String country = Constants.UNITED_STATES;
		String smsPrice = sms.getPrice();
		String priceforthisSmsTest = smsPrice.replace('-', ' ');
		priceforthisSmsTest.trim();
		double priceTestBD = Double.parseDouble(priceforthisSmsTest);
		double oncecentsms = 0.01;
		int res = Double.compare(priceTestBD, oncecentsms);
		if (res > 0) {
			String resubmitNumber = sms.getTo().substring(2,
					sms.getTo().length());
			country = Util.getCountryforthisPhnNumber(resubmitNumber,
					countryLookupMap);
		}
		return country;
	}

}
