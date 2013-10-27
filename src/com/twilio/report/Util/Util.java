package com.twilio.report.Util;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.twilio.report.Calls.CallUsageReport;
import com.twilio.report.SMS.SMSUsageReport;

/**
 * Util class to provide helper methods
 * 
 * @author ktoraskar
 * 
 */

public class Util {

	/**
	 * This method will lookup the country for a given phone number prefix
	 * 
	 * @param: phonenumber
	 * 
	 * @param: countrylookup hashmap
	 * 
	 */

	public static String getCountryforthisPhnNumber(String phonenumber,
			HashMap<String, String> countrylookup) {

		String countryKey = null;

		for (Map.Entry<String, String> entry : countrylookup.entrySet()) {
			String prefixSet = entry.getValue();
			// parse the prefixSet
			// loop over the parsed set, then match the prefix
			String prefixes[] = prefixSet.split(";");
			countryKey = entry.getKey();
			List prefixList = new ArrayList();
			for (int i = 0; i < prefixes.length; i++)
				prefixList.add(prefixes[i]);

			if (prefixList.contains(phonenumber)) {
				// System.out.println("Prefix Found");
				break;
			} else
				countryKey = null;
		}

		return countryKey;

	}

	/**
	 * This method convert the call duration (in secs) to minutes and rounds off
	 * to the nearest whole minute
	 * 
	 * @param callduration
	 *            in seconds
	 * 
	 * @return callduration in minutes
	 * 
	 */
	public static String convertCallDurationToMins(String calldurationSecs) {

		String calldurationDefault = "0";
		// System.out.println("callDurationSecsSTR:  "+calldurationSecs);
		if (calldurationSecs == null) {
			// System.out.println("Call duration is null");
			return calldurationDefault;
		}
		double calldurationSecsInt = Integer.valueOf(calldurationSecs);
		double calldurationMinsInt = calldurationSecsInt / 60.0;

		// round off to nearest minute
		calldurationMinsInt = Math.ceil(calldurationSecsInt / 60.0);
		// System.out.println("calldurationMinsInt: After Rounding "+calldurationMinsInt);
		return String.valueOf(calldurationMinsInt);

	}

	/**
	 * This method generates the report in a csv file at the location specified
	 * 
	 * @param countrywisecallreport
	 *            hashmap
	 * 
	 * @return none
	 */

	public static void generateCsvFile(
			HashMap<String, Object> countrywisecallreport,
			String csvReportFileName) {
		// String csvReportFileName =
		// "/Users/ktoraskar/Documents/DevLibs/PricingSheet/Report.csv";
//		try {
//			FileWriter writer = new FileWriter(csvReportFileName);
//			writer.append("Country");
//			writer.append(',');
//			writer.append("Total $ Spend");
//			writer.append(',');
//			writer.append("Total Usage");
//			writer.append(',');
//			writer.append("Average Rate/Min");
//			writer.append('\n');
//
//			for (Map.Entry<String, Object> entry : countrywisecallreport
//					.entrySet()) {
//				String country = entry.getKey();
//				CallUsageReport curObj = (CallUsageReport) entry.getValue();
//				String totalSpend = curObj.getPrice();
//				String totalUsage = curObj.getUsage();
//				String averagepermin = curObj.getAveragePerMin();
//				writer.append(country);
//				writer.append(',');
//				writer.append(totalSpend);
//				writer.append(',');
//				writer.append(totalUsage);
//				writer.append(',');
//				writer.append(averagepermin);
//				writer.append('\n');
//			}
//			writer.flush();
//			writer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * This method generates the report in a csv file at the location specified
	 * 
	 * @param countrywisecallreport
	 *            hashmap
	 * 
	 * @return none
	 */

	public static void generateSMSCsvReportFile(
			HashMap<String, Object> countrywiseSMSReport,
			String csvReportFileName) {
//		try {
//			FileWriter writer = new FileWriter(csvReportFileName);
//			writer.append("Country");
//			writer.append(',');
//			writer.append("Total $ Spend");
//			writer.append(',');
//			writer.append("Total Usage");
//			writer.append(',');
//			writer.append("Average Rate/Min");
//			writer.append('\n');
//
//			for (Map.Entry<String, Object> entry : countrywiseSMSReport
//					.entrySet()) {
//				String country = entry.getKey();
//				SMSUsageReport smsObj = (SMSUsageReport) entry.getValue();
//				String totalSpend = smsObj.getPrice();
//				int totalUsage = smsObj.getUsage();
//				String averagepermin = smsObj.getAverageRateperMin();
//				writer.append(country);
//				writer.append(',');
//				writer.append(totalSpend);
//				writer.append(',');
//				writer.append(String.valueOf(totalUsage));
//				writer.append(',');
//				writer.append(averagepermin);
//				writer.append('\n');
//			}
//			writer.flush();
//			writer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * 
	 * @param args
	 * @return
	 */

	public static ArrayList<String> parseInputParams(String args[]) {

		ArrayList<String> argumentList = new ArrayList<String>();
		int argCount = 0;
		while (argCount < args.length) {
			argumentList.add(args[argCount]);
			argCount++;
		}
		return argumentList;
	}

	/**
	 * 
	 * @param reportEndDate
	 * @return
	 */

	public static Date generateCutOffDate(String reportEndDate) {
		Date cutOffDate = new Date();
		return cutOffDate;
	}

	public static String formatPhoneNumber(String phoneNumber) {

		String formattedPhoneNumber = null;
		// remove the '+' from the phone number
		if (phoneNumber != null && !phoneNumber.isEmpty()
				&& phoneNumber.length() > 5)
			formattedPhoneNumber = phoneNumber.substring(1,
					phoneNumber.length());
		return formattedPhoneNumber;
	}

	public static boolean checkShortCode(String toNumber, String fromNumber) {

		boolean fromNumberisShortCode = false;
		if (fromNumber.length() == 5)
			fromNumberisShortCode = true;

		return fromNumberisShortCode;
	}

	/**
	 * 
	 * @param toNumber
	 * @param fromNumber
	 * @param countryLookupMap
	 * @return
	 */

	public static String getCountryforthisPhnNumber(String toNumber,
			String fromNumber, HashMap<String, String> countryLookupMap) {

		boolean countryNotFound = true;
		String country = null;
		String phoneNumberShort = null;
		// reverse lookup the country
		while (countryNotFound) {
			if (toNumber.contains("client") || fromNumber.contains("client")) {
				country = Constants.TWILIO_CLIENT;
				countryNotFound = false;
			} else {
				country = Util.getCountryforthisPhnNumber(toNumber,
						countryLookupMap);
				if (country != null)
					countryNotFound = false;

				if (toNumber.length() == 1 && countryNotFound == true) {
					// System.out.println("Phn No: "+phonenumber+ " Junk ");
					country = Constants.TWILIO_JUNK_DIAL;
					countryNotFound = false;
				}

				// shorten phonenumber to get the prefix
				if (toNumber.length() > 1) {
					phoneNumberShort = toNumber.substring(0,
							toNumber.length() - 1);
					toNumber = phoneNumberShort;
				}
			}
		}
		return country;
	}

	/**
	 * 
	 * @param reportStartDateStr
	 * @param reportEndDateStr
	 * @return
	 */

	public static ArrayList<String> generateReportDateChunkList(
			String reportStartDateStr, String reportEndDateStr) {
		System.out.println("Start Date Chunk: " + reportStartDateStr
				+ " EndDate: " + reportEndDateStr);
		ArrayList<String> reportDateChunkList = new ArrayList<String>();
		try {
			Calendar intervalDateCal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					Constants.TWILIO_SIMPLE_DATE_FORMAT);
			intervalDateCal.setTime(dateFormat.parse(reportStartDateStr));
			Date intervalDate = dateFormat.parse(reportStartDateStr);
			Date reportEndDate = dateFormat.parse(reportEndDateStr);
			String intervalDateStr = intervalDate.toString();
			intervalDateStr = dateFormat.format(intervalDateCal.getTime());
			reportDateChunkList.add(intervalDateStr);
			while (intervalDate.compareTo(reportEndDate) <= 0) {
				intervalDateCal.add(Calendar.DATE, 1);
				intervalDateStr = dateFormat.format(intervalDateCal.getTime());
				reportDateChunkList.add(intervalDateStr);
				intervalDate = dateFormat.parse(intervalDateStr);
			}
		} catch (ParseException e) {
			System.out.println("Date Parse Exception");
		}
		return reportDateChunkList;
	}

	/**
	 * This method is for testing only Printing the hashmap key,value pairs
	 * 
	 * @param countrywisecallreport
	 */

	public static void printHashMap(
			HashMap<String, Object> countrywisecallreport) {

		Iterator entries = countrywisecallreport.entrySet().iterator();
		while (entries.hasNext()) {

			Map.Entry entry = (Map.Entry) entries.next();
			String key = (String) entry.getKey();
			CallUsageReport val = (CallUsageReport) entry.getValue();

			System.out.println("Key " + key);
			System.out.println("Usage: " + val.getUsage() + " Price:  "
					+ val.getPrice());
		}

	}
}
