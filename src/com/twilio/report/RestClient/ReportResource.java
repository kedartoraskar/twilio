package com.twilio.report.RestClient;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestResponse;
import com.twilio.sdk.resource.Resource;

/**
 * Extends the TwilioJDKLibrary
 * 
 * @author ktoraskar
 * 
 */

public class ReportResource extends Resource {

	public ReportResource(TwilioRestClient client,
			String TWILIO_REQUEST_ACCOUNT_SID) {
		super(client);
		this.setRequestAccountSidCustomer(TWILIO_REQUEST_ACCOUNT_SID);

		// TODO Auto-generated constructor stub
	}

	@Override
	protected void parseResponse(TwilioRestResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String getResourceLocation() {
		// TODO Auto-generated method stub
		return null;
	}
}
