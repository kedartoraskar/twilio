package com.twilio.report.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TwilioCountryWiseReportMainServlet extends HttpServlet {
	
	/** service() responds to both GET and POST requests.
	  You can also use doGet() or doPost()
	*/
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
			response.setContentType("text/html");

	      // Actual logic goes here.
	      PrintWriter out = response.getWriter();
	      out.println("<h1>" + "hello twilio" + "</h1>");
	}

}
