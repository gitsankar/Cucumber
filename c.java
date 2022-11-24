package com.tes.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

//import com.pc.elements.Elements;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.google.common.base.Predicate;


public class Common {
	//got from Main Driver
	public static XlsxReader sXL;
	public static Integer TCRow;
	public static String  TCID;
	public static  String testcasename = null;
	public static String methodName = null;
	public static String  TestCaseID;
	public static String  TestSetID;
	public static String  DataSheetName;
	public static RemoteWebDriver driver;
	public static PhantomJSDriverService service = null;
	static Logger logger =Logger.getLogger("Common");
	public static WebElement ele;
//	
	
	public static WebDriver dr(){
	 return driver;
	}
	
	/*public static boolean WaitForElementExist(By bylocator, int iWaitTime) throws Exception
	{    	
    	boolean bFlag = false;		
		WebDriverWait wait = new WebDriverWait(driver, iWaitTime);
		try
		{
			wait.until(ExpectedConditions.presenceOfElementLocated(bylocator)); //see if you can append ExpectedConditions.visibilityOfElementLocated(bylocator) also in Until
			if(driver.findElement(bylocator).isDisplayed()||driver.findElement(bylocator).isEnabled())
			{
				bFlag = true;
			}
		}
		catch (NoSuchElementException e)
		{
			e.printStackTrace();
			bFlag = false;
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
			bFlag = false;			
		}
		return bFlag;
	}*/
	
		
	/**
	 * Method - Safe Method for User Select option from list menu, waits until the element is loaded and then selects an option from list menu
	 * @param bylocator
	 * @param sOptionToSelect
	 * @param iWaitTime
	 * @return
	 * @throws Exception
	**/
	/*public static boolean SafeSelectGWListBox(By bylocator, String sOptionToSelect, int iWaitTime) throws Exception
    { 
		WaitUntilClickable(bylocator, iWaitTime);		
		WebElement element = driver.findElement(bylocator);
		element.click();
		Thread.sleep(1000);
		driver.findElement(bylocator).sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(1500);	

		boolean bFlag = false; 
		WaitForElementExist(bylocator, iWaitTime);
	    List<WebElement> gwListBox = driver.findElements(By.tagName("LI"));
	    
        for (int i=0; i<gwListBox.size(); i++) 
        {
        	String strListValue = gwListBox.get(i).getText();
			try
			{
	            if (strListValue.contains(sOptionToSelect))
	            {
					System.out.println(gwListBox.get(i).getText());
	            	gwListBox.get(i).click();
					bFlag = true;
					break;
	            }
			}
			catch (Exception e)
			{				
				e.printStackTrace();
				bFlag = false;
			}
        }
		return bFlag;
    }

	public static boolean WaitUntilClickable(By bylocator, int iWaitTime) throws Exception
	{    	
    	boolean bFlag = false;		
		WebDriverWait wait = new WebDriverWait(driver, iWaitTime);
		try
		{
			wait.until(ExpectedConditions.elementToBeClickable(bylocator)); 
			//if(bylocator.isDisplayed())
			if(driver.findElement((bylocator)).isDisplayed())
			{
				bFlag = true;
			}
		}
		catch (NoSuchElementException e)
		{
			e.printStackTrace();
			bFlag = false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			bFlag = false;			
		}
		return bFlag;
	}
	
	*//**
	 * Method: Highlights on current working element or locator
	 * @param driver
	 * @param locator
	 * @throws Exception
	 *//*
	public static void highlightElement(By locator) throws Exception
	{
		//pro = new ConfigManager("sys");
		WebElement element = driver.findElement(locator);
		if(HTML.properties.getProperty("HighlightElements").equalsIgnoreCase("true"))
		{
	        String attributevalue="border:10px solid green;";//change border width and colour values if required
	        JavascriptExecutor executor= (JavascriptExecutor) driver;
	        String getattrib=element.getAttribute("style");
	        executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, attributevalue);
	        Thread.sleep(100);
	        executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, getattrib);
		}       
    }*/
	/**
	 * @function Use to perfrom any action in the application
	 * @param element
	 * @param value
	 * @param ColumnName
	 * @return
	 * @throws Exception
	 */
	/*public static  Boolean SafeAction(By element, String value,String ColumnName) throws Exception
	{
		Boolean returnValue = true;
		Actions objActions = null;
		objActions = new Actions(driver);
		String elementType = ColumnName.substring(0, 3);
		String objectName = ColumnName.substring(3);
		boolean elementClickable = WaitUntilClickable(element, Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
//		boolean elementExist = driver.findElements(element).size()!=0;
		if(elementClickable == true)
//		if(elementExist == true)
		{
				String wait = HTML.properties.getProperty("LONGWAIT");
				Integer x = Integer.valueOf(wait);
				Boolean f = WaitUntilClickable(element, x); 
				Boolean f = driver.findElements(element).size()!=0;
				if (!f)
		    	{
					returnValue = false;
				}
				else
				{
					highlightElement(element);
					try
					{
						ele = driver.findElement(element);
						returnValue = true;
					}
					catch(Exception e)
					{
						returnValue = false;
					}
				}
		}	
		else
		{
			returnValue = false;
		}
		if(returnValue)
		{	
			switch (elementType.toUpperCase())
			{
				case "MEL":
					String colName = ColumnName.toUpperCase();
					Integer xYaxis=null;
					Integer yYaxis=null;
					if(colName.contains("ACCOUNT")){
						xYaxis = 36;
						yYaxis = 5;
					}else if(colName.contains("SEARCH"))
					{
						xYaxis = 60;
						yYaxis = 5;
					}else if(colName.contains("ADMINISTRATION"))
					{
						xYaxis = 67;
						yYaxis = 5;
					}
					Actions clickTriangle= new Actions(driver);
					clickTriangle.moveToElement(ele).moveByOffset(xYaxis, yYaxis).click().perform();
					returnValue = true;
					logger.info("Clicked on '" + objectName + "' element or button or link and element '"+ element + "'");
					HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should click on '" + objectName + "' element or button or link", "Clicked on '" + objectName + "' element or button or link", "PASS");
					break;
			 	case "ZED":
//			 		ele.sendKeys(value);
//			    	returnValue = true;
			 		driver.findElement(element).clear();
			    	driver.findElement(element).sendKeys(value);
			    	WaitForPageToBeReady();
			    	driver.findElement(element).sendKeys(Keys.TAB);
			    	WaitForPageToBeReady();
			    	logger.info("Value entered '"+ value + "' in '" + objectName + "' field and element '"+ element + "'");
			    	HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should enter value  '"+ value + "' in '" + objectName + "' field", "Value entered '"+ value + "' in '" + objectName + "' field", "PASS");
					break;
			    case "EDT":
			    	ele.clear();
			    	ele.sendKeys(value);
			    	returnValue = true;
			    	logger.info("Value entered '"+ value + "' in '" + objectName + "' field and element '"+ element + "'");
			    	HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should enter value  '"+ value + "' in '" + objectName + "' field", "Value entered '"+ value + "' in '" + objectName + "' field", "PASS");
					break;
			    case "PWD":
			    	ele.clear();
			    	ele.sendKeys(value);
			    	returnValue = true;
					break;
			    case "BTN":
                    ele.click();
                    returnValue=true;
                    logger.info("Clicked on '" + objectName + "' element or button or link and element '"+ element + "'");
                    HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should click on '" + objectName + "' element or button or link", "Clicked on '" + objectName + "' element or button or link", "PASS");
                    break;
			    case "ELE":
			        Action objMouseClick1 = objActions
			        		.click(ele)
			        		.build();
			        objMouseClick1.perform();  
			        returnValue=true;
					logger.info("Clicked on '" + objectName + "' element or button or link and element '"+ element + "'");
					HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should click on '" + objectName + "' element or button or link", "Clicked on '" + objectName + "' element or button or link", "PASS");
					break;
			    case "DBL":
			    	objActions.click(ele);
			        Action objMousedblClick=objActions
			        		.doubleClick(ele)
			        		.build();
			        objMousedblClick.perform(); 
			        returnValue=true;
					logger.info("Double Clicked on '" + objectName + "' element or button or link and element '"+ element + "'");
					HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should Double Click on '" + objectName + "' element or button or link", "Double Clicked on '" + objectName + "' element or button or link", "PASS");
					break;
			    case "LST":
    				*//**boolean listStatus = false;
//					WebElement element1 = driver.findElement(element);
					ele.click();
					WaitForPageToBeReady();
					driver.findElement(element).sendKeys(Keys.ARROW_DOWN);
					WaitForPageToBeReady();	
				    List<WebElement> gwListBox = driver.findElements(By.tagName("LI"));
			        for (int i = 0; i<gwListBox.size(); i++) 
			        {
			        	String strListValue = gwListBox.get(i).getText();
			            if (strListValue.contains(value))
			            {
			            	gwListBox.get(i).click();
			            	returnValue = true;
			            	listStatus = true;
							logger.info("Selected item '"+ value +"' from '" + objectName + "' listbox and element '"+ element + "'");
							HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should Select item '"+ value +"' from '" + objectName + "' listbox", "Selected item '"+ value +"' from '" + objectName + "' listbox", "PASS");
							break;
			            }
			        }
			       if(!listStatus)
			        {
		            	returnValue = false;
			        	logger.info("Value not available '"+ value +"' in '" + objectName + "' listbox and element '"+ element + "'");
			        	HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should Select item '"+ value +"' from '" + objectName + "' listbox", "Item '"+ value +"' not available in '" + objectName + "' listbox", "PASS");
						break;
			        }  **//*  
			    	driver.findElement(element).clear();
			    	driver.findElement(element).clear();
			    	driver.findElement(element).sendKeys(value);
//			    	WaitForPageToBeReady();
			    	driver.findElement(element).sendKeys(Keys.TAB);
//			    	WaitForPageToBeReady();
					logger.info("Value available '"+ value +"' in '" + objectName + "' listbox and element '"+ element + "'");
					HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should Select item '"+ value +"' from '" + objectName + "' listbox", "Selected item '"+ value +"' from '" + objectName + "' listbox", "PASS");
			    	break;
			    case "SCL":
			    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(element));
			    	logger.info("Scroll Donw to the Element " + objectName + " element or button or link and element '"+ element + "'");	
			    	HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should click on '" + objectName + "' element or button or link", "Clicked on '" + objectName + "' element or button or link", "PASS");
			    	returnValue = true;
			    	break;
			    case "RDO":
					logger.info("Selected Radio " + objectName + " element or button or link and element '"+ element + "'");
					HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should click on '" + objectName + "' element or button or link", "Clicked on '" + objectName + "' element or button or link", "PASS");
			    	returnValue = true;
			    	break;
			    case "CHK":
			    	logger.info("Checked " + objectName + " element or button or link and element '"+ element + "'");
			    	HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should click on '" + objectName + "' element or button or link", "Clicked on '" + objectName + "' element or button or link", "PASS");
			    	returnValue = true;
			    	break;
			    case "CLR":
			    	logger.info("Checked " + objectName + " element or button or link and element '"+ element + "'");
			    	ele.clear();
					HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "", "", "PASS");
			    	returnValue = true;
			    	break; 	
			    case "ELJ":
			    	logger.info("Checked " + objectName + " element or button or link and element '"+ element + "'");
			    	JavascriptExecutor executor = (JavascriptExecutor)driver;
					executor.executeScript("arguments[0].click();", ele);
					HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should click on '" + objectName + "' element or button or link", "Clicked on '" + objectName + "' element or button or link", "PASS");
			    	returnValue = true;
			    	break;
			    case "BLI":
			    	ele.click();
			       	By option = By.xpath("//span[starts-with(text(),'"+value+"')]");
                   	if(driver.findElement(option).isDisplayed())
                    {
                    driver.findElement(option).click();
                    returnValue=true;
                    logger.info("Clicked on '" + objectName + "' element or button or link and element '"+ element + "'");
                    HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should click on '" + objectName + "' element or button or link", "Clicked on '" + objectName + "' element or button or link", "PASS");
                    }
                    else
                    {
                    returnValue=false;
                    logger.info("Object not enabled or displayed or not clickable '"+ element + "'");
                    HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should display '" + objectName + "' in screen", "'" + objectName + "' not displayed in screen", "FAIL");
                    }
                    break;
			    case "DRP":
			    	Select sDropDown = new Select(ele);
			    	sDropDown.selectByVisibleText(value);
			    	HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should select the '" + value + "' from the Dropdown", "Selected the '" + value + "' from the Dropdown", "PASS");
			    	returnValue = true;
                    break;
			}
			WaitForPageToBeReady();
		}
		else
		{
			logger.info("Object not enabled or displayed or not clickable '"+ element + "'");
			HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should display '" + objectName + "' in screen", "'" + objectName + "' not displayed in screen", "FAIL");
		}
		return returnValue;
	}*/
	
	/**
	 * @function This will return the element
	 * @param sLocator
	 * @return
	 */
/*	public static WebElement returnObject(By sLocator)
	{
		WebElement elements = null;
		try{
			elements = driver.findElement(sLocator);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return elements;	
	}*/
	
	/**
	 * function This will use to check the browser state is ready perform the next action
	 */
	/*public static void WaitForPageToBeReady() 
    {
           //http://www.testingexcellence.com/webdriver-wait-page-load-example-java/
        JavascriptExecutor js = (JavascriptExecutor)driver;
        for (int i=0; i<Integer.parseInt(HTML.properties.getProperty("VERYLONGWAIT")); i++)
        { 
            try 
            {
                Thread.sleep(800);
            }catch (InterruptedException e) {} 
            if (js.executeScript("return document.readyState").toString().equals("complete"))
            { 
                break; 
            }   
          }
    }*/
	
	/*public static void WaitForPageToBeReady1()
	{
		WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(HTML.properties.getProperty("VERYLONGWAIT")));
		Predicate<WebDriver> pageLoad = new Predicate<WebDriver>()
				{
					@Override
					public boolean apply(WebDriver input)
					{
						return((JavascriptExecutor) input).executeScript("return document.readyState").equals("complete");
					}
				};
			System.out.println("Page is loaded");	
			wait.until(pageLoad);
	}*/
    
	/**
	 * @function Used to open the browser according to the environment variable
	 * @return bStatus
	 * @throws Exception
	 */
	/*public  static Boolean OpenApp() throws Exception
	{
		Boolean bStatus = false;
		String driverPath = HTML.properties.getProperty("driverPath");
		String execution = HTML.properties.getProperty("TypeOfAutomation");
		String browser = HTML.properties.getProperty("Browser");
		DesiredCapabilities cap = null;
			if (execution.toUpperCase().equalsIgnoreCase("HEADLESS"))
			{
				logger.info("Webdriver Headless Automation started");
				final String phantomJsFilePath = driverPath + "\\phantomjs.exe";
				service = new PhantomJSDriverService.Builder().usingPhantomJSExecutable(new File(phantomJsFilePath)).usingAnyFreePort()
				.build();
				service.start(); 
				logger.info("Headless phantomjs service started");
				if (browser.toUpperCase().contains("CH"))
				{	
					 cap = DesiredCapabilities.chrome();
				}
				if (browser.toUpperCase().contains("FF"))
				{
					cap = DesiredCapabilities.firefox();
				}
				if (browser.toUpperCase().contains("IE"))
				{
					cap = DesiredCapabilities.internetExplorer();
				}
				driver = new RemoteWebDriver(new URL(service.getUrl().toString()), cap);
				logger.info("Headless '" + browser.toUpperCase() + "' DesiredCapabilities initiated");
				logger.info("Headless '" + browser.toUpperCase() + "' driver started");	
				HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "Headless '" + browser.toUpperCase() + "' driver shold start","Headless '" + browser.toUpperCase() + "' driver started", "PASS");
			}
			if (execution.toUpperCase().equalsIgnoreCase("Grid"))
			{
				logger.info("Webdriver Grid Automation started");
				if (browser.toUpperCase().contains("CH"))
				{	
					cap = DesiredCapabilities.chrome();
					cap.setBrowserName("chrome");
					cap.setPlatform(Platform.WINDOWS);
				}
				if (browser.toUpperCase().contains("FF"))
				{
					cap = DesiredCapabilities.firefox();
					cap.setBrowserName("firefox");
					cap.setPlatform(Platform.WINDOWS);
				}
				if (browser.toUpperCase().contains("IE"))
				{
					cap = DesiredCapabilities.internetExplorer();
					cap.setBrowserName("iexplore");
					cap.setPlatform(Platform.WINDOWS);
				}
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
				logger.info("Headless '" + browser.toUpperCase() + "' DesiredCapabilities initiated");
				logger.info("Headless '" + browser.toUpperCase() + "' driver started");	
				HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "Headless '" + browser.toUpperCase() + "' driver shold start","Headless '" + browser.toUpperCase() + "' driver started", "PASS");
			}
			if (execution.toUpperCase().equalsIgnoreCase("HEAD"))
			{
				logger.info("Webdriver with Head Automation started");
				if (browser.toUpperCase().contains("CH"))
				{
					System.setProperty("webdriver.chrome.driver", driverPath + "\\chromedriver.exe");
					//driver = new ChromeDriver();
					ChromeOptions options = new ChromeOptions();
					options.addArguments("--disable-extensions");
					driver = new ChromeDriver(options);
				}
				if (browser.toUpperCase().contains("FF"))
				{
					driver = new FirefoxDriver();
					 logger.info("firefox  DesiredCapabilities initiated");	
				}
				if (browser.toUpperCase().contains("IE"))
				{
					System.setProperty("webdriver.ie.driver", driverPath + "\\IEDriverServer.exe");
					driver = new InternetExplorerDriver();
				}
				 logger.info("'" + browser.toUpperCase() + "' driver started");
					HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "Head '" + browser.toUpperCase() + "' driver shold start","Head '" + browser.toUpperCase() + "' driver started", "PASS");				 
			}
			String sURL = null;
			sURL = HTML.properties.getProperty(HTML.properties.getProperty("Region"));
			logger.info("Execution starting in '" + HTML.properties.getProperty("Region").toUpperCase() + "' environment");
			HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "Execution should start in '" + HTML.properties.getProperty("Region").toUpperCase() + "' environment","Execution started in '" + HTML.properties.getProperty("Region").toUpperCase() + "' environment", "PASS");
			driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			logger.info("Browser maximized");
			driver.get(sURL);
			logger.info("Execution starting in '" + HTML.properties.getProperty("Region").toUpperCase() + "' environment and url '" + sURL + "'");
			Integer x = Integer.valueOf(HTML.properties.getProperty("LONGWAIT"));
			if(WaitUntilClickable(o.getObject("edtUserName"),  x))
			//if(WaitUntilClickable(o.getObject("eleDeskTopAction"),  x))
			{
				logger.info("Application Opened Successfully");
				HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "Application should Open successfully","Application Opened successfully", "PASS");
				bStatus = true;
			}
			return bStatus;
	}*/
	
	/**
	 * Method - Safe Method for User Select option from list menu, waits until the element is loaded and then selects an option from list menu
	 * @param bylocator
	 * @param sOptionToSelect
	 * @param iWaitTime
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	/*public static boolean MouseHoverAction(By sMainMenu, By sSubMenu)
	{
		boolean status = false;
	    try 
	     {
	         String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
	         ((JavascriptExecutor) driver).executeScript(mouseOverScript, driver.findElement(sMainMenu));
	         Thread.sleep(1000);
	         ((JavascriptExecutor)driver).executeScript("arguments[0].click();",driver.findElement(sSubMenu));
	         status = true;
	    }catch(Exception e) 
	    {
	    	 System.out.println("Element not found");
	    	 status = false;
	    }
		return status;
	}*/
	
	/**
	 * @function Ability to get the text of the element
	 * @param bylocator
	 * @param iWaitTime
	 * @return element
	 * @throws Exception
	**/
	/*public static String ReadElement(By bylocator, int iWaitTime) throws Exception
    { 
		WaitUntilClickable(bylocator, iWaitTime);		
		WebElement element = driver.findElement(bylocator);
		return element.getText();
    }*/
	
    /**
    * @function Ability to get the text of the element which is having Unclickable field
    * @param bylocator
    * @param iWaitTime
    * @return element
    * @throws Exception
    **/
  /*  public static String ReadElementforODS(By bylocator, int iWaitTime) throws Exception
    { 
           WebElement element = driver.findElement(bylocator);
           return element.getText();
    }

	public boolean ElementExist(By bylocator) throws Exception
    { 
		WebElement element = driver.findElement(bylocator);
		if(element.isDisplayed())
		{
			return true;			
		}else
		{
			return false;
		}
    }
	*/
	/**
	 * @function This will use to check whether the object is empty or not
	 * @return
	 */
	/*public static boolean ElementEmpty(By sLocator)
	{
		driver.findElements(sLocator).isEmpty();
		return true;
	}
	
	public static boolean ElementDisplayed(By sLocator)
	{
		boolean status = false;
		if(driver.findElement(sLocator).isDisplayed())
		{
			status = true;
		}else{
			status = false;
		}
		return status;
	}*/
	
	/**
	 * 
	 * @param sCase
	 * @param sExpectedValue
	 * @param sAcutualValue
	 * @function Compare two strings and populate the results in HTML
	 * @return
	 * @throws Exception
	 */
	/*public static boolean CompareStringResult(String sCase, String sExpectedValue, String sAcutualValue) throws Exception
	{
		boolean status = true;
		if(sExpectedValue.equals(sAcutualValue))
		{
			HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), ""+sCase+": '"+sExpectedValue+"' should match", ""+sCase+": '"+sAcutualValue+"' is matching", "PASS");
			status = true;
		}else{
			HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), ""+sCase+": '"+sExpectedValue+"' should match", ""+sCase+": '"+sAcutualValue+"' is not matching", "FAIL");
			status = false;
		}
		return status;
	}*/
	
	/**
	 * 
	 * @param sCase
	 * @param sExpectedValue
	 * @param sAcutualValue
	 * @param sId
	 * @function Check whether the element is present in the applicaiton(element should not present) and populate the results
	 * @return staus
	 * @throws Exception
	 */
	/*public static boolean ElementExistOrNotFalse(String sCase, String sExpectedValue, String sAcutualValue, By sId) throws Exception
	{
		boolean status = driver.findElements(sId).size()!=0;
		if(status == false)
		{
			HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), ""+sCase+": '"+sExpectedValue+"' should not present", ""+sCase+": '"+sAcutualValue+"' not present", "PASS");
			status = true;
		}else{
			HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), ""+sCase+": '"+sExpectedValue+"' should not present", ""+sCase+": '"+sAcutualValue+"' is present", "FAIL");
			status = false;
		}
		return status;
	}*/
	
	/**
	 * 
	 * @param sCase
	 * @param sExpectedValue
	 * @param sAcutualValue
	 * @param sId
	 * @function Check whether the element is present in the applicaiton(element should present) and populate the results
	 * @return
	 * @throws Exception
	 */
/*	public static boolean ElementExistOrNotTrue(String sCase, String sExpectedValue, String sAcutualValue, By sId) throws Exception
	{
		boolean status = driver.findElements(sId).size()!=0;
		if(status == true)
		{
			HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), ""+sCase+": '"+sExpectedValue+"'", ""+sCase+": '"+sAcutualValue+"'", "PASS");
			status = true;
		}else{
			HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), ""+sCase+": '"+sExpectedValue+"'", ""+sCase+": '"+sAcutualValue+"' is not present", "FAIL");
			status = false;
		}
		return status;
	}*/
	
	/**
	 * @function this function will quite the browser
	 * @throws Exception
	 */
/*	public static void Terminate() throws Exception
    { 
		String execution = HTML.properties.getProperty("TypeOfAutomation");
		
		if (execution.toUpperCase().contains("HEADLESS"))
		{
			service.stop();
			logger.info("phantomjs service stoped");
		}
		else
		{
			driver.quit();
			logger.info("WebDriver Quit");
		}
		ScreenVideoCapture.stopVideoCapture(HTML.properties.getProperty("testcasename"));
    }
	*/
	/**
	 * @function This function used to take the SS where its required
	 * @throws IOException
	 */
  /*  public static void TerminationScreenShot() throws IOException
    {                    
           File directory = new File (".");
           File reportFile;
           int number = 0;
           Date currDate = new Date();
           SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
           String date = dateFormat.format(currDate);
           do {
                        reportFile = new File(directory.getCanonicalPath() + "\\Reports\\HTMLReports\\ScreenShot" + date +"_"+ number + ".png");
                        number++;
              } 
                  while (reportFile.exists());
                  File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                  FileUtils.copyFile(screenshot, reportFile);
    }*/
    
    /**
     * 
     * @param intTestCaseID,intTestSetID,FLAG_EVALFAIL,strAttachmentFilePath,strAttachmentDesc,strUserName,strPassword,sQCURL,sQCURL,sProject
     * @function This function use to update the test results in ALM
     * @throws IOException
     */
/*	public static void RunScript(String intTestCaseID, String intTestSetID, String FLAG_EVALFAIL, String strAttachmentFilePath, String strAttachmentDesc,String strUserName,String strPassword, String sQCURL, String sDomain, String sProject, String sDraftRun) throws IOException{
		//http://stackoverflow.com/questions/14711490/pass-arguments-to-vbs-from-java
		File directory = new File (".");
		String sConfigfilespath = directory.getCanonicalPath()+"\\VBScript\\UpdateALM.vbs"; 
			try 
			{
				String[] parms = {"wscript", sConfigfilespath, intTestCaseID, intTestSetID, FLAG_EVALFAIL, strAttachmentFilePath, strAttachmentDesc,strUserName,strPassword,sQCURL,sDomain,sProject,sDraftRun};
				Runtime.getRuntime().exec(parms);
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
	}*/
	
	/**
	 * @function This function use to send a mail after the test with the results
	 * @throws IOException
	 */
	public static void SendMail(String strMailTo,String strMailCC,String strSummaryFileName,String sTime, String strRelease,String strModuleName,String g_SummaryTotal_TC,String g_SummaryTotal_Pass,String g_SummaryTotal_Fail,String strEnvironment) throws IOException{
		//http://stackoverflow.com/questions/14711490/pass-arguments-to-vbs-from-java
		File directory = new File (".");
		String sConfigfilespath = directory.getCanonicalPath()+"\\VBScript\\SendMail.vbs"; 
		System.out.println("sconfigspath in common java " +sConfigfilespath );
			try 
			{
				String[] parms = {"wscript", sConfigfilespath, strMailTo,strMailCC,strSummaryFileName,sTime,strRelease,strModuleName, g_SummaryTotal_TC,g_SummaryTotal_Pass,g_SummaryTotal_Fail,strEnvironment};
				Runtime.getRuntime().exec(parms);
				
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
	}
	
	/**
	 * @function this function use to find the element size
	 * @param byLocater
	 * @return
	 */
/*	public static int ElementSize(By byLocater)
	{
		//boolean status = false;
		int size = driver.findElements(byLocater).size();
		return size;
		
	}*/
	
	/**
	 * @function This function use to update the data in the excel sheet
	 * @param sFilename
	 * @param sQuery
	 * @return
	 * @throws Exception
	 */
	public static boolean UpdateQuery(String sFilename, String sQuery) throws Exception
	{
		boolean status = false;
		try
		{
			Fillo fillo=new Fillo();
			File directory = new File (".");
			String sConfigfilespath = directory.getCanonicalPath()+"\\Data\\"+sFilename+".xlsm";
			Connection connection=fillo.getConnection(sConfigfilespath);
			String strQuery= sQuery;
			connection.executeUpdate(strQuery);
			connection.close();		
			status = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	/**
	 * @function Common function for all the screen class
	 * @param Sheetname
	 * @param o
	 * @return
	 */
	/*public static Boolean ClassComponent(String Sheetname, Elements o) 
	{
		XlsxReader sXL;
		boolean tcAvailability = true;
		String sheetname = Sheetname;
		sXL = new XlsxReader(HTML.properties.getProperty("DataSheetName"));
		Boolean status = true;
		try
		{
			int rowcount = sXL.getRowCount(sheetname);
			  for(int i = 2; i <= rowcount; i++)
			  {
				  if(sXL.getCellData(sheetname, "ID", i).equals(HTML.properties.getProperty("TCID")))
				  {
					  tcAvailability = false;
						int colcount = sXL.getColumnCount(sheetname);
						for(int j = 2; j <= colcount; j++)
						{
								String ColName = sXL.getCellData(sheetname, j, 1);
								if (!ColName.isEmpty())
				  				{
						  				String value = sXL.getCellData(sheetname, j, i);
						  				String element = ColName.substring(0, 3);
						  				if (element.contentEquals("mel") || element.contentEquals("fun") || element.contentEquals("cfu") || element.contentEquals("zed") || element.contentEquals("edt") || element.contentEquals("btn")  || element.contentEquals("ele") || element.contentEquals("lst") || element.contentEquals("pwd") || element.contentEquals("dbl") || element.contentEquals("scl") || element.contentEquals("rdo") || element.contentEquals("chk") || element.contentEquals("clr") || element.contentEquals("elj")  || element.contentEquals("ofu") || element.contentEquals("edw") || element.contentEquals("bli") || (element.contentEquals("drp")))
						  				{
						  					if ((!value.equals(""))) 
						  					{
						  						String ClassName  =null;
						  						if(ColName.toUpperCase().contains("FUN") || ColName.toUpperCase().contains("OFU") )
						  						{
						  							ClassName  = "com.pc.screen." + sheetname;          
						  						}
						  						if(ColName.toUpperCase().contains("CFU"))
						  						{
						  							ClassName  = "com.pc.screen." + "SCRCommon";
						  						}
						  						if(ColName.toUpperCase().contains("FUN") || ColName.toUpperCase().contains("CFU") || ColName.toUpperCase().contains("OFU"))
						  						{
						  								String methodName = ColName.substring(3);
						  								if(value.toUpperCase().equals("YES"))
								  						{
															Class noparams[] = {};
															Class cls = Class.forName(ClassName);
															Object obj = cls.newInstance();
															Method method = cls.getDeclaredMethod(methodName, noparams);
															status = (Boolean)method.invoke(obj, null);
								  						}
						  								else
						  								{
						  									if(ColName.toUpperCase().endsWith("PAGE"))
		                                                    {
		                                                           methodName = "ODSCfun";
		                                                    }
															Class[] paramString = new Class[1];    
															paramString[0] = String.class;
															Class cls = Class.forName(ClassName);
															Object obj = cls.newInstance();
															Method method = cls.getDeclaredMethod(methodName, paramString);
															status = (Boolean)method.invoke(obj, new String(value));
								  						}
						  						}
						  						else
						  						{
									  					status = SafeAction(o.getObject(ColName), value,ColName);
						  						}
						  						if(!status)
							  					{
							  						return false;
							  					}
						  					}
						  				}
				  				}
						}
				  }
			  }
			  
			  if(tcAvailability)
			  {
				  logger.info(HTML.properties.getProperty("TCID") + ":::"+HTML.properties.getProperty("TCID")+" not available in "+Sheetname+" Sheet");
				  status = false;
			  } 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = false;
		}
		return status;
	}*/
	
	/**
	 * @function This function use to Select the data from the table and performs the action accordingly
	 * @param obj,readCol,actionCol,strReadString,actionObjetName
	 * @return status
	 * @throws Exception
	 */
	/*public static Boolean ActionOnTable(By obj, int readCol, int actionCol, String strReadString, String actionObjetName, String sTagName) throws Exception
	{	
	  boolean Status=false;
	  boolean SearchString=false;
	  boolean ActionObject=false;
	  WebElement mytable = driver.findElement(obj);
	  List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
	  for(int i = 0; i <= allrows.size()-1; i++)
	  {
		  List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
		  String readText = Cells.get(readCol).getText();
		  if (readText.contains(strReadString))
		  {
			  SearchString = true;
			  List<WebElement> CellElements = Cells.get(actionCol).findElements(By.tagName(sTagName));
	          for(WebElement element: CellElements)
	          {
	        	  String objName = element.getText();
	        	  if(objName.contains(actionObjetName))
        	  		{
	        		  	Status = true;
	        		  	ActionObject = true;
            			element.click();
            			break;
	            	}
	          }
	     }
		 if(ActionObject == true)
		 {
			 break;
		 }
	  }
	  if(SearchString)
	  {
		  	logger.info("Search String available in the table. '" + strReadString + "'");  
		  	HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and srarch string is  '" + actionObjetName + "'", "PASS");
			if(ActionObject)
			  {
					logger.info("Search and click on object in the table cell and object name is '" + actionObjetName + "'");  
				  	HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + actionObjetName + "'","System searched object in the table and clicked on object. object name is '" + actionObjetName + "'", "PASS");
					Status = true;					  
			  }
			  else
			  {
					logger.info("Search and click on object in the table cell and object name is '" + actionObjetName + "'");  
				  	HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + actionObjetName + "'","System searched object in the table and clicked on object. object name is '" + actionObjetName + "'", "FAIL");
					Status = false;
			  }
	  }
	  else
	  {
		  	logger.info("Search String not available in the table. '" + strReadString + "'");  
			HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and srarch string is  '" + actionObjetName + "'", "FAIL");
			Status = false;
	  }
	  return Status;
	}*/
	
	/**
	 * 
	 * @param obj,readTextCol,getTextCol,strReadString
	 * @function this function use to get the text the table
	 * @return String
	 * @throws Exception
	 */
/*	public static String GetTextFromTable(By obj, int readTextCol, int getTextCol, String strReadString) throws Exception
	{	
		  String text = null;
		  WebElement mytable = driver.findElement(obj);
		  List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		  for(int i = 0; i <= allrows.size()-1; i++)
		  {
			  List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
			  String readText = Cells.get(readTextCol).getText();
			  if (readText.contains(strReadString))
			  {
				  text = Cells.get(getTextCol).getText();
		     }        
		   }
		  return text;
	}*/
	
	/**
	 * 
	 * @param obj,getTextRow,getTextRow
	 * @function this function use to get the text the table
	 * @return String
	 * @throws Exception
	 */
/*	public static String GetTextFromTable(By obj, int getTextRow, int getTextCol) throws Exception
	{	
		  String text = null;
		  WebElement mytable = driver.findElement(obj);
		  List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		  List<WebElement> Cells = allrows.get(getTextRow).findElements(By.tagName("td"));
		  text = Cells.get(getTextCol).getText();
		  return text;
	}*/
	
	/**
	 * @function This function use to get the text from the table according to the column
	 * @param obj
	 * @param readTextCol
	 * @param strReadString
	 * @return
	 * @throws Exception
	 */
	/*public static String GetTextFromTable(By obj, int readTextCol, String strReadString) throws Exception
	{	
		  boolean SearchString = false;
		  String readText = null;
		  WebElement mytable = driver.findElement(obj);
		  List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		  for(int i = 0; i <= allrows.size()-1; i++)
		  {
			  List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
			  readText = Cells.get(readTextCol).getText(); 
			  if(readText.contains(strReadString))
			  {
				  SearchString = true;
				  break;
			  }
		   }
		  if(SearchString)
		  {
			  	logger.info("Search String available in the table. '" + strReadString + "'");  
			  	HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and search string is  '" + strReadString + "'", "PASS");
		  }
		  else
		  {
			  	logger.info("Search String not available in the table. '" + strReadString + "'");  
				HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and search string is  '" + strReadString + "'", "FAIL");
				SearchString = false;
		  }
		  return readText;
	}	*/
	
	/**
	 * @function This function use to start the driver script
	 * @param strRunMode
	 * @param strTestCaseName
	 * @param DataSheetName
	 * @param Region
	 * @throws Exception
	 */
	/*//public static void RunTest(String strRunMode, String strTestCaseName, String DataSheetName, String Region, String Browser) throws Exception
	{
		Date d = new Date();
		System.out.println("Start Time--------------------------------------------" + d);
	//	PropertyConfigurator.configure("log4j.properties");
		HTML.fnSummaryInitialization("Execution Summary Report");	
	//	logger.info("-----------------STARTED RUNNING TESTNG METHOD-----------------");
	      boolean strYES = true; 
	      Boolean status = true;
	      //DataSheetName = "Data";
	      String strColumnName = null;
	      String strCondition = null;
	      if(!Region.isEmpty())
	      {
	    	  HTML.properties.setProperty("Region",Region);
	      } 
          if(!Browser.isEmpty()) 
          { 
              HTML.properties.setProperty("Browser",Browser); 
          }
	      HTML.properties.setProperty("DataSheetName",DataSheetName);
		  if(strRunMode.contains("RunModeYes"))
		  {
			  strColumnName = "Execution";
			  strCondition = "YES";
		  }
		  else if(strRunMode.contains("RunModeNo"))
		  {
			  strColumnName = "TestCaseName";
			  strCondition = strTestCaseName;
		  }
		  sXL = new XlsxReader(DataSheetName);
		  String sheetname = "TestCase";
		  int rowcount = sXL.getRowCount(sheetname);
		  for(int i = 2; i <= rowcount; i++)
		  {		
//			  if(sXL.getCellData(sheetname, strColumnName, i).equalsIgnoreCase(strCondition))
			  if(sXL.getCellData(sheetname, strColumnName, i).equalsIgnoreCase(strCondition))
			  {
				  boolean ScriptLevelStatus = true;
				  strYES = false; 
				  TCID  = sXL.getCellData(sheetname, "ID", i);
				  TestCaseID  = sXL.getCellData(sheetname, "TestCaseID", i);
				  TestSetID  = sXL.getCellData(sheetname, "TestSetID", i);
				  HTML.properties.setProperty("TCID",TCID);			  
				  HTML.properties.setProperty("Row",String.valueOf(i));
				  HTML.properties.setProperty("TestCaseID",TestCaseID);
				  HTML.properties.setProperty("TestSetID",TestSetID);
				  testcasename = sXL.getCellData(sheetname, "TestCaseName", i);
				  HTML.properties.setProperty("testcasename",testcasename);
				  HTML.fnInitilization(testcasename);	
				//  logger.info("-----------------STARTED RUNNING TEST CASE " + testcasename + " EXECUTION-----------------");
		  			int colcount = sXL.getColumnCount(sheetname);
		  			for(int j = 2; j <= colcount; j++)
		  			{
		  				try
						  {
		  						String ColName = sXL.getCellData(sheetname, j, 1);
						  		if(ColName.contains("Component"))
								  {
						  				TCRow = i;
						  				methodName = sXL.getCellData(sheetname, j, i);
						  				HTML.properties.setProperty("methodName",methodName);
						  				if (!methodName.isEmpty())
						  				{
											//no paramater
											Class noparams[] = {};
										     //load the AppTest at runtime
											Class cls = Class.forName("com.pc.screen." + methodName);
											Object obj = cls.newInstance();
											//logger.info("---------------Started Executing " + methodName + " function---------------");	
											HTML.fnInsertResult(testcasename, methodName, "Component should start execution","Started Executing " + methodName + " Component", "PASS");
											//call the printIt method
											Method method = cls.getDeclaredMethod("SCR" + methodName, noparams);
											status = (Boolean)method.invoke(obj, null);
										//	logger.info("---------------Completed Executing " + methodName + " function---------------");	
											HTML.fnInsertResult(testcasename, methodName, "Component execution should end","Completed Executing " + methodName + " Component", "PASS");
											if(!status)
						  					{
												ScriptLevelStatus = false;
												break; 
						  					}
						  				}
					  			}
						  }
						  catch(Exception e)
						  {
							  	ScriptLevelStatus = false;
								break; 
						  }
		  			}
					if(ScriptLevelStatus)
					{
						//logger.info("-----------------ENDED RUNNING TEST CASE " + testcasename + " EXECUTION-----------------");
						HTML.fnSummaryInsertTestCase();
						Common.Terminate();
					}
					else
					{
						Common.TerminationScreenShot();
						//logger.info("---------------Error in executing " + methodName + " function---------------");
						HTML.fnInsertResult(testcasename, methodName, "Component should run properly", "Error in: '" + methodName + "'", "FAIL");
						HTML.fnSummaryInsertTestCase();
						Common.Terminate();
					}
			  }
		  }
		  if(strYES)
		  {
			  //logger.info("No test case selected as 'YES' in Data sheet");
			  System.out.println("No Test case selected as 'YES' in Data sheet");
		  }
		  HTML.fnSummaryCloseHtml(HTML.properties.getProperty("Release"));
		  Date dd = new Date();
		  System.out.println("End Time--------------------------------------------" + dd);
	}
	*/
	/*public static Recordset GetDataFromExcel(String strFileName, String strQuery1) throws FilloException
    {
           Fillo fillo=new Fillo();
           Connection connection=(Connection) fillo.getConnection(strFileName);
           Recordset recordset = connection.executeQuery(strQuery1);
           return recordset;
    }
  
	public static void UpdateDataInExcel(String strFileName, String strQuery) throws FilloException
    {
         Fillo fillo=new Fillo();
         Connection connection=fillo.getConnection(strFileName);
         connection.executeUpdate(strQuery);
         connection.close();
    }
	public static void UpdateStatusInExcel(String strFileName, String strQuery) throws FilloException
    {
         Fillo fillo=new Fillo();
         Connection connection=fillo.getConnection(strFileName);
         connection.executeUpdate(strQuery);
         connection.close();
    }*/
	
	/**
     * @function This function use to retrieve Product Select Shell / SOR
     * @return String
     * @throws Exception
     */
    /* public static String getSpecifiedExcelValue(String strSheetName,String strProductSelection) throws Exception
     {
            String strProduct = "";
            boolean blnFlag = false;
            sXL = new XlsxReader(HTML.properties.getProperty("DataSheetName"));
            int rowcount = sXL.getRowCount(strSheetName);
            try
            {
                   for(int i=2;i<=rowcount;i++)
                   {
                         String value = sXL.getCellData(strSheetName, 0, i);                  
                         if(!value.isEmpty())
                         {
                                if(HTML.properties.getProperty("TCID").equals(value))
                                {      
                                       int colcount = sXL.getColumnCount(strSheetName);
                                       for(int j = 2; j <= colcount; j++)
                                       {
	                                             String ColName = sXL.getCellData(strSheetName, j, 1);
	                                             if(ColName.equals(strProductSelection))
	                                             {
	                                                    strProduct = sXL.getCellData(strSheetName, j, i);
	                                                    blnFlag = true;
	                                                    break;
	                                             }
                                       }
                                }
                         }
                         if (blnFlag == true)
                         {
                                break;
                         }
                   }      
            }
            catch (Exception e)
            {
                   blnFlag = false;
                   e.printStackTrace(); 
            }
            return strProduct;
     }*/
}