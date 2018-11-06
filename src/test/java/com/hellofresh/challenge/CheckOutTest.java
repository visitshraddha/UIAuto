/*
 *This test is for do checkout of items and complete order for existing user in Automation Practice URL 
 *  
 */

package com.hellofresh.challenge;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import java.util.Hashtable;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import com.relevantcodes.extentreports.LogStatus;
import com.hellofresh.hybrid.util.DataReader;
import com.hellofresh.basetest.Xls_Reader;
import com.hellofresh.basetest.BaseTest;
import com.hellofresh.hybrid.util.ExtentManager;
import org.testng.asserts.SoftAssert;

public class CheckOutTest extends BaseTest {
	public String TC = "CheckOut_TC";
	public Xls_Reader xls = null;

	@BeforeMethod
	public void setup() {
		s_assert = new SoftAssert();
	}

	@Test(dataProvider = "tcdata")
	public void CheckOut(Hashtable<String, String> ht) {
		loadconfig();
		report = ExtentManager.getInstance();
		test = report.startTest("CheckOut_TC" + ht.toString());

		// read the runmode status of testcase and data combination and execute
		// the testcase accordingly
		if (!DataReader.isRunnable(xls, "Suite", TC) || ht.get("RunMode").equals("N")) {
			test.log(LogStatus.SKIP, TC + " is passed");
			throw new SkipException("skip testcase as runmode flag is 'N'");
		}
		launchbrowser(ht.get("Browser"));
		test.log(LogStatus.INFO, "Browser launch successfully");
		driver.navigate().to("http://automationpractice.com/index.php");
		test.log(LogStatus.PASS, "Your Logo page is launch successfully");
		takescreenshot(test);
		dynamicwait(30, "login_class");
		click("login_class");
		settext("email_id", ht.get("Email"));
		settext("pwd_id", ht.get("PWD"));
		click("sublog_id");
		test.log(LogStatus.PASS, "User is able to login successfully");
		takescreenshot(test);
		dynamicwait(30, "women_linktest");
		click("women_linktest");
		
		// Click the product with name "Faded Short Sleeve T-shirts"
		click("Tshirt_xpath");
		
		// Click on Proceed to checkout
		click("Tshirt_xpath");
		test.log(LogStatus.PASS, "Click the product with name Faded Short Sleeve T-shirts & Proceed to checkout");
		takescreenshot(test);
		
		// Click on Proceed to checkout
		dynamicwait(30, "sub_name");
		click("sub_name");
		test.log(LogStatus.PASS, "Tshirts are added successfully into Cart");
		takescreenshot(test);
		dynamicwait(30, "chkout_xpath");
		((JavascriptExecutor) driver).executeScript("scroll(200,300)");
		click("chkout_xpath");
		
		//Summary page displayed
		((JavascriptExecutor) driver).executeScript("scroll(200,500)");
		test.log(LogStatus.PASS, "Shopping Cart summary page displayed successfully");
		takescreenshot(test);
		dynamicwait(30, "procechkout_xpath");
		click("procechkout_xpath");
		
		// Address of delivery is displayed. Click on next
		dynamicwait(30, "pradd_name");
		click("pradd_name");
		((JavascriptExecutor) driver).executeScript("scroll(200,300)");
		test.log(LogStatus.PASS, "Shipping page is displayed successfully");
		takescreenshot(test);
		
		// Click by *Terms of service* to agree
		dynamicwait(30, "uni_id");
		selectchkbox("uni_id");
		click("proc_name");
		((JavascriptExecutor) driver).executeScript("scroll(200,300)");
		test.log(LogStatus.PASS, "Payment page is displayed successfully");
		takescreenshot(test);
		
		// Click by payment method *Pay by bank wire*
		dynamicwait(30, "bank_class");
		click("bank_class");
		((JavascriptExecutor) driver).executeScript("scroll(200,300)");
		test.log(LogStatus.PASS, "Bank Wire page is displayed successfully");
		takescreenshot(test);
		
		// Click *I confirm my order*
		dynamicwait(30, "navigation_xpath");
		click("navigation_xpath");
		((JavascriptExecutor) driver).executeScript("scroll(200,300)");
		
		//Order confirmation page(?controller=order-confirmation) is opened
		s_assert.assertEquals("ORDER CONFIRMATION", gettext("h1_cssSelector"));
		s_assert.assertTrue(driver.getCurrentUrl().contains("controller=order-confirmation"));
		test.log(LogStatus.PASS, "Order confirmation page is displayed successfully");
		takescreenshot(test);
		dynamicwait(30, "h1_cssSelector");
		click("h1_cssSelector");
		((JavascriptExecutor) driver).executeScript("scroll(200,300)");
		
		//The order is complete & Current page is the last step of ordering.
		s_assert.assertTrue(gettext("chq_xpath").contains("Your order on My Store is complete."));
		test.log(LogStatus.PASS, "Order Complete is displayed successfully");
		takescreenshot(test);
		click("logout_class");

	}

	@AfterMethod
	public void teardowbn() {
		try {
			s_assert.assertAll();
		} catch (Error e) {
			test.log(LogStatus.FAIL, e.getMessage());
		}
		report.endTest(test);
		report.flush();
	}

	@AfterClass
	public void close() {
		if (driver != null)
			driver.quit();

	}

	@DataProvider(name = "tcdata")
	public Object[][] tcdata() {
		xls = new Xls_Reader(System.getProperty("user.dir") + "\\TestData\\HelloFresh_TestData.xlsx");
		return DataReader.getdata(xls, "TestData", TC);

	}
}
