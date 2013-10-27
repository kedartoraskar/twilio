package com.twilio.report.Calls;

import java.util.Map;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.list.CallList;

public class Calls {

	public static CallList getCallList(TwilioRestClient client,
			Map<String, String> params) {

		CallList calllist = client.getAccount().getCalls(params);
		return calllist;
	}

}
