package com.twilio.report.RestClient;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.Resource;

public class TwilioRestClientFactory {

	public static TwilioRestClient createTwilioRestClient(String TWILIO_SE_ACCOUNT_SID, String TWILIO_SE_AUTH_TOKEN, String TWILIO_REQUEST_ACCOUNT_SID){
	
	    TwilioRestClient client = new TwilioRestClient(TWILIO_SE_ACCOUNT_SID, TWILIO_SE_AUTH_TOKEN);
	    setRequestAccountSID(client, TWILIO_REQUEST_ACCOUNT_SID);
	    return client;
	}
	
	public static void setRequestAccountSID(TwilioRestClient client, String TWILIO_REQUEST_ACCOUNT_SID){
		System.out.println("Setting the Request Acc SID");
		new ReportResource(client, TWILIO_REQUEST_ACCOUNT_SID);
		
	}
	
}
