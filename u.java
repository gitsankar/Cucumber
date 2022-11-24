package com.tes.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Utilities --- Class for utility functions
 * 
 * @author Prasad Joshi
 */

public class Utilities {

	/*
	 * private String strAbsolutepath = new File("").getAbsolutePath(); private
	 * String strDataPath = strAbsolutepath + "/src/com/tes/data/";
	 * private String strScriptPath = strAbsolutepath +
	 * "/src/com/tes/scripts/"; private String strResultPath =
	 * strAbsolutepath + "/src/com/tes/results/";
	 */
	private static Map<String, String> envVariableMap = new HashMap();
	private String strAbsolutepath = new File("").getAbsolutePath();
	private String strDataPath = strAbsolutepath + "/data/";
	private String strScriptPath = strAbsolutepath + "/src/com/tes/scripts/";
	private String strResultPath = strAbsolutepath + "/results/";
	private int totalPagesScanned = 0;
	Reporter reporter;
	GridReporter reporter1;

	public Utilities(Reporter reporter) {
		this.reporter = reporter;
	}

	public Utilities(GridReporter reporter1) {
		this.reporter1 = reporter1;
	}

	public int getTotalPagesScanned() {
		return totalPagesScanned;
	}

	public void incrementPagesScanned() {
		totalPagesScanned++;
	}

	public String getAbsolutePath() {
		return strAbsolutepath;
	}

	public String getDataPath() {
		return strDataPath;
	}

	public String getScriptPath() {
		return strScriptPath;
	}

	public String getResultPath() {
		return strResultPath;
	}

	public static String getEnvironmentVariable(String strVariableName) {
		return envVariableMap.get(strVariableName);

	}

	public static void setEnvironmentVariable(String strVariableName, String strValue) {
		envVariableMap.put(strVariableName, strValue);

	}

	public String getConfigValues(String strKey) {
		Map<String, String> configMap = new HashMap();

		String strURL = null;
		String strApplicationName = null;
		String strBrowser = null;
		String strCBTFlag = "false";
		String strConfigFile = "Config File";
		String strDataFileName = "Data File";
		String strDatFilePath = "Data File Path";
		String strScriptFileName = "Script File";
		String strDataSource = "Text";
		String strScreenshotFlag = "All";
		String strExecutionConfigFileName = "Execution Configuration File";
		String strConfigFileName = strDataPath + "Cafe.config";
		// List<String> lstComponents = new ArrayList<String>(0);

		Scanner Scaning;
		try {
			Scaning = new Scanner(new FileReader(new File(strConfigFileName)));

			while (Scaning.hasNextLine()) {
				Scanner scan = new Scanner(Scaning.nextLine());
				scan.useDelimiter("=");
				if (scan.hasNext()) {
					String name = scan.next();
					String value = scan.next();
					if (name.trim().equalsIgnoreCase("Application URL")) {
						strURL = value;
						configMap.put("Application URL", strURL.trim());
					}
					if (name.trim().equalsIgnoreCase("Application Name")) {
						strApplicationName = value;
						configMap.put("Application Name", strApplicationName.trim());
					}
					if (name.trim().equalsIgnoreCase("Browser Type")) {
						strBrowser = value;
						configMap.put("Browser Type", strBrowser.trim());
					}
					if (name.trim().equalsIgnoreCase("Data Source")) {
						strDataSource = value;
						configMap.put("Data Source", strDataSource.trim());
					}
					if (name.trim().equalsIgnoreCase("Screenshot Flag")) {
						strScreenshotFlag = value;
						configMap.put("Screenshot Flag", strScreenshotFlag.trim());
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		configMap.put(strDataFileName, strDataPath + strApplicationName.trim() + "_Data.txt");
		configMap.put(strScriptFileName, strScriptPath + strApplicationName.trim() + ".java");
		configMap.put(strExecutionConfigFileName, strDataPath + "/" + strApplicationName.trim() + "/" + strApplicationName.trim() + "_Execution.config");
		configMap.put(strConfigFile, strConfigFileName);
		configMap.put(strDatFilePath, strDataPath);

		if (configMap.get("Data Source") == null) {
			configMap.put("Data Source", "Text");
		}

		return configMap.get(strKey);
	}

	public void setBrowserType(String strBrowserType) {

	}

	public String getDataFile(String strModule, String strComponent) {
		String strDataFileName = null;
		try {
			strDataFileName = strModule + "_" + strComponent;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return strDataFileName;
	}

	public String getDataFile(String strModule, String strComponent, String strThreadId) {
		String strDataFileName = null;
		try {
			System.out.println("strThreadId" + strThreadId);
			strDataFileName = strModule + "_" + strComponent + "_" + strThreadId;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return strDataFileName;
	}

	public void setDataFile(String strModuleName, String strComponentName) {
		String strDataSource = getConfigValues("Data Source");
		String strDataFile = strDataPath + "DatafileName.txt";
		FileWriter fwo;
		try {
			fwo = new FileWriter(strDataFile);
			BufferedWriter bwObj = new BufferedWriter(fwo);
			bwObj.write(strModuleName + "_" + strComponentName);
			/*
			 * if(strDataSource.equalsIgnoreCase("Excel") ||
			 * strDataSource.equalsIgnoreCase("xls")){ bwObj.write(strModuleName
			 * +"_" + strComponentName); } else{ bwObj.write(strModuleName + "_"
			 * + strComponentName + "_Data.txt"); }
			 */

			bwObj.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setDataFile(String strModuleName, String strComponentName, String strThreadId) {
		String strDataSource = getConfigValues("Data Source");
		String strDataFile = strDataPath + "DatafileName_" + strThreadId + ".txt";
		FileWriter fwo;
		try {
			fwo = new FileWriter(strDataFile);
			BufferedWriter bwObj = new BufferedWriter(fwo);
			bwObj.write(strModuleName + "_" + strComponentName);
			/*
			 * if(strDataSource.equalsIgnoreCase("Excel") ||
			 * strDataSource.equalsIgnoreCase("xls")){ bwObj.write(strModuleName
			 * +"_" + strComponentName); } else{ bwObj.write(strModuleName + "_"
			 * + strComponentName + "_Data.txt"); }
			 */

			bwObj.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getDataFile(String strComponent) {

		String strDataFileNamePath = strDataPath + "DatafileName.txt";
		String strLine = null;
		String strDataFileName = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(strDataFileNamePath));
			while ((strLine = br.readLine()) != null) {
				strDataFileName = strLine;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String strDataFile = null;
		String strDataSource = getConfigValues("Data Source");

		if (strDataSource.equalsIgnoreCase("Excel") || strDataSource.equalsIgnoreCase("xls")) {
			// strDataFile = strDataFileName + "_" + strComponent + "_Data";
			strDataFile = strDataFileName;
		} else {
			Map<String, String> configMap = new HashMap();
			Utilities utils = new Utilities(reporter);
			List<String> lstComponents = new ArrayList<String>(0);
			String strApplicationName = null;
			// strApplicationName = utils.getConfigValues("Application Name");

			// lstComponents = utils.getComponentNames();
			/*
			 * Modified for GP lstComponents = utils.getExecutionComponent();
			 * String strComponentDataFile = null; String strComponentName =
			 * null; for(int component = 0; component<lstComponents.size();
			 * component++){ strComponentName =
			 * lstComponents.get(component).trim(); //strComponentDataFile =
			 * utils.getConfigValues("Data File Path") + strApplicationName +
			 * "_" + strComponentName + "_Data.txt"; strComponentDataFile =
			 * utils.getConfigValues("Data File Path") + strDataFileName;
			 * configMap.put(strComponentName, strComponentDataFile);
			 * 
			 * } strDataFile = configMap.get(strComponent); Modification ends
			 */
			strDataFile = strDataFileName;

		}
		return strDataFile;
	}

	public String getDataFileInfo() {

		String strDataFileNamePath = strDataPath + "DatafileName.txt";
		String strLine = null;
		String strDataFileName = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(strDataFileNamePath));
			while ((strLine = br.readLine()) != null) {
				strDataFileName = strLine;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strDataFileName;
	}

	public String getDataFileInfo(String strThreadId) {

		String strDataFileNamePath = strDataPath + "DatafileName_" + strThreadId + ".txt";
		String strLine = null;
		String strDataFileName = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(strDataFileNamePath));
			while ((strLine = br.readLine()) != null) {
				strDataFileName = strLine;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strDataFileName;
	}

	public List<String> getComponentNames() {

		String strConfigFileName = strDataPath + "Cafe.config";
		List<String> lstComponents = new ArrayList<String>(0);

		Scanner Scaning;
		try {
			Scaning = new Scanner(new FileReader(new File(strConfigFileName)));

			while (Scaning.hasNextLine()) {
				Scanner scan = new Scanner(Scaning.nextLine());
				scan.useDelimiter("=");
				if (scan.hasNext()) {
					String name = scan.next();
					String value = scan.next();

					if (name.trim().equalsIgnoreCase("Component Name")) {
						lstComponents.add(value.trim());
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstComponents;
	}

	public int getRow(String strComponentName) {
		String strKey = null;
		String strValue = null;
		// int expectedTokenNumber = 0;
		String strExecutionConfigFileName = null;
		Map<String, String> executionConfigMap = new HashMap<String, String>();
		int executionRowNumber = 0;
		Utilities utils = new Utilities(reporter);
		String strDelimiter = "##";
		String strRowNumber = "1";

		String strDataSource = utils.getConfigValues("Data Source");

		if (strDataSource.equalsIgnoreCase("Excel") || strDataSource.equalsIgnoreCase("xls")) {
			String strExcelDataFileName = strDataPath + utils.getConfigValues("Application Name") + "_Data.xls";
			POIFSFileSystem fs;
			String strCellValue = null;
			boolean isExpectedComponent = false;

			try {
				fs = new POIFSFileSystem(new FileInputStream(strExcelDataFileName));
				HSSFWorkbook workbook = new HSSFWorkbook(fs);

				HSSFSheet dataSheet = workbook.getSheet("Scenario");
				int totalRows = dataSheet.getLastRowNum();
				String strRowData = null;
				HSSFRow row = null;
				// HSSFCell cell = null;
				int totalCells = 0;
				for (int i = 0; i <= totalRows; i++) {
					row = dataSheet.getRow(i);
					totalCells = row.getLastCellNum();
					for (int j = 0; j < totalCells; j++) {
						strCellValue = row.getCell(j).toString();
						if (strCellValue.equals(strComponentName)) {
							isExpectedComponent = true;
							strCellValue = row.getCell(j + 1).toString();
							strRowNumber = strCellValue;
							break;
						}
					}
					if (isExpectedComponent) {
						break;
					}
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				executionRowNumber = Integer.parseInt(strRowNumber);
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
		} else {

			try {
				strExecutionConfigFileName = utils.getConfigValues("Execution Configuration File");
				BufferedReader br = new BufferedReader(new FileReader(strExecutionConfigFileName));
				String strLine = null;
				StringTokenizer st = null;
				int tokenNumber = 0;
				int rowNumber = 0;
				int counter = 0;

				while ((strLine = br.readLine()) != null) {
					rowNumber++;

					// break comma separated line using delimiter "strDelimiter"
					st = new StringTokenizer(strLine, strDelimiter);

					while (st.hasMoreTokens()) {
						tokenNumber++;
						strKey = st.nextToken();

						if (counter != 0) {
							// strKey = st.nextToken();
							strValue = st.nextToken();
							executionConfigMap.put(strKey, strValue);
							strValue = st.nextToken();
						}
					}
					// reset token number
					counter++;
					tokenNumber = 0;

				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			strRowNumber = executionConfigMap.get(strComponentName);
			try {
				executionRowNumber = Integer.parseInt(strRowNumber);
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
		}

		return executionRowNumber;
	}

	public List<String> getExecutionComponent() {
		String strComponent = null;
		List<String> lstComponents = new ArrayList<String>(0);

		String strExecutionConfigFileName = null;
		String strDelimiter = "##";

		try {
			strExecutionConfigFileName = getConfigValues("Execution Configuration File");
			BufferedReader br = new BufferedReader(new FileReader(strExecutionConfigFileName));
			String strLine = null;
			StringTokenizer st = null;
			int tokenNumber = 0;
			int rowNumber = 0;
			int counter = 0;

			while ((strLine = br.readLine()) != null) {
				rowNumber++;

				// break comma separated line using delimiter "strDelimiter"
				st = new StringTokenizer(strLine, strDelimiter);

				while (st.hasMoreTokens()) {
					tokenNumber++;
					strComponent = st.nextToken();
					if (counter != 0) {
						lstComponents.add(strComponent);
						strComponent = st.nextToken();
						strComponent = st.nextToken();
					}

				}
				// reset token number
				counter++;
				tokenNumber = 0;

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstComponents;
	}

	public String getExecutionFlag(String strComponentName) {

		String strKey = null;
		String strValue = null;
		String strExecute = null;

		String strExecutionConfigFileName = null;
		Map<String, String> executionConfigMap = new HashMap<String, String>();
		Utilities utils = new Utilities(reporter);
		String strDelimiter = "##";

		String strDataSource = utils.getConfigValues("Data Source");

		if (strDataSource.equalsIgnoreCase("Excel") || strDataSource.equalsIgnoreCase("xls")) {
			String strExcelDataFileName = strDataPath + utils.getConfigValues("Application Name") + "_Data.xls";
			POIFSFileSystem fs;
			String strCellValue = null;
			boolean isExpectedComponent = false;

			try {
				fs = new POIFSFileSystem(new FileInputStream(strExcelDataFileName));
				HSSFWorkbook workbook = new HSSFWorkbook(fs);

				HSSFSheet dataSheet = workbook.getSheet("Scenario");
				int totalRows = dataSheet.getLastRowNum();
				String strRowData = null;
				HSSFRow row = null;
				// HSSFCell cell = null;
				int totalCells = 0;
				for (int i = 0; i <= totalRows; i++) {
					row = dataSheet.getRow(i);
					totalCells = row.getLastCellNum();
					for (int j = 0; j < totalCells; j++) {
						strCellValue = row.getCell(j).toString();
						if (strCellValue.equals(strComponentName)) {
							isExpectedComponent = true;
							strCellValue = row.getCell(j + 2).toString();
							strExecute = strCellValue;
							break;
						}
					}
					if (isExpectedComponent) {
						break;
					}
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			try {
				strExecutionConfigFileName = utils.getConfigValues("Execution Configuration File");
				BufferedReader br = new BufferedReader(new FileReader(strExecutionConfigFileName));
				String strLine = null;
				StringTokenizer st = null;
				int tokenNumber = 0;
				int rowNumber = 0;
				int counter = 0;

				while ((strLine = br.readLine()) != null) {
					rowNumber++;

					// break comma separated line using delimiter "strDelimiter"
					st = new StringTokenizer(strLine, strDelimiter);

					while (st.hasMoreTokens()) {
						tokenNumber++;
						strKey = st.nextToken();

						if (counter != 0) {
							// strKey = st.nextToken();
							strValue = st.nextToken();
							strValue = st.nextToken();
							executionConfigMap.put(strKey, strValue);
						}
					}
					// reset token number
					counter++;
					tokenNumber = 0;

				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			strExecute = executionConfigMap.get(strComponentName);
		}

		return strExecute;
	}

	public void verifyElementPresent(RemoteWebDriver webDriver, String strElement) {

		boolean exists = false;
		CreateResult result = new CreateResult();

		for (int interval = 0; interval < 30; interval++) {
			if (webDriver.findElementsByName(strElement).size() != 0 && webDriver.findElementByName(strElement).isDisplayed()) {
				exists = true;
				break;
			} else if (webDriver.findElementsById(strElement).size() != 0 && webDriver.findElementById(strElement).isDisplayed()) {
				exists = true;
				break;
			} else if (webDriver.findElementsByXPath(strElement).size() != 0 && webDriver.findElementByXPath(strElement).isDisplayed()) {
				exists = true;
				break;
			} else {
				exists = false;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// String strBrowserType = utils.getConfigValues("Browser Type");

		if (exists) {
			exists = true;
			result.takeScreenshot("Login", "Verfy element " + strElement, "Pass", "Pass", "Verfy element " + strElement, true, "Pass", webDriver);
		} else {
			exists = true;
			result.takeScreenshot("Login", "Verfy element " + strElement, "Pass", "Pass", "Verfy element " + strElement, true, "Fail", webDriver);
		}

	}

	public boolean verifyLinkPresent(RemoteWebDriver webDriver, String strElement) {

		boolean exists = false;

		for (int interval = 0; interval < 30; interval++) {
			if (webDriver.findElementsByLinkText(strElement).size() != 0 && webDriver.findElementByLinkText(strElement).isDisplayed()) {
				exists = true;
				break;
			} else if (webDriver.findElementsByName(strElement).size() != 0 && webDriver.findElementByName(strElement).isDisplayed()) {
				exists = true;
				break;
			} else if (webDriver.findElementsById(strElement).size() != 0 && webDriver.findElementById(strElement).isDisplayed()) {
				exists = true;
				break;
			} else if (webDriver.findElementsByXPath(strElement).size() != 0 && webDriver.findElementByXPath(strElement).isDisplayed()) {
				exists = true;
				break;
			} else {
				exists = false;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// String strBrowserType = utils.getConfigValues("Browser Type");

		if (exists) {
			return exists;
		} else {
			return exists;
		}

	}

	public boolean verifyElementText(RemoteWebDriver webDriver, String strElement, String strElementProperty, String strExpectedText) {

		verifyElementPresent(webDriver, strElement);
		String strActualText = null;

		if (strElementProperty.equalsIgnoreCase("id")) {
			strActualText = webDriver.findElementById(strElement).getText();
		}

		if (strElementProperty.equalsIgnoreCase("name")) {
			strActualText = webDriver.findElementByName(strElement).getText();
		}

		if (strElementProperty.equalsIgnoreCase("xpath")) {
			strActualText = webDriver.findElementByXPath(strElement).getText();
		}

		if (strActualText.equals(strExpectedText)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean verifyListValue(RemoteWebDriver webDriver, String strElement, String strElementProperty, String strExpectedValue) {

		verifyElementPresent(webDriver, strElement);
		WebElement listbox = null;

		if (strElementProperty.equalsIgnoreCase("id")) {
			listbox = webDriver.findElementById(strElement);
		}

		if (strElementProperty.equalsIgnoreCase("name")) {
			listbox = webDriver.findElementByName(strElement);
		}

		if (strElementProperty.equalsIgnoreCase("xpath")) {
			listbox = webDriver.findElementByXPath(strElement);
		}

		List<WebElement> options = listbox.findElements(By.tagName("option"));
		for (WebElement option : options) {
			if (option.getText().equals(strExpectedValue))
				return true;
		}
		return false;
	}

	public boolean verifyListValues(RemoteWebDriver webDriver, String strElement, String strElementProperty, String strExpectedValues) {

		verifyElementPresent(webDriver, strElement);
		WebElement listbox = null;
		String[] arrListValues = strExpectedValues.split(";");
		int counter = 0;

		if (strElementProperty.equalsIgnoreCase("id")) {
			listbox = webDriver.findElementById(strElement);
		}

		if (strElementProperty.equalsIgnoreCase("name")) {
			listbox = webDriver.findElementByName(strElement);
		}

		if (strElementProperty.equalsIgnoreCase("xpath")) {
			listbox = webDriver.findElementByXPath(strElement);
		}

		List<WebElement> options = listbox.findElements(By.tagName("option"));
		for (int i = 0; i < arrListValues.length; i++) {
			for (WebElement option : options) {
				if (option.getText().equals(arrListValues[i]))
					counter++;
				break;
			}
		}
		if (counter == arrListValues.length) {
			return true;
		} else {
			return false;
		}
	}

	public boolean verifySelectedListValue(RemoteWebDriver webDriver, String strElement, String strElementProperty, String strExpectedSelectedValue) {

		verifyElementPresent(webDriver, strElement);
		WebElement listbox = null;

		if (strElementProperty.equalsIgnoreCase("id")) {
			listbox = webDriver.findElementById(strElement);
		}

		if (strElementProperty.equalsIgnoreCase("name")) {
			listbox = webDriver.findElementByName(strElement);
		}

		if (strElementProperty.equalsIgnoreCase("xpath")) {
			listbox = webDriver.findElementByXPath(strElement);
		}

		List<WebElement> options = listbox.findElements(By.tagName("option"));
		for (WebElement option : options) {
			if (option.isSelected())
				return true;
		}
		return false;
	}

	public boolean verifyCheckboxStatus(RemoteWebDriver webDriver, String strElement, String strElementProperty, String strExpectedText) {

		verifyElementPresent(webDriver, strElement);
		String strActualStatus = null;

		if (strElementProperty.equalsIgnoreCase("id")) {
			strActualStatus = webDriver.findElementById(strElement).getAttribute("checked");
		}

		if (strElementProperty.equalsIgnoreCase("name")) {
			strActualStatus = webDriver.findElementByName(strElement).getAttribute("checked");
		}

		if (strElementProperty.equalsIgnoreCase("xpath")) {
			strActualStatus = webDriver.findElementByXPath(strElement).getAttribute("checked");
		}

		if (strActualStatus.equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean verifyRadioButtonStatus(RemoteWebDriver webDriver, String strElement, String strElementProperty, String strExpectedText) {
		verifyElementPresent(webDriver, strElement);
		String strActualStatus = null;

		if (strElementProperty.equalsIgnoreCase("id")) {
			strActualStatus = webDriver.findElementById(strElement).getAttribute("checked");
		}

		if (strElementProperty.equalsIgnoreCase("name")) {
			strActualStatus = webDriver.findElementByName(strElement).getAttribute("checked");
		}

		if (strElementProperty.equalsIgnoreCase("xpath")) {
			strActualStatus = webDriver.findElementByXPath(strElement).getAttribute("checked");
		}

		if (strActualStatus.equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}

	}

	public static String now() {
		String DATE_FORMAT_NOW = "yyyy-MM-dd-hh.mm.ss";
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int year = cal.get(Calendar.YEAR);
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		Random rand = new Random();
		int num = rand.nextInt(1000);
		return sdf.format(cal.getTime());
	}

	public void copyReportFile(String strSourceFile, String strDestFile) {
		File source = new File(strSourceFile);
		File desc = new File(strDestFile);
		try {
			FileUtils.copyFile(source, desc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteSavedReport(String strSavedReport) {
		try {
			File file = new File(strSavedReport);

			if (file.delete()) {
				System.out.println(file.getName() + " is deleted!");
			} else {
				System.out.println("Delete operation is failed.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getTodaysDate() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String[] aarDate = formatter.format(date).split(" ");
		return aarDate[0];

	}

	public String getTodaysDate(String strFormat) {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat formatter = null;
		if (strFormat.equalsIgnoreCase("UK")) {
			formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		}
		if (strFormat.equalsIgnoreCase("US")) {
			formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		}
		String[] aarDate = formatter.format(date).split(" ");
		return aarDate[0];
	}

	public String addToTodaysDate(int days, String strFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, days);
		Date date = calendar.getTime();
		SimpleDateFormat formatter = null;

		if (strFormat.equalsIgnoreCase("UK")) {
			formatter = new SimpleDateFormat("dd/MM/yyyy");
		}
		if (strFormat.equalsIgnoreCase("US")) {
			formatter = new SimpleDateFormat("MM/dd/yyyy");
		}
		String[] aarDate = formatter.format(date).split(" ");
		return aarDate[0];
	}

	public String setExecutionDetails(String strHost, String strBrowserType, String strModule) {
		File file = new File(strDataPath + "ExecutionDetails.txt");
		if (file.exists()) {
			file.delete();
		}
		Date date = new Date();

		// String strRowDataFile = strDataPath + "ExecutionDetails" +
		// String.valueOf(Thread.currentThread().getId())
		// + ".txt";
		String strRowDataFile = strDataPath + "ExecutionDetails" + "1" + ".txt";
		FileWriter fwo;
		try {
			fwo = new FileWriter(strRowDataFile);
			BufferedWriter bwObj = new BufferedWriter(fwo);
			bwObj.write(strHost + "@@" + strBrowserType + "@@" + strModule);
			bwObj.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return String.valueOf(Thread.currentThread().getId());
	}

	public String getExecutionDetails(String strThreadId) {

		String strRowDataFile = strDataPath + "ExecutionDetails" + strThreadId + ".txt";
		String strLine = null;
		String strExecutionDetails = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(strRowDataFile));
			while ((strLine = br.readLine()) != null) {
				strExecutionDetails = strLine;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strExecutionDetails;
	}

	public String getDriverMethod(String strCurrentTC, String strObject) {
		// String arrFile [] = new String[10];
		// arrFile = strDataFileName.split("_");
		String strDriverMethod = null;
		// String strExcelDataFileName = strDataPath + arrFile[0] + "/" +
		// arrFile[0] + "_Data.xls";
		String strExcelDataFileName = strDataPath + "ScriptData/ScriptData.xls";
		String strComponentName = "";

		File dataFile = new File(strExcelDataFileName);
		if (!dataFile.exists()) {
			// isXLS = false;
			// strExcelDataFileName = strDataPath + arrFile[0] + "/" +
			// arrFile[0] + "_Data.xlsx";
			strExcelDataFileName = strDataPath + "ScriptData/ScriptData.xlsx";
		}

		try {
			String strCellValue = null;
			InputStream st = new FileInputStream(strExcelDataFileName);
			// XSSFWorkbook workbook1 = new XSSFWorkbook(st);
			Workbook workbook1 = WorkbookFactory.create(st);

			Sheet dataSheet = workbook1.getSheet("Object Repository");
			int totalRows = 0;
			totalRows = dataSheet.getLastRowNum();
			Row dataRow = null;
			Cell cell = null;
			int totalCells = 0;

			for (int i = 0; i <= totalRows; i++) {

				dataRow = dataSheet.getRow(i);
				if (dataRow != null) {
					cell = dataRow.getCell(0);
					if (cell != null) {
						strComponentName = cell.toString();
					}

					cell = dataRow.getCell(1);
					if (cell != null) {
						strCellValue = cell.toString();
					}
					if (strComponentName.equals(strCurrentTC) && strCellValue.equals(strObject)) {
						if (dataRow.getCell(2) != null) {
							strDriverMethod = dataRow.getCell(2).toString();
							break;
						} else {
							break;
						}
					}
				} else {
					break;
				}
			}

			if (strDriverMethod != null) {
				if (strDriverMethod.equalsIgnoreCase("id")) {
					strDriverMethod = "findElementById";
				}
				if (strDriverMethod.equalsIgnoreCase("name")) {
					strDriverMethod = "findElementByName";
				}
				if (strDriverMethod.equalsIgnoreCase("xpath")) {
					strDriverMethod = "findElementByXPath";
				}
				if (strDriverMethod.equalsIgnoreCase("linktext")) {
					strDriverMethod = "findElementByLinkText";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (strDriverMethod != null) {
			return strDriverMethod;
		} else {
			return "findElementByXPath";
		}

	}

	public String getDriverMethod(String strDataFileName, String strCurrentTC, String strObject) {
		String arrFile1[] = strDataFileName.split("#");
		List<String> columnDetailsValue = Arrays.asList(arrFile1);
		String testStr = columnDetailsValue.get(1);
		String arrFile[] = testStr.split("_");
		String strExcelDataFileName = strDataPath + arrFile1[0] + "\\" + arrFile[0] + "\\" + arrFile[0] + "_Data.xls";
		String strComponentName = "";
		String strDriverMethod = null;
		File dataFile = new File(strExcelDataFileName);
		if (!dataFile.exists()) {
			// isXLS = false;
			// strExcelDataFileName = strDataPath + arrFile[0] + "/" +
			// arrFile[0] + "_Data.xlsx";
			strExcelDataFileName = strDataPath + arrFile1[0] + "\\" + arrFile[0] + "\\" + arrFile[0] + "_Data.xls";
		}

		/*
		 * String strExcelDataFileName = strDataPath + arrFile[0] + "/" +
		 * arrFile[0] + "_Data.xls"; // String strExcelDataFileName =
		 * strDataPath + // "ScriptData/ScriptData.xls"; String strComponentName
		 * = "";
		 * 
		 * File dataFile = new File(strExcelDataFileName); if
		 * (!dataFile.exists()) { // isXLS = false; strExcelDataFileName =
		 * strDataPath + arrFile[0] + "/" + arrFile[0] + "_Data.xlsx"; //
		 * strExcelDataFileName = strDataPath + // "ScriptData/ScriptData.xlsx";
		 * }
		 */
		try {
			String strCellValue = null;
			InputStream st = new FileInputStream(strExcelDataFileName);
			// XSSFWorkbook workbook1 = new XSSFWorkbook(st);
			Workbook workbook1 = WorkbookFactory.create(st);

			Sheet dataSheet = workbook1.getSheet("Object Repository");
			int totalRows = 0;
			totalRows = dataSheet.getLastRowNum();
			Row dataRow = null;
			Cell cell = null;
			int totalCells = 0;

			for (int i = 0; i <= totalRows; i++) {

				dataRow = dataSheet.getRow(i);
				if (dataRow != null) {
					cell = dataRow.getCell(0);
					if (cell != null) {
						strComponentName = cell.toString();
					}

					cell = dataRow.getCell(1);
					if (cell != null) {
						strCellValue = cell.toString();
					}
					if (strComponentName.equals(strCurrentTC) && strCellValue.equals(strObject)) {
						if (dataRow.getCell(2) != null) {
							strDriverMethod = dataRow.getCell(2).toString();
							break;
						} else {
							break;
						}
					}
				} else {
					break;
				}
			}

			if (strDriverMethod != null) {
				if (strDriverMethod.equalsIgnoreCase("id")) {
					strDriverMethod = "findElementById";
				}
				if (strDriverMethod.equalsIgnoreCase("name")) {
					strDriverMethod = "findElementByName";
				}
				if (strDriverMethod.equalsIgnoreCase("xpath")) {
					strDriverMethod = "findElementByXPath";
				}
				if (strDriverMethod.equalsIgnoreCase("linktext")) {
					strDriverMethod = "findElementByLinkText";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (strDriverMethod != null) {
			return strDriverMethod;
		} else {
			return "findElementByXPath";
		}

	}

	public String getObjectProperty(String strCurrentTC, String strObject) {
		// String arrFile [] = new String[10];
		// arrFile = strDataFileName.split("_");

		String strObjectProperty = null;
		// String strExcelDataFileName = strDataPath + arrFile[0] + "/" +
		// arrFile[0] + "_Data.xls";
		String strExcelDataFileName = strDataPath + "ScriptData/ScriptData.xls";
		String strComponentName = "";

		File dataFile = new File(strExcelDataFileName);
		if (!dataFile.exists()) {
			// isXLS = false;
			// strExcelDataFileName = strDataPath + arrFile[0] + "/" +
			// arrFile[0] + "_Data.xlsx";
			strExcelDataFileName = strDataPath + "ScriptData/ScriptData.xlsx";
		}

		try {
			String strCellValue = null;
			InputStream st = new FileInputStream(strExcelDataFileName);
			// XSSFWorkbook workbook1 = new XSSFWorkbook(st);
			Workbook workbook1 = WorkbookFactory.create(st);

			Sheet dataSheet = workbook1.getSheet("Object Repository");
			int totalRows = 0;
			totalRows = dataSheet.getLastRowNum();
			Row dataRow = null;
			Cell cell = null;
			int totalCells = 0;

			for (int i = 0; i <= totalRows; i++) {
				dataRow = dataSheet.getRow(i);
				if (dataRow != null) {
					cell = dataRow.getCell(0);
					if (cell != null) {
						strComponentName = cell.toString();
					}

					cell = dataRow.getCell(1);
					if (cell != null) {
						strCellValue = cell.toString();
					}
					if (strComponentName.equals(strCurrentTC) && strCellValue.equals(strObject)) {
						if (dataRow.getCell(2) != null) {
							strObjectProperty = dataRow.getCell(2).toString();
							break;
						} else {
							break;
						}
					}
				} else {
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (strObjectProperty != null) {
			return strObjectProperty;
		} else {
			return "XPath";
		}

	}

	public String getObjectProperty(String strDataFileName, String strCurrentTC, String strObject) {
		/*
		 * String arrFile[] = strDataFileName.split("_");
		 * 
		 * String strObjectProperty = null; String strExcelDataFileName =
		 * strDataPath + arrFile[0] + "/" + arrFile[0] + "_Data.xls"; // String
		 * strExcelDataFileName = strDataPath + // "ScriptData/ScriptData.xls";
		 * String strComponentName = "";
		 * 
		 * File dataFile = new File(strExcelDataFileName); if
		 * (!dataFile.exists()) { // isXLS = false; strExcelDataFileName =
		 * strDataPath + arrFile[0] + "/" + arrFile[0] + "_Data.xlsx"; //
		 * strExcelDataFileName = strDataPath + // "ScriptData/ScriptData.xlsx";
		 * }
		 */
		String arrFile1[] = strDataFileName.split("#");
		List<String> columnDetailsValue = Arrays.asList(arrFile1);
		String testStr = columnDetailsValue.get(1);
		String arrFile[] = testStr.split("_");
		String strExcelDataFileName = strDataPath + arrFile1[0] + "\\" + arrFile[0] + "\\" + arrFile[0] + "_Data.xls";
		String strComponentName = "";
		String strObjectProperty = null;
		File dataFile = new File(strExcelDataFileName);
		if (!dataFile.exists()) {
			// isXLS = false;
			// strExcelDataFileName = strDataPath + arrFile[0] + "/" +
			// arrFile[0] + "_Data.xlsx";
			strExcelDataFileName = strDataPath + arrFile1[0] + "\\" + arrFile[0] + "\\" + arrFile[0] + "_Data.xls";
		}
		try {
			String strCellValue = null;
			InputStream st = new FileInputStream(strExcelDataFileName);
			// XSSFWorkbook workbook1 = new XSSFWorkbook(st);
			Workbook workbook1 = WorkbookFactory.create(st);

			Sheet dataSheet = workbook1.getSheet("Object Repository");
			int totalRows = 0;
			totalRows = dataSheet.getLastRowNum();
			Row dataRow = null;
			Cell cell = null;
			int totalCells = 0;

			for (int i = 0; i <= totalRows; i++) {
				dataRow = dataSheet.getRow(i);
				if (dataRow != null) {
					cell = dataRow.getCell(0);
					if (cell != null) {
						strComponentName = cell.toString();
					}

					cell = dataRow.getCell(1);
					if (cell != null) {
						strCellValue = cell.toString();
					}
					if (strComponentName.equals(strCurrentTC) && strCellValue.equals(strObject)) {
						if (dataRow.getCell(2) != null) {
							strObjectProperty = dataRow.getCell(2).toString();
							break;
						} else {
							break;
						}
					}
				} else {
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (strObjectProperty != null) {
			return strObjectProperty;
		} else {
			return "XPath";
		}

	}

	public String getObjPropertyValue(String strCurrentTC, String strObject) {
		// String arrFile [] = new String[10];
		// arrFile = strDataFileName.split("_");
		// String strExcelDataFileName = strDataPath + arrFile[0] + "/" +
		// arrFile[0] + "_Data.xls";
		String strExcelDataFileName = strDataPath + "ScriptData/ScriptData.xls";
		String strComponentName = "";
		String strObjPropertyValue = null;
		File dataFile = new File(strExcelDataFileName);
		if (!dataFile.exists()) {
			// isXLS = false;
			// strExcelDataFileName = strDataPath + arrFile[0] + "/" +
			// arrFile[0] + "_Data.xlsx";
			strExcelDataFileName = strDataPath + "ScriptData/ScriptData.xlsx";
		}

		try {
			String strCellValue = null;
			InputStream st = new FileInputStream(strExcelDataFileName);
			// XSSFWorkbook workbook1 = new XSSFWorkbook(st);
			Workbook workbook1 = WorkbookFactory.create(st);

			Sheet dataSheet = workbook1.getSheet("Object Repository");
			int totalRows = 0;
			totalRows = dataSheet.getLastRowNum();
			Row dataRow = null;
			Cell cell = null;
			int totalCells = 0;

			for (int i = 0; i <= totalRows; i++) {
				dataRow = dataSheet.getRow(i);
				if (dataRow != null) {
					cell = dataRow.getCell(0);
					if (cell != null) {
						strComponentName = cell.toString();
					}

					cell = dataRow.getCell(1);
					if (cell != null) {
						strCellValue = cell.toString();
					}
					if (strComponentName.equals(strCurrentTC) && strCellValue.equals(strObject)) {
						if (dataRow.getCell(3) != null) {
							strObjPropertyValue = dataRow.getCell(3).toString();
							break;
						} else {
							break;
						}
					}
				} else {
					break;
				}
			}
			System.out.println(strCurrentTC + "----" + strObject + "--------" + strObjPropertyValue);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (strObjPropertyValue != null) {
			return strObjPropertyValue;
		} else {
			return "";
		}

	}

	public String getObjPropertyValue(String strDataFileName, String strCurrentTC, String strObject) {
		String arrFile1[] = strDataFileName.split("#");
		List<String> columnDetailsValue = Arrays.asList(arrFile1);
		String testStr = columnDetailsValue.get(1);
		String arrFile[] = testStr.split("_");
		String strExcelDataFileName = strDataPath + arrFile1[0] + "\\" + arrFile[0] + "\\" + arrFile[0] + "_Data.xls";
		String strComponentName = "";
		String strObjPropertyValue = null;
		File dataFile = new File(strExcelDataFileName);
		if (!dataFile.exists()) {
			// isXLS = false;
			// strExcelDataFileName = strDataPath + arrFile[0] + "/" +
			// arrFile[0] + "_Data.xlsx";
			strExcelDataFileName = strDataPath + arrFile1[0] + "\\" + arrFile[0] + "\\" + arrFile[0] + "_Data.xls";
		}

		try {
			String strCellValue = null;
			InputStream st = new FileInputStream(strExcelDataFileName);
			// XSSFWorkbook workbook1 = new XSSFWorkbook(st);
			Workbook workbook1 = WorkbookFactory.create(st);

			Sheet dataSheet = workbook1.getSheet("Object Repository");
			int totalRows = 0;
			totalRows = dataSheet.getLastRowNum();
			Row dataRow = null;
			Cell cell = null;
			int totalCells = 0;

			for (int i = 0; i <= totalRows; i++) {
				dataRow = dataSheet.getRow(i);
				if (dataRow != null) {
					cell = dataRow.getCell(0);
					if (cell != null) {
						strComponentName = cell.toString();
					}

					cell = dataRow.getCell(1);
					if (cell != null) {
						strCellValue = cell.toString();
					}
					if (strComponentName.equals(strCurrentTC) && strCellValue.equals(strObject)) {
						if (dataRow.getCell(3) != null) {
							strObjPropertyValue = dataRow.getCell(3).toString();
							break;
						} else {
							break;
						}
					}
				} else {
					break;
				}
			}
			System.out.println(strCurrentTC + "----" + strObject + "--------" + strObjPropertyValue);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (strObjPropertyValue != null) {
			return strObjPropertyValue;
		} else {
			return "";
		}

	}

	public String getupdatedDataFile(String strDataFileName) {
		String strExcelDataFileName = "";
		try {
			String arrFile1[] = strDataFileName.split("#");
			List<String> columnDetailsValue = Arrays.asList(arrFile1);
			String testStr = columnDetailsValue.get(1);
			String arrFile[] = testStr.split("_");
			strExcelDataFileName = strDataPath + arrFile1[0] + "\\" + arrFile[0] + "\\" + arrFile[0] + "_Data.xls";
		} catch (Exception e) {

		}
		return strExcelDataFileName;
	}

}
