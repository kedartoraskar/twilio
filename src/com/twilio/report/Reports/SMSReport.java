package com.twilio.report.Reports;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.twilio.report.SMS.SMSUsageReport;
import com.twilio.report.SMS.SMSUtil;
import com.twilio.report.Util.Constants;
import com.twilio.report.Util.LoadPricingCSV;
import com.twilio.report.Util.Util;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.instance.Sms;
import com.twilio.sdk.resource.list.SmsList;

/**
 * SMS Report generation class
 * 
 * @author ktoraskar
 * 
 */

public class SMSReport {

	private Date cutOffDateStr = null;
	private HashMap<String, String> countryLookupMap = new HashMap<String, String>();
	private HashMap<String, Object> countrySMSReport = new HashMap<String, Object>();
	int reportCount = 0;

	/**
	 * This method will run the SMS report
	 * 
	 * @param client
	 * @param reportStartDate
	 * @param reportEndDate
	 * @param pricingFilePath
	 * @param reportLocationPath
	 */

	public void runSMSReport(TwilioRestClient client, String reportStartDate,
			String reportEndDate, String pricingFilePath,
			String reportLocationPath) {

		// needed to cover the hours from 17:00 PDT on the last day to 00:00 PDT
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

			// API call to get the list of sms's for this date range
			SmsList smsForDateChunk = client.getAccount()
					.getSmsMessages(params);
			generateReport(smsForDateChunk);
			reportCount++;
			if (reportCount + 1 >= reportDateChunkList.size())
				break;
		}
		Util.generateSMSCsvReportFile(countrySMSReport, reportLocationPath);
	}

	/**
	 * This method will generate the report for the given sms list
	 * 
	 * @param smsForDateChunk
	 */
	public void generateReport(SmsList smsForDateChunk) {

		// iterate through this smslist
		Iterator<Sms> smsForDateChunkItr = smsForDateChunk.iterator();
		String smsPrice = null;
		String toNumber = null;
		String fromNumber = null;
		String country = null;
		String phoneNumber = null;

		while (smsForDateChunkItr.hasNext()) {

			Sms sms = smsForDateChunkItr.next();
			//
			// smsPrice = null;
			// toNumber = null;
			// fromNumber = null;
			// country = null
			toNumber = sms.getTo();
			fromNumber = sms.getFrom();
			phoneNumber = toNumber;

			if (sms.getDateCreated().before(cutOffDateStr)) {

				// get price, handle 0 price, usage or null price, usage
				smsPrice = sms.getPrice();
				if (smsPrice == null || smsPrice.isEmpty())
					smsPrice = Constants.ZERO;

				// check for short code
				boolean fromNumberisShortCode = Util.checkShortCode(toNumber,
						fromNumber);
				if (!fromNumberisShortCode)
					phoneNumber = fromNumber;

				phoneNumber = Util.formatPhoneNumber(phoneNumber);

				// lookup country for this phonenumber
				country = Util.getCountryforthisPhnNumber(phoneNumber,
						fromNumber, countryLookupMap);

				// get sms direction
				String smsDirection = sms.getDirection();
				StringBuffer sb = new StringBuffer();
				sb.append(country);
				sb.append("-");
				sb.append(smsDirection);
				country = sb.toString();

				System.out.println("Country: " + country + " PhoneNumber: "
						+ phoneNumber + "Sid: " + sms.getSid());

				if (countrySMSReport.get(country) != null) {
					if (country.equalsIgnoreCase(Constants.UNITED_STATES))
						country = SMSUtil.handleInCorrectUSNumbers(sms,
								countryLookupMap);

					updateSMSReportHM(sms, smsPrice, country);
				} else {
					SMSUsageReport smsUR = new SMSUsageReport(country, smsPrice);
					countrySMSReport.put(country, smsUR);
				}
			}
		}
	}

	/**
	 * This method updates the country SMS report If a country record has
	 * already been added to the map, this method will update the price and
	 * usage on the SMSUsage Report and sets it back to the map
	 * 
	 * @param sms
	 * @param smsPrice
	 * @param smsUsage
	 * @param country
	 * @param countryLookupMap
	 */

	public void updateSMSReportHM(Sms sms, String smsPrice, String country) {

		Object smsObjInc = countrySMSReport.get(country);
		SMSUsageReport smsURObj = (SMSUsageReport) smsObjInc;

		float priceBD = new Float(smsURObj.getPrice());
		float priceforthiscallBD = new Float(smsPrice);
		float resultpriceBD = priceBD + priceforthiscallBD;
		DecimalFormat resultBDFrmt = new DecimalFormat(Constants.DECIMAL_FORMAT);
		smsURObj.setPrice(String.valueOf(Float.valueOf(resultBDFrmt
				.format(resultpriceBD))));
		smsURObj.setCountry(country);
		smsURObj.incrementSMSCount();
		smsURObj.setAverageRateperMin();
		countrySMSReport.put(country, smsURObj);
	}

}
