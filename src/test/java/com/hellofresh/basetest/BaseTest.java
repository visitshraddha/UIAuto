package com.hellofresh.basetest;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

public class BaseTest {

	public WebDriver driver = null;
	public ExtentReports report = null;
	public ExtentTest test = null;
	public Properties prop = null;
	public Xls_Reader xls = null;
	public SoftAssert s_assert = null;

	public void loadconfig() {
		if (prop == null) {
			try {
				FileInputStream fis = new FileInputStream("config.properties");
				prop = new Properties();
				prop.load(fis);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void launchbrowser(String BrowserType) {
		try {
			if (driver == null) {
				if (BrowserType.equals("IE")) {
					System.setProperty("webdriver.ie.driver",
							System.getProperty("user.dir") + "\\resources\\IEDriverServer.exe");
					driver = new InternetExplorerDriver();
				} else if (BrowserType.equals("FF")) {
					System.setProperty("webdriver.gecko.driver", "\\resources\\geckodriver.exe");
					driver = new FirefoxDriver();
				} else if (BrowserType.equals("Chrome")) {
					System.setProperty("webdriver.chrome.driver",
							System.getProperty("user.dir") + "\\resources\\chromedriver.exe");
					driver = new ChromeDriver();

				}
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public WebElement getelemnt(String locator) {
		if (locator.endsWith("xpath")) {
			return driver.findElement(By.xpath(prop.getProperty(locator)));
		} else if (locator.endsWith("id")) {
			return driver.findElement(By.id(prop.getProperty(locator)));
		} else if (locator.endsWith("name")) {
			return driver.findElement(By.name(prop.getProperty(locator)));
		} else if (locator.endsWith("class")) {
			return driver.findElement(By.className(prop.getProperty(locator)));
		} else if (locator.endsWith("cssSelector")) {
			return driver.findElement(By.cssSelector(prop.getProperty(locator)));
		} else if (locator.endsWith("linktest")) {
			return driver.findElement(By.linkText(prop.getProperty(locator)));
		} else {
			test.log(LogStatus.ERROR, "Incorrect locator");
		}

		return null;

	}

	public void settext(String locator, String inputtext) {
		WebElement elem = getelemnt(locator);
		elem.sendKeys(inputtext);

	}

	public void switchwindow(int index) {
		driver.switchTo().frame(index);
	}

	public void click(String locator) {
		WebElement elem = getelemnt(locator);
		elem.click();
	}

	public void selectvalue(String locator, String inputtext) {
		WebElement elem = getelemnt(locator);
		Select sel = new Select(elem);
		sel.selectByValue(inputtext);

	}

	public void selectvvisibletext(String locator, String inputtext) {
		WebElement elem = getelemnt(locator);
		Select sel = new Select(elem);
		sel.selectByVisibleText(inputtext);

	}

	public void selectchkbox(String locator) {
		WebElement elem = getelemnt(locator);
		elem.click();
	}

	public void takescreenshot(ExtentTest Test) {
		Date d = new Date();
		String TimeStamp = d.toString().replaceAll(" ", "_").replace(":", "_");
		File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(f,
					new File(System.getProperty("user.dir") + "\\screenshots\\" + "Screenshot_" + TimeStamp + ".PNG"));
			test.log(LogStatus.INFO, test.addBase64ScreenShot(
					System.getProperty("user.dir") + "\\screenshots\\" + "Screenshot_" + TimeStamp + ".PNG"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String generateRandomemail() {
		Random randomGenerator = new Random();  
		int randomInt = randomGenerator.nextInt(1000); 
		String email="username"+ randomInt +"@gmail.com";
	    return email;
	}
	public String gettext(String locator) {
		WebElement elm = getelemnt(locator);
		String Actresult = elm.getText();
		return Actresult;
	}

	public void dynamicwait(int waittime, String locator) {
		WebDriverWait wait = new WebDriverWait(driver, waittime);
		if (locator.endsWith("xpath")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty(locator))));
		} else if (locator.endsWith("class")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(prop.getProperty(locator))));
		} else if (locator.endsWith("cssSelector")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(prop.getProperty(locator))));
		} else if (locator.endsWith("linktest")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(prop.getProperty(locator))));
		} else if (locator.endsWith("name")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(prop.getProperty(locator))));
		} else if (locator.endsWith("id")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(prop.getProperty(locator))));
		}
	}
}
