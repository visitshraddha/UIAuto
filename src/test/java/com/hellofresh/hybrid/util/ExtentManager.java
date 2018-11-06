package com.hellofresh.hybrid.util;

import java.util.Date;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	
	
	public static ExtentReports extent=null;

	public static ExtentReports getInstance(){
		if (extent==null) {
			Date d= new Date();
			String filename=d.toString().replace(":", "_").replace(" ", "_")+".html";
			extent = new ExtentReports(System.getProperty("user.dir")+"\\reports\\"+filename,true,DisplayOrder.NEWEST_FIRST);

			System.out.println(filename);

		}
		return extent;
	}
}

