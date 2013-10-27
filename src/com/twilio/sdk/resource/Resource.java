package com.twilio.sdk.resource;

import java.util.Map;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.TwilioRestResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class Resource.
 * 
 * Added new method to set the Customer Account SID
 * 
 */
public abstract class Resource {

	/** The client. */
	private TwilioRestClient client;
	
	/** The request account sid. */
	private String requestAccountSid;
	public static String requestAccountSid_Customer;

	
	/** The filters. */
	protected Map<String, String> filters;
	
	/**
	 * Instantiates a new resource.
	 *
	 * @param client the client
	 */
	public Resource(TwilioRestClient client) {
		this.client = client;
	}
	
	/**
	 * Gets the client.
	 *
	 * @return the client
	 */
	protected TwilioRestClient getClient() {
		return this.client;
	}
	
	/**
	 * Load.
	 *
	 * @param params the params
	 * @throws TwilioRestException the twilio rest exception
	 */
	protected void load(Map<String, String> params) throws TwilioRestException {
		String path = this.getResourceLocation();
		TwilioRestResponse response = this.getClient().safeRequest(path, "GET",
				params);

		this.parseResponse(response);
		this.loaded = true;
	}
	
	/**
	 * Parses the response.
	 *
	 * @param response the response
	 */
	protected abstract void parseResponse(TwilioRestResponse response);
	
	// flags whether or not the HTTP request to popluate
	// this data has occured. We can construct resources
	// that are lazily loaded
	/** The loaded. */
	private boolean loaded;

	/**
	 * Checks if is loaded.
	 *
	 * @return true, if is loaded
	 */
	protected boolean isLoaded() {
		return loaded;
	}
	
	/**
	 * Sets the loaded.
	 *
	 * @param loaded the new loaded
	 */
	protected void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	// Taken from the request uri, or the request account
	/**
	 * Gets the request account sid.
	 *
	 * @return the request account sid
	 */
	protected String getRequestAccountSid() {
		this.requestAccountSid = getCustomerSid();
		return this.requestAccountSid;
	}
	
	/**
	 * Gets the request account sid.
	 *
	 * @return the request account sid
	 */
	protected String getCustomerSid() {
		return this.requestAccountSid_Customer;
	}
	
	
	/**
	 * Sets the request account sid.
	 *
	 * @param sid the new request account sid
	 */
	public void setRequestAccountSid(String sid) {
		this.requestAccountSid = sid;
	}
	
	/**
	 * Sets the customer request account sid .
	 *
	 * @param sid the new request account sid
	 */
	public void setRequestAccountSidCustomer(String requestAccountSid_Customer) {
		this.requestAccountSid_Customer = requestAccountSid_Customer;
	}

	/**
	 * Gets the resource location.
	 *
	 * @return the resource location
	 */
	protected abstract String getResourceLocation();

}
