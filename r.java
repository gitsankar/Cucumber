package com.tes.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.tes.driver.ScriptExecutor;
import com.tes.executor.ExecutionRowNumber;
import com.thoughtworks.selenium.Selenium;

/**
 * Reporter --- Class for generation detail and high level reports
 * 
 * @author Prasad Joshi
 */

public class Reporter {

	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd-hh.mm.ss";
	Utilities utils = new Utilities(this);
	private String strAbsolutepath = new File("").getAbsolutePath();
	public static String strScreenshot;
	public static String strScreenshotFileName;
	static String strAbsolutePath = new File("").getAbsolutePath();
	static List<String> tempList_scenario_name1 = new ArrayList<String>(0);
	static List<String> tempList_teststep_description = new ArrayList<String>(0);
	static List<String> tempList_test_data = new ArrayList<String>(0);
	static List<String> tempList_snapshot = new ArrayList<String>(0);
	static List<String> tempList_status = new ArrayList<String>(0);
	static List<String> tempList_result_description = new ArrayList<String>(0);

	static List<String> listScenarioDetails = new ArrayList<String>(0);
	static List<String> listPassDetails = new ArrayList<String>(0);
	static List<String> listFailDetails = new ArrayList<String>(0);
	static List<String> listTotalStepsDetails = new ArrayList<String>(0);
	static List<String> listReportFileDetails = new ArrayList<String>(0);
	static List<String> listReportFileFullDetails = new ArrayList<String>(0);

	static String[] arrMethods = new String[100];

	static String strScenarioDetails = null;
	static String strPassDetails = null;
	static String strFailDetails = null;
	static String strTotalStepsDetails = null;
	static String strReportFileDetails = null;

	public static int month;
	public static int day;
	public static int year;

	public static String strFinalStartTime;
	public static String strFinalStopTime;

	public static String strStartTime;
	public static String strStopTime;

	public static float timeElapsed;
	public static long startTime;
	public static long stopTime;

	public Calendar calendar = new GregorianCalendar();

	private static int hour;
	private static int min;
	private static int sec;
	private static String am_pm;
	private static boolean running = false;

	private String strDataPath = strAbsolutepath + "\\data\\";
	private ExecutionRowNumber executionRowNumber = new ExecutionRowNumber();

	public void start(Calendar calander) {

		Long actualStartTime = System.currentTimeMillis();
		hour = calander.get(Calendar.HOUR);
		min = calander.get(Calendar.MINUTE);
		sec = calander.get(Calendar.SECOND);
		if (calander.get(Calendar.AM_PM) == 0)
			am_pm = "AM";
		else
			am_pm = "PM";

		running = true;
		startTime = actualStartTime;
		strStartTime = "" + hour + ":" + min + ":" + sec + " " + am_pm;
	}

	public String stop() {
		String strStoTime = null;
		Calendar stop = new GregorianCalendar();
		Long actualstopTime = System.currentTimeMillis();
		hour = stop.get(Calendar.HOUR);
		min = stop.get(Calendar.MINUTE);
		sec = stop.get(Calendar.SECOND);
		if (stop.get(Calendar.AM_PM) == 0)
			am_pm = "AM";
		else
			am_pm = "PM";
		// .currentTimeMillis();
		stopTime = actualstopTime;
		strStoTime = "" + hour + ":" + min + ":" + sec + " " + am_pm;

		running = false;
		return strStoTime;
	}

	// Total Execution time in milliseconds
	public float getElapsedTime() {
		float elapsedTime = 0;
		if (running) {
			elapsedTime = (System.currentTimeMillis() - startTime);
			// .currentTimeMillis() - startTime);
		} else {
			elapsedTime = (stopTime - startTime);
		}
		return elapsedTime;
	}

	public static String now() {
		Reporter reporter = new Reporter();
		Utilities utils = new Utilities(reporter);
		String strDetails = utils.getDataFileInfo();
		String[] arrDetails = strDetails.split("_");
		String strScreenshotPath = strAbsolutePath + "/results/"
				+ arrDetails[0] + "/screenshot/";
		Calendar cal = Calendar.getInstance();
		month = cal.get(Calendar.MONTH) + 1;
		day = cal.get(Calendar.DAY_OF_MONTH);
		year = cal.get(Calendar.YEAR);
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		Random rand = new Random();
		int num = rand.nextInt(1000);
		strScreenshotFileName = (String) (strScreenshotPath
				+ sdf.format(cal.getTime()) + num + ".png");
		strScreenshot = (String) ("screenshot/" + sdf.format(cal.getTime())
				+ num + ".png");
		return sdf.format(cal.getTime());
	}

	public void stepReportGenerator() {
		FileWriter aWriter = null;
		String strComponent = null;
		String strBrowser = utils.getConfigValues("Browser Type");
		String strDetails = utils.getDataFileInfo();
		String[] arrDetails = strDetails.split("_");
		String strOSName = System.getProperty("os.name");
		String strTestCaseId = null;

		try {
			strComponent = utils.getConfigValues("Application Name");
			String time = now();

			File resultFolder = new File(strAbsolutepath + "/results/"
					+ arrDetails[0]);
			if (!resultFolder.exists()) {
				resultFolder.mkdir();
			}
			File cssFile = new File(strAbsolutepath + "/results/"
					+ arrDetails[0] + "/pages");
			if (!cssFile.exists()) {
				FileUtils.copyDirectory(new File(strAbsolutepath
						+ "/results/pages"), new File(strAbsolutepath
						+ "/results/" + arrDetails[0] + "/pages"));
			}

			// String strReportFile = resultFolder + "/" + strDetails+
			// "_Report_" + time + ".html";
			String strReportFile = resultFolder + "\\" + strDetails
					+ "_Report_" + time + ".html";
			String strActualReportFile = strDetails + "_Report_" + time
					+ ".html";
			String strReportFileName = strDetails + "_Report_" + time + ".html";

			aWriter = new FileWriter(strReportFile, true);

			aWriter.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\"> ");
			aWriter.write("<html>");
			aWriter.write("<head>");

			aWriter.write("<link type=\"text/css\" href=\"./pages/css/themes/ui-lightness/jquery-ui-1.8.16.custom.css\" rel=\"Stylesheet\" />");
			aWriter.write("<link type=\"text/css\" href=\"./pages/css/myStyle.css\" rel=\"Stylesheet\" />");
			aWriter.write("<script type=\"text/javascript\" src=\"./pages/js/jquery-1.6.2.min.js\"></script>");
			aWriter.write("<script type=\"text/javascript\" src=\"./pages/js/jquery-ui-1.8.16.custom.min.js\"></script>");
			aWriter.write("<script type=\"text/javascript\" src=\"./pages/js/my.js\"></script>");
			aWriter.write("</head>");
			aWriter.write("<script type=\"text/javascript\">");

			aWriter.write("$(document).ready(function(){");
			aWriter.write("$(\"#tabs_environment,#tabs_plan,#tabs_set,#set_content_tabs\").tabs({");
			aWriter.write("selected: 0,");
			aWriter.write("deselectable: true");
			aWriter.write("});");
			aWriter.write("$(\"button\", \".btn\" ).button();");
			aWriter.write("$(\"button\", \".plan_step_list\" ).button();");
			aWriter.write("$(\"#tabs_plan\").hide();");
			aWriter.write("$(\"#tabs_set\").hide();");
			aWriter.write("$(\"#dialog\").dialog({");
			aWriter.write("autoOpen:false,");
			aWriter.write("modal:true,");
			aWriter.write("buttons:{");
			aWriter.write(" Store:function(){");
			aWriter.write("return;");
			aWriter.write("}");
			aWriter.write("},");
			aWriter.write("dialogClass: 'f2',");
			aWriter.write("resizable: true,");
			aWriter.write("show: 'slide',");
			aWriter.write("height:120");
			aWriter.write("});");

			aWriter.write("});");
			aWriter.write("</script>");
			aWriter.write("<body>");
			aWriter.write("<div class=\"page_container\">");
			aWriter.write("<div class=\"head\">");
			aWriter.write("<img alt=\"tes\" src=\"./pages/images/logo160.gif\">");
			aWriter.write("</div>");
			aWriter.write("<div class=\"content\">");
			aWriter.write("<table class=\"content_table\" cellpadding=\"0\" cellspacing=\"0\">");
			aWriter.write("<tr>");
			aWriter.write("<td valign=\"top\">");
			aWriter.write("<div class=\"right_content\">");
			aWriter.write("<div id=\"tabs_environment\">");
			aWriter.write("<ul>");
			// aWriter.write("<li><a href=\"#tabs-set-2\" class=\"f2\">" +
			// strDetails + " Report </a></li>");
			strTestCaseId = getTestcaseId(strDetails);
			if (strTestCaseId != null && !strTestCaseId.equalsIgnoreCase("")) {
				aWriter.write("<li><a href=\"#tabs-set-2\" class=\"f2\">Test Case - "
						+ strTestCaseId + " Report </a></li>");

			} else {
				aWriter.write("<li><a href=\"#tabs-set-2\" class=\"f2\">"
						+ strDetails + " Report </a></li>");
			}
			aWriter.write("</ul>");
			aWriter.write("<div id=\"tabs-set-1\"  class=\"f2\">");
			aWriter.write("<div style=\"margin-top: 10px;\">");
			aWriter.write("<table id=\"set_table\" width=\"100%\" class=\"f2\" cellpadding=\"\" cellspacing=\"10\" ><tr>");
			aWriter.write("<td><b>Execution Date</b></td>");
			aWriter.write("<td><b>Execution Start Time</b></td>");
			aWriter.write("<td><b>Execution End Time</b></td>");
			aWriter.write("<td><b>Total Execution Time</b></td>");
			aWriter.write("<td><b>Operating System</b></td>");
			aWriter.write("<td><b>Browser</b></td>");
			aWriter.write("</tr>");
			aWriter.write("<tr class=\"list_table_tr\">");
			aWriter.write("<td>" + day + "-" + month + "-" + year + "</td>");
			aWriter.write("<td>" + strStartTime + "</td>");
			aWriter.write("<td>" + strStopTime + "</td>");

			// Code added for time
			int seconds = Math.round((timeElapsed / (60000)) * 60);
			int hours = 0;
			int minutes = 0;
			int newSeconds = 0;
			int newSecond1 = 0;
			String strExecutiontime = "";
			if (seconds >= 3600) {
				hours = seconds / 3600;
				minutes = (seconds % 3600) / 60;
				newSeconds = (seconds % 3600) % 60;
				if (minutes == 0) {
					newSecond1 = (seconds % 3600) % 60;
					if (newSecond1 != 0) {
						strExecutiontime = hours + " Hour(s)" + newSecond1
								+ " Second(s)";
					} else {
						strExecutiontime = hours + " Hour(s)";
					}
				}

				if (minutes > 0 && minutes < 60) {
					if (newSeconds > 0 && minutes > 0) {
						strExecutiontime = hours + " Hour(s) " + minutes
								+ " Minute(s)" + newSeconds + " Second(s)";
					}
					if (newSeconds == 0 && minutes > 0) {
						strExecutiontime = hours + " Hour(s) " + minutes
								+ " Minute(s)";
					}
				}

				if (minutes > 60) {
					seconds = minutes % 60;
					minutes = minutes / 60;
					strExecutiontime = hours + " Hour(s) " + minutes
							+ " Minutes " + seconds + " Seconds";
				}

			} else {
				minutes = seconds / 60;
				seconds = seconds % 60;
				if (minutes > 0 && seconds == 0) {
					strExecutiontime = minutes + " Minute(s)";
				}
				if (minutes > 0 && seconds > 0) {
					strExecutiontime = minutes + " Minute(s) " + seconds
							+ " Second(s)";
				}
				if (minutes == 0) {
					strExecutiontime = seconds + " Second(s)";
				}
			}

			aWriter.write("<td>" + strExecutiontime + "</td>");
			// aWriter.write("<td>" + Math.round((timeElapsed / (60000)) * 60) +
			// " " + "seconds" + "</td>");
			aWriter.write("<td>" + strOSName + "</td>");
			aWriter.write("<td>" + strBrowser + "</td>");
			aWriter.write("</tr>");
			aWriter.write(" <tr class=\"list_table_tr\">");
			aWriter.write(" <td></td>");
			aWriter.write(" <td></td>");
			aWriter.write(" <td></td>");
			aWriter.write("<td></td>");
			aWriter.write(" <td></td>");
			aWriter.write("<td></td>");
			aWriter.write("</tr>");
			aWriter.write("<tr class=\"list_table_tr\">");
			aWriter.write("<td><b>Test Scenario Name</b></td>");
			aWriter.write("<td><b>Test Step Description</b></td>");
			aWriter.write("<td><b>Test Data</b></td>");
			aWriter.write("<td><b>Status</b></td>");
			aWriter.write("<td><b>Result Description</b></td>");
			aWriter.write("<td><b>ScreenShot</b></td>");
			aWriter.write("</tr>");
			aWriter.write("<tr class=\"list_table_tr\">");
			String strTempStatus = "Fail";
			for (int i = 0; i < tempList_scenario_name1.size(); i++) {
				if (i % 2 == 0) {
					aWriter.write("<tr class=\"list_table_tr\"><td >"
							+ tempList_scenario_name1.get(i) + "</td>");
					aWriter.write("<td >"
							+ tempList_teststep_description.get(i) + "</td>");
					aWriter.write("<td >" + tempList_test_data.get(i) + "</td>");
					strTempStatus = tempList_status.get(i);
					if (strTempStatus == null) {
						strTempStatus = "Fail";
					}
					if (strTempStatus.equalsIgnoreCase("Pass")) {
						aWriter.write("<td><font color=\"Green\">"
								+ tempList_status.get(i) + "</td>\n");
					} else {
						aWriter.write("<td><font color=\"Red\">"
								+ tempList_status.get(i) + "</td>\n");
					}
					aWriter.write("<td >" + tempList_result_description.get(i)
							+ "</td>");
					aWriter.write("<td >");
					try {
						if (tempList_snapshot.get(i).contentEquals(
								"No screenshot available")) {
							aWriter.write("No Screenshot available");

						} else {
							aWriter.write("<a href =\"");
							// aWriter.write("file:///" +
							// tempList_snapshot.get(i));
							aWriter.write("./" + tempList_snapshot.get(i));
							aWriter.write("\" target=\"_blank\">Screenshot</td>\n");

						}
					} catch (Exception e1) {
						aWriter.write("No Screenshot available");
					}
				} else {
					aWriter.write("<tr class=\"list_table_tr\"><td >"
							+ tempList_scenario_name1.get(i) + "</td>");
					aWriter.write("<td >"
							+ tempList_teststep_description.get(i) + "</td>");
					aWriter.write("<td >" + tempList_test_data.get(i) + "</td>");
					try {
						strTempStatus = tempList_status.get(i);
						if (strTempStatus == null) {
							strTempStatus = "Fail";
						}
						if (strTempStatus.equalsIgnoreCase("Pass")) {
							aWriter.write("<td><font color=\"Green\">"
									+ tempList_status.get(i) + "</td>\n");
						} else {
							aWriter.write("<td><font color=\"Red\">"
									+ tempList_status.get(i) + "</td>\n");
						}
					} catch (Exception e1) {
						System.out.println("Error in printing report");
					}
					aWriter.write("<td >" + tempList_result_description.get(i)
							+ "</td>");
					aWriter.write("<td >");

					if (tempList_snapshot.get(i).contentEquals(
							"No screenshot available")) {
						aWriter.write("No Screenshot available");

					} else {
						aWriter.write("<a href =\"");
						// aWriter.write("file:///" + tempList_snapshot.get(i));
						aWriter.write("./" + tempList_snapshot.get(i));
						aWriter.write("\" target=\"_blank\">Screenshot</td>\n");

					}
				}
			}

			aWriter.write("</table>");
			aWriter.write("</div>");
			aWriter.write("</div>");
			aWriter.write("</div>");
			aWriter.write("</div>");
			aWriter.write("</td>");
			aWriter.write("</tr>");
			aWriter.write("</table>");
			aWriter.write("</div>");
			aWriter.write("</div>");
			aWriter.write("</body>");
			aWriter.write("</html>");
			aWriter.flush();
			aWriter.close();

			listScenarioDetails.add(tempList_scenario_name1.get(0));
			int passCounter = 0;
			for (int x = 0; x < tempList_status.size(); x++) {
				if (tempList_status.get(x).equalsIgnoreCase("Pass")) {
					passCounter++;
				}
			}
			listPassDetails.add(Integer.toString(passCounter));
			listFailDetails.add(Integer.toString(tempList_status.size()
					- passCounter));
			listReportFileFullDetails.add(strReportFile);
			listReportFileDetails.add(strActualReportFile);
			listTotalStepsDetails.add(Integer.toString(tempList_status.size()));

			// New code added

			String strExcelDataFileName = strDataPath + arrDetails[0] + "\\"
					+ arrDetails[0] + "_Data.xls";
			POIFSFileSystem fs;
			String strCellValue = null;
			boolean isExpectedRow = false;
			try {
				fs = new POIFSFileSystem(new FileInputStream(
						strExcelDataFileName));
				HSSFWorkbook workbook = new HSSFWorkbook(fs);

				HSSFCellStyle hlink_style = workbook.createCellStyle();
				HSSFFont hlink_font = workbook.createFont();
				hlink_font
						.setUnderline(org.apache.poi.hssf.usermodel.HSSFFont.U_SINGLE);
				hlink_font
						.setColor(org.apache.poi.hssf.util.HSSFColor.BLUE.index);
				hlink_style.setFont(hlink_font);

				HSSFSheet dataSheet = null;
				dataSheet = workbook.getSheet("Scenario");
				HSSFRow dataRow = null;
				String strExecuteData = "No";
				HSSFCell executeCell = null;
				HSSFCell cellReportLink = null;
				Iterator rows = dataSheet.rowIterator();
				int noOfRows = 0;
				while (rows.hasNext()) {
					HSSFRow row = (HSSFRow) rows.next();
					noOfRows++;
				}

				// New code for same methods result
				// List<String> listAllMethods = new ArrayList<String>(0);

				for (int x1 = 0; x1 < noOfRows; x1++) {
					dataRow = dataSheet.getRow(x1);
					try {
						strCellValue = dataRow.getCell(0).toString();
					} catch (Exception e1) {
						strCellValue = null;
					}
					if (strCellValue != null) {
						// listAllMethods.add(strCellValue);
						arrMethods[x1] = strCellValue;
					}
				}

				String StrStatus = null;
				int i = 0;
				if (listFailDetails.get(i) != null) {

					// if(Integer.parseInt(listFailDetails.get(i))>0){
					if (tempList_status.contains("Fail")) {
						StrStatus = "Fail";
						// for(int xx = 1; xx < arrMethods.length; xx++){
						for (int xx = 0; xx < ScriptExecutor
								.getExecutionMethodsListSize(); xx++) {
							if (ScriptExecutor.getExecutionMethodsList(xx)
									.equalsIgnoreCase(arrDetails[1])) {
								dataRow = dataSheet.getRow(xx + 1);
								executeCell = dataRow.getCell(2);

								if (executeCell == null) {
									strExecuteData = "No";
								} else {
									strExecuteData = executeCell.toString();
								}
								if (!strExecuteData.equalsIgnoreCase("No")) {
									dataRow.createCell(3).setCellValue(
											StrStatus);
									cellReportLink = dataRow.createCell(4);
									cellReportLink.setCellStyle(hlink_style);
									cellReportLink.setCellValue(strReportFile);
									HSSFHyperlink link = new HSSFHyperlink(
											org.apache.poi.hssf.usermodel.HSSFHyperlink.LINK_FILE);
									link.setAddress(strReportFile);
									cellReportLink.setHyperlink(link);

									// dataRow.createCell(y+4).setCellValue(listReportFileFullDetails.get(i));
									dataRow.createCell(5).setCellValue(
											arrDetails[1] + " Report");
									isExpectedRow = true;
									// arrMethods[xx] = arrMethods[xx] + "Done";
									ScriptExecutor.setExecutionMethodsList(xx,
											"Done");
									break;
								}
							}
							if (isExpectedRow) {
								break;
							}
						}
						FileOutputStream fileOut = new FileOutputStream(
								strExcelDataFileName);
						workbook.write(fileOut);
						fileOut.close();
					} else {
						StrStatus = "Pass";
						// for(int xx = 1; xx < arrMethods.length; xx++){
						for (int xx = 0; xx < ScriptExecutor
								.getExecutionMethodsListSize(); xx++) {
							// if(listAllMethods.get(xx).equalsIgnoreCase(arrDetails[1])){
							if (ScriptExecutor.getExecutionMethodsList(xx)
									.equalsIgnoreCase(arrDetails[1])) {
								dataRow = dataSheet.getRow(xx + 1);
								executeCell = dataRow.getCell(2);

								if (executeCell == null) {
									strExecuteData = "No";
								} else {
									strExecuteData = executeCell.toString();
								}
								if (!strExecuteData.equalsIgnoreCase("No")) {
									dataRow.createCell(3).setCellValue(
											StrStatus);
									cellReportLink = dataRow.createCell(4);
									cellReportLink.setCellStyle(hlink_style);
									cellReportLink.setCellValue(strReportFile);
									HSSFHyperlink link = new HSSFHyperlink(
											org.apache.poi.hssf.usermodel.HSSFHyperlink.LINK_FILE);
									link.setAddress(strReportFile);
									cellReportLink.setHyperlink(link);
									System.out.println("");
									// dataRow.createCell(y+4).setCellValue(listReportFileFullDetails.get(i));
									dataRow.createCell(5).setCellValue(
											arrDetails[1] + " Report");
									isExpectedRow = true;
									ScriptExecutor.setExecutionMethodsList(xx,
											"Done");
									// arrMethods[xx] = arrMethods[xx] + "Done";
									break;
								}
							}
							if (isExpectedRow) {
								break;
							}
						}
						FileOutputStream fileOut = new FileOutputStream(
								strExcelDataFileName);
						workbook.write(fileOut);
						fileOut.close();
					}
				}
			} catch (Exception e1) {
				System.out
						.println("Exception occurred while writing result in excel file");
				e1.printStackTrace();
			}

			// New block ends*/

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			tempList_scenario_name1.clear();
			tempList_teststep_description.clear();
			tempList_test_data.clear();
			tempList_snapshot.clear();
			tempList_status.clear();
			tempList_result_description.clear();
		}
	}

	public void moduleReportGenerator() {
		FileWriter aWriter = null;
		String strComponent = null;
		String strBrowser = utils.getConfigValues("Browser Type");
		String strDetails = utils.getDataFileInfo();
		String[] arrDetails = strDetails.split("_");
		String strOSName = System.getProperty("os.name");

		try {
			strComponent = utils.getConfigValues("Application Name");
			String time = now();
			timeElapsed = stopTime - startTime;
			File resultFolder = new File(strAbsolutepath + "/results/"
					+ arrDetails[0]);

			if (!resultFolder.exists()) {
				resultFolder.mkdir();
			}
			File cssFile = new File(strAbsolutepath + "/results/"
					+ arrDetails[0] + "/pages");
			if (!cssFile.exists()) {
				FileUtils.copyDirectory(new File(strAbsolutepath
						+ "/results/pages"), new File(strAbsolutepath
						+ "/results/" + arrDetails[0] + "/pages"));
			}

			String strReportFile = resultFolder + "/" + arrDetails[0]
					+ "_Report_" + time + ".html";

			aWriter = new FileWriter(strReportFile, true);

			aWriter.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\"> ");
			aWriter.write("<html>");
			aWriter.write("<head>");

			aWriter.write("<link type=\"text/css\" href=\"./pages/css/themes/ui-lightness/jquery-ui-1.8.16.custom.css\" rel=\"Stylesheet\" />");
			aWriter.write("<link type=\"text/css\" href=\"./pages/css/myStyle.css\" rel=\"Stylesheet\" />");
			aWriter.write("<script type=\"text/javascript\" src=\"./pages/js/jquery-1.6.2.min.js\"></script>");
			aWriter.write("<script type=\"text/javascript\" src=\"./pages/js/jquery-ui-1.8.16.custom.min.js\"></script>");
			aWriter.write("<script type=\"text/javascript\" src=\"./pages/js/my.js\"></script>");
			aWriter.write("</head>");
			aWriter.write("<script type=\"text/javascript\">");

			aWriter.write("$(document).ready(function(){");
			aWriter.write("$(\"#tabs_environment,#tabs_plan,#tabs_set,#set_content_tabs\").tabs({");
			aWriter.write("selected: 0,");
			aWriter.write("deselectable: true");
			aWriter.write("});");
			aWriter.write("$(\"button\", \".btn\" ).button();");
			aWriter.write("$(\"button\", \".plan_step_list\" ).button();");
			aWriter.write("$(\"#tabs_plan\").hide();");
			aWriter.write("$(\"#tabs_set\").hide();");
			aWriter.write("$(\"#dialog\").dialog({");
			aWriter.write("autoOpen:false,");
			aWriter.write("modal:true,");
			aWriter.write("buttons:{");
			aWriter.write(" Store:function(){");
			aWriter.write("return;");
			aWriter.write("}");
			aWriter.write("},");
			aWriter.write("dialogClass: 'f2',");
			aWriter.write("resizable: true,");
			aWriter.write("show: 'slide',");
			aWriter.write("height:120");
			aWriter.write("});");

			aWriter.write("});");
			aWriter.write("</script>");
			aWriter.write("<body>");
			aWriter.write("<div class=\"page_container\">");
			aWriter.write("<div class=\"head\">");
			aWriter.write("<img alt=\"tes\" src=\"./pages/images/logo160.gif\">");
			aWriter.write("</div>");
			aWriter.write("<div class=\"content\">");
			aWriter.write("<table class=\"content_table\" cellpadding=\"0\" cellspacing=\"0\">");
			aWriter.write("<tr>");
			aWriter.write("<td valign=\"top\">");
			aWriter.write("<div class=\"right_content\">");
			aWriter.write("<div id=\"tabs_environment\">");
			aWriter.write("<ul>");
			aWriter.write("<li><a href=\"#tabs-set-2\" class=\"f2\">"
					+ arrDetails[0] + " Report </a></li>");
			aWriter.write("</ul>");
			aWriter.write("<div id=\"tabs-set-1\"  class=\"f2\">");
			aWriter.write("<div style=\"margin-top: 10px;\">");
			aWriter.write("<table id=\"set_table\" width=\"100%\" class=\"f2\" cellpadding=\"\" cellspacing=\"10\" ><tr>");
			aWriter.write("<td><b>Execution Date</b></td>");
			aWriter.write("<td><b>Execution Start Time</b></td>");
			aWriter.write("<td><b>Execution End Time</b></td>");
			aWriter.write("<td><b>Total Execution Time</b></td>");
			aWriter.write("<td><b>Operating System</b></td>");
			aWriter.write("<td><b>Browser</b></td>");
			aWriter.write("</tr>");
			aWriter.write("<tr class=\"list_table_tr\">");
			aWriter.write("<td>" + day + "-" + month + "-" + year + "</td>");
			aWriter.write("<td>" + strStartTime + "</td>");
			aWriter.write("<td>" + strStopTime + "</td>");

			// Code added for time
			int seconds = Math.round((timeElapsed / (60000)) * 60);
			int hours = 0;
			int minutes = 0;
			int newSeconds = 0;
			int newSecond1 = 0;
			String strExecutiontime = "";
			if (seconds >= 3600) {
				hours = seconds / 3600;
				minutes = (seconds % 3600) / 60;
				newSeconds = (seconds % 3600) % 60;
				if (minutes == 0) {
					newSecond1 = (seconds % 3600) % 60;
					if (newSecond1 != 0) {
						strExecutiontime = hours + " Hour(s)" + newSecond1
								+ " Second(s)";
					} else {
						strExecutiontime = hours + " Hour(s)";
					}
				}
				if (minutes > 0 && minutes < 60) {
					if (newSeconds > 0 && minutes > 0) {
						strExecutiontime = hours + " Hour(s) " + minutes
								+ " Minute(s)" + newSeconds + " Second(s)";
					}
					if (newSeconds == 0 && minutes > 0) {
						strExecutiontime = hours + " Hour(s) " + minutes
								+ " Minute(s)";
					}
				}

				if (minutes > 60) {
					seconds = minutes % 60;
					minutes = minutes / 60;
					strExecutiontime = hours + " Hour(s) " + minutes
							+ " Minutes " + seconds + " Seconds";
				}

			} else {
				minutes = seconds / 60;
				seconds = seconds % 60;
				if (minutes > 0 && seconds == 0) {
					strExecutiontime = minutes + " Minute(s)";
				}
				if (minutes > 0 && seconds > 0) {
					strExecutiontime = minutes + " Minute(s) " + seconds
							+ " Second(s)";
				}
				if (minutes == 0) {
					strExecutiontime = seconds + " Second(s)";
				}
			}

			aWriter.write("<td>" + strExecutiontime + "</td>");

			// aWriter.write("<td>" + Math.round((timeElapsed / (60000)) * 60)+
			// " " + "seconds" + "</td>");
			aWriter.write("<td>" + strOSName + "</td>");
			aWriter.write("<td>" + strBrowser + "</td>");
			aWriter.write("</tr>");
			aWriter.write(" <tr class=\"list_table_tr\">");
			aWriter.write(" <td></td>");
			aWriter.write(" <td></td>");
			aWriter.write(" <td></td>");
			aWriter.write("<td></td>");
			aWriter.write(" <td></td>");
			aWriter.write("<td></td>");
			aWriter.write("</tr>");
			aWriter.write("<tr class=\"list_table_tr\">");
			aWriter.write("<td><b>Scenario Name</b></td>");
			aWriter.write("<td><b>Total Steps</b></td>");
			aWriter.write("<td><b>Pass Steps</b></td>");
			aWriter.write("<td><b>Fail Steps</b></td>");
			aWriter.write("<td><b>Link to detail result</b></td>");
			aWriter.write("</tr>");

			for (int i = 0; i < listScenarioDetails.size(); i++) {
				if (i % 2 == 0) {
					aWriter.write("<tr class=\"list_table_tr\"><td >"
							+ listScenarioDetails.get(i) + "</td>");
					aWriter.write("<td >" + listTotalStepsDetails.get(i)
							+ "</td>");
					aWriter.write("<td ><font color=\"Green\">"
							+ listPassDetails.get(i) + "</td>");
					if (Integer.parseInt(listFailDetails.get(i)) == 0) {
						aWriter.write("<td >" + listFailDetails.get(i)
								+ "</td>");
					} else {
						aWriter.write("<td ><font color=\"Red\">"
								+ listFailDetails.get(i) + "</td>");
					}
					aWriter.write("<td >");

					aWriter.write("<a href =\"");
					// aWriter.write("file:///" + listReportFileDetails.get(i));
					aWriter.write("./" + listReportFileDetails.get(i));
					aWriter.write("\" target=\"_blank\">Detail Result</td>\n");
				} else {
					aWriter.write("<tr class=\"list_table_tr\"><td >"
							+ listScenarioDetails.get(i) + "</td>");
					aWriter.write("<td >" + listTotalStepsDetails.get(i)
							+ "</td>");
					aWriter.write("<td ><font color=\"Green\">"
							+ listPassDetails.get(i) + "</td>");
					if (Integer.parseInt(listFailDetails.get(i)) == 0) {
						aWriter.write("<td >" + listFailDetails.get(i)
								+ "</td>");
					} else {
						aWriter.write("<td ><font color=\"Red\">"
								+ listFailDetails.get(i) + "</td>");
					}
					aWriter.write("<td >");
					aWriter.write("<a href =\"");
					// aWriter.write("file:///" + listReportFileDetails.get(i));
					aWriter.write("./" + listReportFileDetails.get(i));
					aWriter.write("\" target=\"_blank\">Detail Result</td>\n");
				}

				/*
				 * // Added by Prasad String StrStatus = null;
				 * if(listFailDetails.get(i) != null){
				 * if(Integer.parseInt(listFailDetails.get(i))>0){ StrStatus =
				 * "Fail"; String strExcelDataFileName = strDataPath +
				 * arrDetails[0] + "\\" + arrDetails[0] + "_Data.xls";
				 * POIFSFileSystem fs; String strCellValue = null; //
				 * callVBScript try { fs = new POIFSFileSystem(new
				 * FileInputStream(strExcelDataFileName)); HSSFWorkbook workbook
				 * = new HSSFWorkbook(fs);
				 * 
				 * HSSFCellStyle hlink_style = workbook.createCellStyle();
				 * HSSFFont hlink_font = workbook.createFont();
				 * hlink_font.setUnderline
				 * (org.apache.poi.hssf.usermodel.HSSFFont.U_SINGLE);
				 * hlink_font.
				 * setColor(org.apache.poi.hssf.util.HSSFColor.BLUE.index);
				 * hlink_style.setFont(hlink_font);
				 * 
				 * HSSFSheet dataSheet = null; dataSheet =
				 * workbook.getSheet("Scenario"); HSSFRow dataRow = null;
				 * HSSFCell cellReportLink = null; Iterator rows =
				 * dataSheet.rowIterator(); int noOfRows = 0;
				 * 
				 * while(rows.hasNext()) { HSSFRow row = (HSSFRow) rows.next();
				 * noOfRows++; } for(int l = 0; l < noOfRows; l++){ dataRow =
				 * dataSheet.getRow(l); int totalCells =
				 * dataRow.getLastCellNum(); for(int m = 0; m < totalCells;
				 * m++){ try{ strCellValue = dataRow.getCell(m).toString();
				 * }catch(Exception e1){ strCellValue = null; }
				 * if(strCellValue!=null){
				 * if(strCellValue.toUpperCase().equals(listScenarioDetails
				 * .get(i))){ dataRow.createCell(m+3).setCellValue(StrStatus);
				 * cellReportLink = dataRow.createCell(m+4);
				 * cellReportLink.setCellStyle(hlink_style);
				 * cellReportLink.setCellValue
				 * (listReportFileFullDetails.get(i)); HSSFHyperlink link = new
				 * HSSFHyperlink
				 * (org.apache.poi.hssf.usermodel.HSSFHyperlink.LINK_FILE);
				 * link.setAddress(listReportFileFullDetails.get(i));
				 * cellReportLink.setHyperlink(link);
				 * 
				 * //dataRow.createCell(m+4).setCellValue(listReportFileFullDetails
				 * .get(i));
				 * dataRow.createCell(m+5).setCellValue(listScenarioDetails
				 * .get(i) + " Report"); break; } } } } FileOutputStream fileOut
				 * = new FileOutputStream(strExcelDataFileName);
				 * workbook.write(fileOut); fileOut.close(); }catch(Exception
				 * e1){ System.out.println(
				 * "Exception occurred while writing result in excel file");
				 * e1.printStackTrace(); } }else{ StrStatus = "Pass"; //String
				 * strExcelDataFileName = strDataPath + arrDetails[0] + "/" +
				 * arrDetails[0] + "_Data.xls"; String strExcelDataFileName =
				 * strDataPath + arrDetails[0] + "\\" + arrDetails[0] +
				 * "_Data.xls"; POIFSFileSystem fs; String strCellValue = null;
				 * try { fs = new POIFSFileSystem(new
				 * FileInputStream(strExcelDataFileName)); HSSFWorkbook workbook
				 * = new HSSFWorkbook(fs);
				 * 
				 * HSSFCellStyle hlink_style = workbook.createCellStyle();
				 * HSSFFont hlink_font = workbook.createFont();
				 * hlink_font.setUnderline
				 * (org.apache.poi.hssf.usermodel.HSSFFont.U_SINGLE);
				 * hlink_font.
				 * setColor(org.apache.poi.hssf.util.HSSFColor.BLUE.index);
				 * hlink_style.setFont(hlink_font);
				 * 
				 * HSSFSheet dataSheet = null; dataSheet =
				 * workbook.getSheet("Scenario"); HSSFRow dataRow = null;
				 * HSSFCell cellReportLink = null; Iterator rows =
				 * dataSheet.rowIterator(); int noOfRows = 0;
				 * while(rows.hasNext()) { HSSFRow row = (HSSFRow) rows.next();
				 * noOfRows++; } System.out.println(""); for(int x = 0; x <
				 * noOfRows; x++){ dataRow = dataSheet.getRow(x); int totalCells
				 * = dataRow.getLastCellNum(); for(int y = 0; y < totalCells;
				 * y++){ try{ strCellValue = dataRow.getCell(y).toString();
				 * }catch(Exception e1){ strCellValue = null; }
				 * if(strCellValue!=null){
				 * if(strCellValue.toUpperCase().equals(listScenarioDetails
				 * .get(i))){ dataRow.createCell(y+3).setCellValue(StrStatus);
				 * cellReportLink = dataRow.createCell(y+4);
				 * cellReportLink.setCellStyle(hlink_style);
				 * cellReportLink.setCellValue
				 * (listReportFileFullDetails.get(i)); HSSFHyperlink link = new
				 * HSSFHyperlink
				 * (org.apache.poi.hssf.usermodel.HSSFHyperlink.LINK_FILE);
				 * link.setAddress(listReportFileFullDetails.get(i));
				 * cellReportLink.setHyperlink(link);
				 * 
				 * //dataRow.createCell(y+4).setCellValue(listReportFileFullDetails
				 * .get(i));
				 * dataRow.createCell(y+5).setCellValue(listScenarioDetails
				 * .get(i) + " Report");
				 * 
				 * break; } } } } FileOutputStream fileOut = new
				 * FileOutputStream(strExcelDataFileName);
				 * workbook.write(fileOut); fileOut.close(); }catch(Exception
				 * e1){ System.out.println(
				 * "Exception occurred while writing result in excel file");
				 * e1.printStackTrace(); } } } // New block ends
				 */}

			aWriter.write("</table>");
			aWriter.write("</div>");
			aWriter.write("</div>");
			aWriter.write("</div>");
			aWriter.write("</div>");
			aWriter.write("</td>");
			aWriter.write("</tr>");
			aWriter.write("</table>");
			aWriter.write("</div>");
			aWriter.write("</div>");
			aWriter.write("</body>");
			aWriter.write("</html>");
			aWriter.flush();
			aWriter.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			listScenarioDetails.clear();
			listPassDetails.clear();
			listFailDetails.clear();
			listTotalStepsDetails.clear();
			listReportFileDetails.clear();
			listReportFileFullDetails.clear();
		}
	}

	public void writeStepResult(String strScenarioName,
			String strStepDescription, String strTestData, String strStatus,
			String strRessultDescription, boolean isScreenshot,
			RemoteWebDriver webDriver) {
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

			tempList_scenario_name1.add(strScenarioName);
			tempList_teststep_description.add(strStepDescription);
			tempList_test_data.add(strTestData);
			tempList_result_description.add(strRessultDescription);

			if (isScreenshot) {
				now();
				Random rand = new Random();
				int num = rand.nextInt(1000);

				try {
					File srcFile = ((TakesScreenshot) webDriver)
							.getScreenshotAs(OutputType.FILE);

					FileUtils
							.copyFile(srcFile, new File(strScreenshotFileName));

					tempList_snapshot.add(strScreenshot);

				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {
				tempList_snapshot.add("No screenshot available");
			}
			tempList_status.add(strStatus);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void writeStepResult(String strScenarioName,
			String strStepDescription, String strTestData, String strStatus,
			String strRessultDescription, boolean isScreenshot,
			Selenium selenium) {
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

			tempList_scenario_name1.add(strScenarioName);
			tempList_teststep_description.add(strStepDescription);
			tempList_test_data.add(strTestData);
			tempList_result_description.add(strRessultDescription);

			if (isScreenshot) {
				now();
				Random rand = new Random();
				int num = rand.nextInt(1000);

				try {
					selenium.captureScreenshot(strScreenshotFileName);
					// selenium.captureScreenshot(strScreenshot);
					tempList_snapshot.add(strScreenshot);
					// InputStream is = new FileInputStream(new
					// File(strScreenshot));
					// String temp =
					// Hex.encodeHexString(IOUtils.toByteArray(is));
					// tempList_snapshot.add(temp);
					// System.out.println(temp);

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				tempList_snapshot.add("No screenshot available");
			}
			tempList_status.add(strStatus);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void writeStepResult(String strScenarioName,
			String strStepDescription, String strTestData, String strStatus,
			String strRessultDescription) {
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

			tempList_scenario_name1.add(strScenarioName);
			tempList_teststep_description.add(strStepDescription);
			tempList_test_data.add(strTestData);
			tempList_result_description.add(strRessultDescription);
			tempList_status.add(strStatus);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public String getTestcaseId(String strDetails) {

		String strDataSource = utils.getConfigValues("Data Source");
		String strCellValue = null;
		String strTCIdValue = null;
		String[] arrDetails = strDetails.split("_");
		if (strDataSource.equalsIgnoreCase("Excel")
				|| strDataSource.equalsIgnoreCase("xls")) {
			String strExcelDataFileName = strDataPath + arrDetails[0] + "/"
					+ arrDetails[0] + "_Data.xls";
			POIFSFileSystem fs;

			try {
				fs = new POIFSFileSystem(new FileInputStream(
						strExcelDataFileName));
				HSSFWorkbook workbook = new HSSFWorkbook(fs);

				HSSFSheet dataSheet = workbook.getSheet("Scenario");
				int totalRows = dataSheet.getLastRowNum();
				String strRowData = null;
				HSSFRow row = null;
				// HSSFCell cell = null;
				int totalCells = 0;
				for (int i = 1; i <= totalRows; i++) {
					row = dataSheet.getRow(i);
					try {
						strCellValue = row.getCell(0).toString();
						if (strCellValue.equalsIgnoreCase(arrDetails[1])) {
							strTCIdValue = row.getCell(6).toString();
							break;
						}
					} catch (Exception e1) {
						break;
					}
				}
				if (strTCIdValue == null) {
					strTCIdValue = "";
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			String strComponent = null;
			String strExecutionConfigFileName = null;
			String strDelimiter = "##";

			try {
				// strExecutionConfigFileName =
				// utils.getConfigValues("Execution Configuration File");
				strExecutionConfigFileName = strDataPath + arrDetails[0] + "/"
						+ arrDetails[0] + "_Execution.config";
				BufferedReader br = new BufferedReader(new FileReader(
						strExecutionConfigFileName));
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
							// lstExecutionComponents.add(strComponent);
							strComponent = st.nextToken();
							// lstRowNumber.add(strComponent);
							strComponent = st.nextToken();
							// lstExecutionFlag.add(strComponent);
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
		}
		return strTCIdValue;
	}

}
