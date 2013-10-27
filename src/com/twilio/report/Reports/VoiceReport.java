package com.twilio.report.Reports;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.twilio.report.Calls.CallUsageReport;
import com.twilio.report.Calls.CallUtil;
import com.twilio.report.Calls.Calls;
import com.twilio.report.SMS.SMSUsageReport;
import com.twilio.report.SMS.SMSUtil;
import com.twilio.report.Util.Constants;
import com.twilio.report.Util.LoadPricingCSV;
import com.twilio.report.Util.Util;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.instance.Call;
import com.twilio.sdk.resource.list.CallList;
import com.twilio.sdk.resource.list.SmsList;

public class VoiceReport {

	private Date cutOffDateStr = null;
	private HashMap<String, String> countryLookupMap = new HashMap<String, String>();
	private HashMap<String, Object> countryCallReport = new HashMap<String, Object>();
	int reportCount = 0;

	/**
	 * This method will the report for the given startdate and enddate filter
	 * params
	 * 
	 * @param client
	 * @param reportStartDate
	 * @param reportEndDate
	 * @param pricingFilePath
	 * @param reportLocationPath
	 */

	public void runVoiceReport(TwilioRestClient client, String reportStartDate,
			String reportEndDate, String pricingFilePath,
			String reportLocationPath) {

		// needed to cover the hours from 17:00 PDT on the last day to 00:00 PDT
		// @todo
		cutOffDateStr = Util.generateCutOffDate(reportEndDate);

		// get date chunk list
		ArrayList<String> reportDateChunkList = Util
				.generateReportDateChunkList(reportStartDate, reportEndDate);

		// load up the pricing sheet for "Country":"Prefix" mapping
		countryLookupMap = LoadPricingCSV.loadcountrylookupcsv(pricingFilePath);

		// filter params to make a call to REST API
		Map<String, String> params = new HashMap<String, String>();
		params.put("Status", Constants.TWILIO_API_FILTER_STATUS);

		// iterate through the complete list of date intervals
		while (reportCount < reportDateChunkList.size()) {

			params.put("DateSent>", reportDateChunkList.get(reportCount));
			int startTimeLessThanCount = reportCount + 1;
			params.put("DateSent<",
					reportDateChunkList.get(startTimeLessThanCount));
			System.out.println("DateSent> "+ reportDateChunkList.get(reportCount)+ " DateSent< "+reportDateChunkList.get(reportCount+1));
			// API call to get the list of sms's for this date range
			CallList callsForDateChunk = Calls.getCallList(client, params);
			generateReport(callsForDateChunk);
			reportCount++;
			if (reportCount + 1 >= reportDateChunkList.size())
				break;
		}
		Util.generateCsvFile(countryCallReport, reportLocationPath);
	}

	/**
	 * This method will generate the usage report for the given call list
	 * 
	 * @param callsForDateChunk
	 */

	public void generateReport(CallList callsForDateChunk) {

		// iterate through this calllist
		Iterator<Call> callsForDateChunkItr = callsForDateChunk.iterator();
		String callPrice = null;
		String callUsage = null;
		String toNumber = null;
		String fromNumber = null;
		String country = null;

		while (callsForDateChunkItr.hasNext()) {

			Call call = callsForDateChunkItr.next();
			toNumber = call.getTo();
			fromNumber = call.getFrom();

			System.out.println("toNumber: "+toNumber+ " fromNumber: "+fromNumber+ " sid: "+call.getSid());
			
			if (call.getDateCreated().before(cutOffDateStr)) {

				// get price, usage, handle 0 price, usage or null price, usage
				callPrice = call.getPrice();
				if (callPrice == null || callPrice.isEmpty())
					callPrice = Constants.ZERO;

				callUsage = Util.convertCallDurationToMins(call.getDuration());

				// cleanup phonenumber
				toNumber = Util.formatPhoneNumber(toNumber);

				// lookup country for this phonenumber
				country = Util.getCountryforthisPhnNumber(toNumber, fromNumber,
						countryLookupMap);

				// get sms direction
				String callDirection = call.getDirection();
				StringBuffer sb = new StringBuffer();
				sb.append(country);
				sb.append("-");
				sb.append(callDirection);
				country = sb.toString();

				System.out.println("Country: " + country + " PhoneNumber: "
						+ toNumber + "Sid: " + call.getSid());

				if (countryCallReport.get(country) != null) {
					if (country.equalsIgnoreCase(Constants.UNITED_STATES))
						country = CallUtil.handleInCorrectUSNumbers(call,
								countryLookupMap);

					updateCallReportHM(call, callPrice, callUsage, country, countryLookupMap);
				} else {
					CallUsageReport callUR = new CallUsageReport(country, callPrice, callUsage);
					countryCallReport.put(country, callUR);
				}
				
				
				if (country.equalsIgnoreCase(Constants.UNITED_STATES))
					country = CallUtil.handleInCorrectUSNumbers(call,
							countryLookupMap);
			}
		}
	}

	/**
	 * This method will update the countryCallReport
	 * 
	 * @param call
	 * @param callPrice
	 * @param callUsage
	 * @param country
	 * @param countryLookupMap
	 */

	public void updateCallReportHM(Call call, String callPrice,
			String callUsage, String country,
			HashMap<String, String> countryLookupMap) {
		
		if (!country.equalsIgnoreCase(Constants.TWILIO_JUNK_DIAL)) {
			if (countryCallReport.get(country) != null) {
				Object callObjInc = countryCallReport.get(country);
				CallUsageReport curObj = (CallUsageReport) callObjInc;

				BigDecimal priceBD = new BigDecimal(curObj.getPrice());
				BigDecimal usageBD = new BigDecimal(curObj.getUsage());

				if (!(callUsage.equalsIgnoreCase(Constants.ZERO_DECIMAL))
						|| !(callUsage.equalsIgnoreCase(Constants.ZERO))
						|| !(callPrice.equalsIgnoreCase(Constants.ZERO)))

				{
					BigDecimal priceforthiscallBD = new BigDecimal(callPrice);
					BigDecimal usageforthiscallBD = new BigDecimal(callUsage);
					BigDecimal resultpriceBD = priceBD.add(priceforthiscallBD);
					BigDecimal resultusageBD = usageBD.add(usageforthiscallBD);

					curObj.setPrice(String.valueOf(resultpriceBD));
					curObj.setUsage(String.valueOf(resultusageBD));
					curObj.setCountry(country);
					
					if (!(resultpriceBD == BigDecimal.ZERO)
							&& !(resultusageBD == BigDecimal.ZERO)) {
						BigDecimal averagerateperCallBD = resultpriceBD.divide(
								resultusageBD, 4);
						curObj.setAveragePerMin(String
								.valueOf(averagerateperCallBD));
					}

					countryCallReport.put(country, curObj);
				}
			}
		}
	}
}
