package com.twilio.report.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.report.Reports.TwilioCountryWiseReport;
import com.twilio.report.Util.Constants;

@SuppressWarnings("serial")
public class TwilioCountryWiseReportMainServlet extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(TwilioCountryWiseReportMainServlet.class.getCanonicalName());
	HashMap<String, String> argumentMap = new HashMap<String, String>();
	
	/** service() responds to both GET and POST requests.
	  You can also use doGet() or doPost()
	*/
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
			response.setContentType("text/html");
		
		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		out.println("<h1>" + "hello twilio" + "</h1>");
	      
		logger.log(Level.INFO, "Running report");
		String titleKey = request.getParameter("m-title");
		String accountSidKey = request.getParameter("m-accountsid");
		String authTokenKey = request.getParameter("m-authtoken");
		String customerAccSidKey = request.getParameter("m-customeraccsid");
		String startDateKey = request.getParameter("m-startdate");
		String endDateKey = request.getParameter("m-enddate");
		
		argumentMap.put(Constants.TWILIO_REPORT_TYPE_KEY, titleKey);
		argumentMap.put(Constants.TWILIO_ACCOUNT_SID_KEY, accountSidKey);
		argumentMap.put(Constants.TWILIO_AUTH_TOKEN_KEY, authTokenKey);
		argumentMap.put(Constants.TWILIO_CUSTOMER_ACCOUNT_SID_KEY, customerAccSidKey);
		argumentMap.put(Constants.TWILIO_REPORT_START_DATE_KEY, startDateKey);
		argumentMap.put(Constants.TWILIO_REPORT_END_DATE_KEY, endDateKey);
		argumentMap.put(Constants.PRICING_FILE_PATH_KEY, "");
		argumentMap.put(Constants.REPORT_LOCATION_PATH_KEY, "");

		TwilioCountryWiseReport.initializeArguments(argumentMap);
		
	}
}
