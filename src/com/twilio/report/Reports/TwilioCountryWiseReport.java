package com.twilio.report.Reports;

import java.util.HashMap;

import com.twilio.report.RestClient.TwilioRestClientFactory;
import com.twilio.report.Util.ReportException;
import com.twilio.report.Util.Constants;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;

/**
 * Report generation class Based on the environment parameters set, the SMS or
 * Voice report is generated
 * 
 * Environment Variables:
 * 
 * TWILIO_REPORT_TYPE : "SMS" or "VOICE" TWILIO_SE_ACCOUNT_SID : Users Account
 * SID TWILIO_SE_AUTH_TOKEN : Users Auth Token TWILIO_CUSTOMER_ACCOUNT_SID :
 * Customers Account SID PRICING_FILE_PATH : Path to the pricing file
 * REPORT_LOCATION_PATH : Path to report generation location
 * TWILIO_REPORT_START_DATE : Start date "2013-09-01" TWILIO_REPORT_END_DATE :
 * End date "2013-09-30"
 * 
 * 
 * @author ktoraskar
 * 
 */

public class TwilioCountryWiseReport {

	public static String TWILIO_SE_ACCOUNT_SID;
	public static String TWILIO_SE_AUTH_TOKEN;
	public static String TWILIO_CUSTOMER_ACCOUNT_SID;
	public static String TWILIO_VOICE_REPORT_LOCATION;
	public static String TWILIO_SMS_REPORT_LOCATION;
	public static String TWILIO_REPORT_START_DATE;
	public static String TWILIO_REPORT_END_DATE;
	public static String TWILIO_REPORT_TYPE;
	public static String PRICING_FILE_PATH;
	public static String REPORT_LOCATION_PATH;

//	/**
//	 * Main method
//	 * 
//	 * @param args
//	 * @throws TwilioRestException
//	 * @throws ReportException
//	 */
//	public static void main(String[] args) throws TwilioRestException,
//			ReportException {
//		initializeArguments();
//	}

	/**
	 * 
	 * This method will initialize the variables required for report generation
	 * from the argument list
	 * 
	 * @param argumentList
	 */
	public static void initializeArguments(HashMap<String,String> argumentMap) {

		TWILIO_REPORT_TYPE = argumentMap.get("TWILIO_REPORT_TYPE");
		TWILIO_SE_ACCOUNT_SID = argumentMap.get("TWILIO_ACCOUNT_SID");
		TWILIO_SE_AUTH_TOKEN = argumentMap.get("TWILIO_AUTH_TOKEN");
		TWILIO_CUSTOMER_ACCOUNT_SID = argumentMap.get("TWILIO_CUSTOMER_ACCOUNT_SID");
		PRICING_FILE_PATH = argumentMap.get("PRICING_FILE_PATH");
		REPORT_LOCATION_PATH = argumentMap.get("REPORT_LOCATION_PATH");
		TWILIO_REPORT_START_DATE = argumentMap.get("TWILIO_REPORT_START_DATE");
		TWILIO_REPORT_END_DATE = argumentMap.get("TWILIO_REPORT_END_DATE");

		System.out.println("Generating report for Customer SID: "
				+ TWILIO_CUSTOMER_ACCOUNT_SID);

		// create Twilio Client
		TwilioRestClient client = TwilioRestClientFactory
				.createTwilioRestClient(TWILIO_SE_ACCOUNT_SID,
						TWILIO_SE_AUTH_TOKEN, TWILIO_CUSTOMER_ACCOUNT_SID);

		if (TWILIO_REPORT_TYPE.equalsIgnoreCase(Constants.TWILIO_VOICE_REPORT)) {
			VoiceReport voiceReport = new VoiceReport();
			voiceReport.runVoiceReport(client, TWILIO_REPORT_START_DATE,
					TWILIO_REPORT_END_DATE, PRICING_FILE_PATH,
					REPORT_LOCATION_PATH);

		} else if (TWILIO_REPORT_TYPE
				.equalsIgnoreCase(Constants.TWILIO_SMS_REPORT)) {
			SMSReport smsReport = new SMSReport();
			smsReport.runSMSReport(client, TWILIO_REPORT_START_DATE,
					TWILIO_REPORT_END_DATE, PRICING_FILE_PATH,
					REPORT_LOCATION_PATH);
		}
	}
}
