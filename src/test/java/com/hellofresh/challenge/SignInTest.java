/*
 *This test is for SignIn with new user into Automation Practice URL 
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
import org.testng.asserts.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;
import com.hellofresh.hybrid.util.DataReader;
import com.hellofresh.basetest.Xls_Reader;
import com.hellofresh.basetest.BaseTest;
import com.hellofresh.hybrid.util.ExtentManager;

public class SignInTest extends BaseTest {
	public String TC = "SignIn_TC";
	public Xls_Reader xls = null;

	//Soft Asserts are declared
	@BeforeMethod
	public void setup() {
		s_assert = new SoftAssert();
	}

	@Test(dataProvider = "tcdata")
	public void signInTest(Hashtable<String, String> ht) {

		loadconfig();
		report = ExtentManager.getInstance();
		test = report.startTest("SignIn_TC" + ht.toString());

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
		
		//Click *Sign in* button
		dynamicwait(60, "login_class");
		click("login_class");
		((JavascriptExecutor) driver).executeScript("scroll(200,300)");
		test.log(LogStatus.PASS, "SignIn page page is displayed");
		takescreenshot(test);
		
		//Fill *Email address* to create an account
		dynamicwait(30, "CreateEmail_id");
		settext("CreateEmail_id", generateRandomemail());
		
		//Click *Create an account* 
		click("submit_id");
		((JavascriptExecutor) driver).executeScript("scroll(200,300)");
		test.log(LogStatus.PASS, "Email details submitted successfully");
		takescreenshot(test);
		
		//Fill all fields with correct data 
		dynamicwait(30, "gender_id");
		takescreenshot(test);
		test.log(LogStatus.PASS, "Create an account page page is displayed");
		click("gender_id");
		settext("custfirstname_id", ht.get("Name"));
		settext("custlastname_id", ht.get("Surname"));
		settext("pwd_id", ht.get("PWD"));
		selectvalue("day_id", ht.get("Day"));
		selectvalue("month_id", ht.get("Month"));
		selectvalue("yr_id", ht.get("Year"));
		takescreenshot(test);
		settext("comp_id", ht.get("Company"));
		settext("add1_id", ht.get("add1"));
		settext("add2_id", ht.get("add2"));
		settext("city_id", ht.get("city"));
		selectvvisibletext("state_id", ht.get("state"));
		settext("post_id", ht.get("post"));
		takescreenshot(test);
		settext("other_id", ht.get("other"));
		settext("ph_id", ht.get("phone"));
		settext("mob_id", ht.get("mobile"));
		settext("alias_id", ht.get("alias"));
		takescreenshot(test);
		
		//Click *Register* button
		click("sub_id");
		test.log(LogStatus.PASS, "Account details added");
		takescreenshot(test);
		dynamicwait(30, "h1_cssSelector");

		//My account page(?controller=my-account) is opened
		s_assert.assertEquals(gettext("h1_cssSelector"), "MY ACCOUNT");
		test.log(LogStatus.PASS, "My Account page is displayed");
		
		//Proper username is shown in the header
		s_assert.assertEquals(gettext("acct_class"), ht.get("Name") + " " + ht.get("Surname"));
		test.log(LogStatus.PASS, "Account Name displayed as " + ht.get("Name") + " " + ht.get("Surname"));
		s_assert.assertTrue(gettext("acctinfo_class").contains("Welcome to your account."));
		takescreenshot(test);
		test.log(LogStatus.PASS, "Welcome to your Account message is displayed");
		
		//Log out action is available
		s_assert.assertTrue(getelemnt("logout_class").isDisplayed());
		test.log(LogStatus.PASS, "Logout is displayed");
		s_assert.assertTrue(driver.getCurrentUrl().contains("controller=my-account"));
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