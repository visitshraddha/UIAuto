/*
 *This test is for Verify Existing User Login  into Automation Practice URL 
 *  
 */

package com.hellofresh.challenge;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import java.util.Hashtable;
import org.testng.asserts.SoftAssert;
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



public class VerifyLoginIn extends BaseTest {
	public String TC = "VerifyLogin_TC";
	public Xls_Reader xls = null;

	@BeforeMethod
	public void setup(){
		s_assert= new SoftAssert();
	}
	
	
	@Test(dataProvider = "tcdata")
	public void VerifyLogin(Hashtable<String, String> ht) {
		loadconfig();
		report = ExtentManager.getInstance();
		test = report.startTest("VerifyLogin_TC" + ht.toString());

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

			//Click *Sign in* button (in the header)
			dynamicwait(30, "login_class");
			click("login_class");
			((JavascriptExecutor) driver).executeScript("scroll(200,300)");
			test.log(LogStatus.PASS, "SignIn page page is displayed");
			takescreenshot(test);

			//Fill *Email address* in _Already registered_ block
			settext("email_id", ht.get("Email"));
			
			//Fill *Password* in _Already registered_ block
			settext("pwd_id", ht.get("PWD"));
			((JavascriptExecutor) driver).executeScript("scroll(200,400)");
			test.log(LogStatus.PASS, "Enter Existing User Login details");
			takescreenshot(test);
			//Click *Sign in* 
			click("sublog_id");
			dynamicwait(30, "h1_cssSelector");
			((JavascriptExecutor) driver).executeScript("scroll(200,300)");
			test.log(LogStatus.PASS, "Existing User Login is successful");
			takescreenshot(test);
			//My account page(?controller=my-account) is opened
			s_assert.assertEquals("MY ACCOUNT", gettext("h1_cssSelector"));
			test.log(LogStatus.PASS, "My Account page is displayed");
			
			//Proper username is shown in the header
			s_assert.assertEquals(ht.get("FullName"), gettext("acct_class"));
			test.log(LogStatus.PASS, "Account Name displayed as " + ht.get("FullName"));
			s_assert.assertTrue(gettext("acctinfo_class").contains("Welcome to your account."));
			test.log(LogStatus.PASS, "Welcome to your Account message is displayed");
			takescreenshot(test);
			
			//Log out action is available
			s_assert.assertTrue(getelemnt("logout_class").isDisplayed());
			test.log(LogStatus.PASS, "Logout is displayed");
			s_assert.assertTrue(driver.getCurrentUrl().contains("controller=my-account"));
			click("logout_class");
			
		}

	@AfterMethod
	public void teardowbn() {
		try{
			s_assert.assertAll();
		}catch(Error e){				
			test.log(LogStatus.FAIL,e.getMessage());
		}
		report.endTest(test);
		report.flush();
	}
	
	@AfterClass
	public void close(){
		if(driver!=null)
			driver.quit();

	}

	@DataProvider(name = "tcdata")
	public Object[][] tcdata() {
		xls = new Xls_Reader(System.getProperty("user.dir") + "\\TestData\\HelloFresh_TestData.xlsx");
		return DataReader.getdata(xls, "TestData", TC);

	}

}
