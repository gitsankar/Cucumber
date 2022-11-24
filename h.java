package com.tes.utilities;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.awt.AWTException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.nio.file.*;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

//import com.tes.utilities.Common;

public class HTML
{
			private static WebDriver driver;
	        public static String g_FileName=""; //'Report Log File Name.
	        public static String g_sFileName = "";
	        public static String g_iCapture_Count = "";// 'Number of Image capture
	        public static int g_iImage_Capture;//=""; //'Flag for Image Capture in Result File
	        public static int g_iPass_Count = 0; //'Pass Count
	        public static int g_iFail_Count;//=0; //'Fail Count
	        public static Date g_tStart_Time; //'Start Time
	        public static Date g_tEnd_Time; //'End Time
	        public static String g_sScreenName = ""; //'Screen shot name
	        public static int g_Total_TC;//=0;
	        public static int g_Total_Pass;//=0;
	        public static int g_Total_Fail;//=0;
	        public static int g_Flag;//=0;
	        public static int g_Flag1;//=0;
	        public static Date g_tSummaryStart_Time;// 'Start Time
	        public static Date g_tSummaryEnd_Time; //'End Time
	        public static Date g_tSummaryTCStart_Time; //'Start time for each test case in Summary Report
	        public static Date g_tSummaryTCEnd_Time; //'Start time for each test case in Summary Report
	        public static int g_SummaryTotal_TC;//=0;
	        public static int g_SummaryTotal_Pass;//=0;
	        public static int g_SummaryTotal_Fail;//=0;
	        public static int g_SummaryFlag = 0;
	        public static String g_sSummaryFileName = "";
	        public static String g_ScriptName = "";
	        public static String g_sSection = "";
	        public static int hour;
	        public static int minute;
	        public static int second;
	        
	        
	        public static File file ;
			public static FileInputStream fileInput;
			//public static Properties properties;
			//public static Date d = new Date();
        	
			public static Properties properties = new Properties();
		
////////////////////////////Summary Initialization Start//////////////////////////////////////////////////////////			
	        public static void fnSummaryInitialization(String strSummaryReportName) throws IOException
	        {
	        		//	File directory = new File (".");
	        		//	String sConfigfilespath = directory.getCanonicalPath()+"\\Config";
//	        	String sConfigfilespath = "C:\\Automation QA\\Workspace\\TestFramework\\Config";
	        	String sConfigfilespath = "Config";
				//System.out.println(sConfigfilespath);
				 file = new File (sConfigfilespath + "\\Sys.properties");
			    			
	        			
	        			//Common c = new Common();
	        			//driver= c.dr();
	        			//file = new File("D:\\FrameWorks\\SeleniumJava\\WorkSpace\\GuidewirePC\\ConfigFiles\\Sys.properties");
			        	//fileInput = new FileInputStream(file);
						//properties = new Properties();
			        	//properties.load(fileInput);
						//fileInput.close();
						
	        	FileInputStream fis = new FileInputStream( file);
    			properties.load(fis);
    			
//						FileOutputStream fos = new FileOutputStream( "C:\\Automation QA\\Workspace\\TestFramework\\Config\\Sys.properties");
						FileOutputStream fos = new FileOutputStream( "Config\\Sys.properties");

	        			properties.setProperty("TempResultPath",properties.getProperty("ResultsFolderPath"));
	        			properties.setProperty("TempResults",properties.getProperty("TempResultPath"));
	                    properties.setProperty("SummaryFileName","");
	                    properties.setProperty("SummaryFolderName",properties.getProperty("ResultsFolderPath"));
				        
	                    properties.store(fos, null);
				        fos.close();
	        
				        
				        
	                    File objDir=new File(properties.getProperty("SummaryFolderName"));
				        if(!objDir.exists())
	                    {
				        	objDir.mkdir();
	                    }
	                 
				        properties.setProperty("SummaryFolderName1",properties.getProperty("ResultsFolderPath")+"\\" + strSummaryReportName);
				        File objDir1=new File(properties.getProperty("TempResults"));
				        if(!objDir1.exists())
	                    {
				        	objDir1.mkdir();
	                    }
				        
				        fnSummaryOpenHTMLFile(strSummaryReportName);// 'logo, heading
				        fnSummaryInsertSection(); //'TestCaseID,Scenario Name and Result
				        fis.close();	        
	        }

	        @SuppressWarnings("deprecation")
			public static void fnSummaryOpenHTMLFile(String sSection) throws IOException	
	        {
			        g_iPass_Count=0;
			        g_iFail_Count=0;
			        g_sFileName = "sScriptName";
			        //g_iImage_Capture = 1

			        g_SummaryTotal_TC = 0;
			        g_SummaryTotal_Pass = 0;
			        g_SummaryTotal_Fail = 0;
			        g_SummaryFlag = 0;
			        g_ScriptName="sScriptName";
			        hour=0;
			        minute=0;
			        second=0;
			        
//			    	String sConfigfilespath = "C:\\Automation QA\\Workspace\\TestFramework\\Config";
			    	String sConfigfilespath = "Config";
					//System.out.println(sConfigfilespath);
					 file = new File (sConfigfilespath + "\\Sys.properties");
				
			        /*FileInputStream fis = new FileInputStream( file);
	    			properties.load(fis);
	    			fis.close();*/
//							FileOutputStream fos = new FileOutputStream( "C:\\Automation QA\\Workspace\\TestFramework\\Config\\Sys.properties");
							FileOutputStream fos = new FileOutputStream( "Config\\Sys.properties");

			        Date d = new Date();
	                String gsTempFile = properties.getProperty("SummaryFileName");
	              //  String ModuleName= properties.getProperty("ModuleName");
			        if((gsTempFile == null) || (gsTempFile == ""))
	                {
				                gsTempFile = properties.getProperty("SummaryFolderName1") +d.getYear() + d.getMonth() + d.getDay() + "_" + d.getHours() + d.getMinutes() + d.getSeconds()+ ".htm";	
				                properties.setProperty("SummaryFileName",gsTempFile);
			        }
			        properties.store(fos, null);
			        fos.close();
			   	// 	Path objPath = Paths.get(gsTempFile);
				//	String ResSummary = properties.getProperty("ResultsHeader");
			   	 	FileWriter objFile=new FileWriter(gsTempFile,true);
					objFile.write("<HTML><BODY><TABLE BORDER=0 CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
					objFile.write("<TR COLS=2><TD BGCOLOR=WHITE WIDTH=6%><IMG SRC='https://az846835.vo.msecnd.net/company/logos/GlobalPayments.png'></TD><TD WIDTH=94% BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=NAVY SIZE=3><B>&nbsp;" +properties.getProperty("ResultsHeader")+ "<BR/><FONT FACE=VERDANA COLOR=SILVER SIZE=2>&nbsp;Date: " + d +"</BR>&nbsp;</B></FONT></TD><TD BGCOLOR=WHITE WIDTH=6%><IMG SRC='https://www.tes.com/sites/all/themes/tes/logo.png'></TD></TR></TABLE>");
					objFile.write("<TABLE BORDER=0 BGCOLOR=BLACK CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
					objFile.write("<TR><TD BGCOLOR=#66699 WIDTH=15%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Title : " + sSection +"</B></FONT></TD></TR>");
					objFile.write("</TABLE></BODY></HTML>");
					objFile.close();
				
					g_tSummaryStart_Time = d;
					g_tSummaryTCStart_Time = d;
					g_sSection=sSection;
//					fis.close();	 
	        }
	        
	        @SuppressWarnings("deprecation")
			public static void fnSummaryInsertSection() throws IOException
	        {
	        		Date d = new Date();
	                String gsTempFile = properties.getProperty("SummaryFileName");
			        if(gsTempFile =="")
	                {
				                gsTempFile = properties.getProperty("SummaryFolderName") +d.getYear() + d.getMonth() + d.getDay() + "_" + d.getHours() + d.getMinutes() + d.getSeconds()+ ".htm";	
			        }
			      //  Path objPath=Paths.get(gsTempFile);			        
					FileWriter objFile=new FileWriter(gsTempFile,true);
					objFile.write("<HTML><BODY><TABLE BORDER=1 CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
					objFile.write("<TR COLS=6><TD BGCOLOR=#FFCC99 WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Module Name</B></FONT></TD><TD BGCOLOR=#FFCC99 WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Feature Name</B></FONT></TD><TD BGCOLOR=#FFCC99 WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Scenario Name</B></FONT></TD><TD BGCOLOR=#FFCC99 WIDTH=30%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Test Case Name</B></FONT></TD><TD BGCOLOR=#FFCC99 SIZE=2 WIDTH=15%><B>Execution Time</B></FONT></TD><TD BGCOLOR=#FFCC99 SIZE=2 WIDTH=10%><B>Result</B></FONT></TD></TR>");
					objFile.close();						
	        }
////////////////////////////Summary Initialization End/////////////////////////////////////////////////////////////		
			
	        private static String getComputerName()
			{
			    Map<String, String> env = System.getenv();
			    if (env.containsKey("COMPUTERNAME"))
			        return env.get("COMPUTERNAME");
			    else if (env.containsKey("HOSTNAME"))
			        return env.get("HOSTNAME");
			    else
			        return "Unknown Computer";
			}
			
	        
////////////////////////////Test Case Initialization Start/////////////////////////////////////////////////////////			
	        public static void fnInitilization(String strExecutionStartTime,String className, String methodName) throws IOException, AWTException
	        {
	       // 	File directory = new File (".");
        		//	String sConfigfilespath = directory.getCanonicalPath()+"\\Config";
//        			String sConfigfilespath = "C:\\Automation QA\\Workspace\\TestFramework\\Config";
        			String sConfigfilespath = "Config";
        			System.out.println(sConfigfilespath);
        			File file = new File (sConfigfilespath + "\\Sys.properties");
        			FileInputStream fis = new FileInputStream( file);
        			properties.load(fis);
        			fis.close();
	        	
        			
        			FileOutputStream fos = new FileOutputStream( file);
        	
					//properties.setProperty("TempResultPath",properties.getProperty("ResultsFolderPath") + "\\temp");        
					//properties.setProperty("TempResults",properties.getProperty("TempResultPath"));
					properties.setProperty("FileName","");
					properties.setProperty("FolderName",properties.getProperty("ResultsFolderPath") + "\\" + className);
					
					File objDir=new File(properties.getProperty("FolderName"));
			        if(!objDir.exists())
                    {
			        	objDir.mkdir();
                    }
			        properties.setProperty("FolderName1",properties.getProperty("ResultsFolderPath") + "\\" + className + "\\" +methodName );
			        properties.store(fos, null);
			        fos.close();
			        fnOpenHTMLFile(methodName, strExecutionStartTime);
			        fnInsertSection();
			        fnInsertTestCaseName("");
			        
	        }
	
	        @SuppressWarnings("deprecation")
			public static void fnOpenHTMLFile(String sSection, String strExecutionStartTime) throws IOException, AWTException
	        {
			        g_iPass_Count=0;
			        g_iFail_Count=0;
			        g_sFileName = "sScriptName";
			        g_iImage_Capture = 1;
			        g_Total_TC = 0;
			        g_Total_Pass = 0;
			        g_Total_Fail = 0;
			        g_Flag = 0;
			        g_Flag1 = 0;
			        g_ScriptName="sScriptName";
			        
	        	
//			        String sConfigfilespath = "C:\\Automation QA\\Workspace\\TestFramework\\Config";
			        String sConfigfilespath = "Config";
			        File file = new File (sConfigfilespath + "\\Sys.properties");
        			FileOutputStream fos = new FileOutputStream( file);        	
			        
			        Date d = new Date();
			        String gsTempFile = properties.getProperty("FileName");
			        String TCModule = properties.getProperty("TCModule");
			        Path objPath=Paths.get(gsTempFile); 
			        if(gsTempFile =="")
	                {
			        	gsTempFile = properties.getProperty("FolderName1")+d.getYear() + d.getMonth() + d.getDay() + "_" + d.getHours() + d.getMinutes() + d.getSeconds()+ ".htm";	
	                    properties.setProperty("FileName", gsTempFile);
			        } 
			        properties.store(fos, null);
			        fos.close();
	        
			        if(Files.exists(objPath)) 
		        	  {
		        		 FileWriter objFile=new FileWriter(gsTempFile,true);
		        		 objFile.write("<HTML><BODY><TABLE BORDER=0 CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
		        		 objFile.write("<TR COLS=2><TD BGCOLOR=WHITE WIDTH=6%><IMG SRC='https://az846835.vo.msecnd.net/company/logos/GlobalPayments.png'></TD><TD WIDTH=94% BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=NAVY SIZE=3><B>Globalpay SDM Automation Test Results<BR/><FONT FACE=VERDANA COLOR=SILVER SIZE=2>Date: " + d +"</BR>On Machine :" + getComputerName() + "</B></FONT></TD><TD BGCOLOR=WHITE WIDTH=6%><IMG  SRC='https://www.tes.com/sites/all/themes/tes/logo.png'></TD></TR></TABLE>");
		        		 objFile.write("<TABLE BORDER=0 BGCOLOR=BLACK CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
		        		 objFile.write("<TR><TD BGCOLOR=#66699 WIDTH=50%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Module Name : "+ TCModule +"</B></FONT></TD><TD BGCOLOR=#66699 WIDTH=50%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Execution Start Time : " + strExecutionStartTime + "</B></FONT></TD></TR>");
		        		 objFile.write("</TABLE></BODY></HTML>");
		        		 objFile.close();
	                    }
			        ScreenVideoCapture.startVideoCapture();
	                g_tStart_Time = d;
			        g_sSection=sSection;
			        }

	        @SuppressWarnings("deprecation")
			public static void fnInsertSection() throws IOException
	        {
	        	Date d = new Date();
	        		String gsTempFile = properties.getProperty("FileName");
	        		Path objPath=Paths.get(gsTempFile); 
			        if(gsTempFile =="")
	                {
			        	gsTempFile = properties.getProperty("FolderName")+d.getYear() + d.getMonth() + d.getDay() + "_" + d.getHours() + d.getMinutes() + d.getSeconds()+ ".htm";	
			        } 
		
			        if(Files.exists(objPath)) 
		        	 {
		        		 FileWriter objFile=new FileWriter(gsTempFile,true);
		        		 objFile.write("<HTML><BODY><TABLE BORDER=1 CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
		        		 objFile.write("<TR COLS=6><TD BGCOLOR=#FFCC99 WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Test Case Name</B></FONT></TD><TD BGCOLOR=#FFCC99 WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Component</B></FONT></TD><TD BGCOLOR=#FFCC99 WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Expected Value</B></FONT></TD><TD BGCOLOR=#FFCC99 WIDTH=25%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Actual Value</B></FONT></TD><TD BGCOLOR=#FFCC99 WIDTH=25%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Result</B></FONT></TD></TR>");
		        		 objFile.close();
	                 }
	        }

	        @SuppressWarnings("deprecation")
			public static void fnInsertTestCaseName(String sDesc)
	        {
	        	Date d = new Date();
	        	String gsTempFile = properties.getProperty("FileName");
	        	//Path objPath=Paths.get(gsTempFile); 
		        if(gsTempFile =="")
                {
		        	gsTempFile = properties.getProperty("FolderName")+d.getYear() + d.getMonth() + d.getDay() + "_" + d.getHours() + d.getMinutes() + d.getSeconds()+ ".htm";	
		        } 
			        g_Total_TC = g_Total_TC+1;
			        if(g_Flag1!=0)
	                {
					        if(g_Flag == 0)
	                        {
						        g_Total_Pass = g_Total_Pass+1;
	                        }
					        else
	                    {
						        g_Total_Fail = g_Total_Fail+1;
				        }
			        }
			        g_Flag = 0;
	        }
////////////////////////////Test Case Initialization End/////////////////////////////////////////////////////////
	        
////////////////////////////Write Results Start//////////////////////////////////////////////////////////////////	        
	        @SuppressWarnings("deprecation")
			public static void fnInsertResult(WebDriver webDriver,String sTestCaseName, String sDesc, String sExpected, String sActual, String sResult) throws IOException
	        {
	    			//Common c = new Common();
	    			//driver= c.dr();
		            g_Flag1=1;
		            Date d = new Date();
		            String gsTempFile = properties.getProperty("FileName");
		            Path objPath=Paths.get(gsTempFile); 
			        if(gsTempFile =="")
	                {
			        	gsTempFile = properties.getProperty("FolderName")+d.getYear() + d.getMonth() + d.getDay() + "_" + d.getHours() + d.getMinutes() + d.getSeconds()+ ".htm";	
			        } 	
			        if(Files.exists(objPath)) 
		        	  {
		        		 FileWriter objFile=new FileWriter(gsTempFile,true);
				        if(sResult.equalsIgnoreCase("PASS"))
	                    {
					        g_iPass_Count = g_iPass_Count + 1;
					        if (properties.getProperty("CaptureScreenShotforPass").equalsIgnoreCase("YES"))
	                        {
	                                String I_sFile="";
							        g_iCapture_Count="Screen" + d.getHours() +d.getMinutes() + d.getSeconds();
	                                I_sFile = properties.getProperty("FolderName") + "\\screenshot\\" + g_iCapture_Count + ".png";
	                                if(webDriver != null)
	                                {
		                                File scrFile=((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);	
		                                FileUtils.copyFile(scrFile, new File(I_sFile));	  
		                                
	                                }
	                                
	                                //objFile.write("<TR COLS=5><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + sTestCaseName +"</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + sDesc +"</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2>" + sExpected +"</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2>" + sActual + "</A></FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE='WINGDINGS 2' SIZE=5 COLOR=GREEN>P</FONT><FONT FACE=VERDANA SIZE=2  COLOR=GREEN><B><A HREF='" + I_sFile +"'>" + sResult + "</A></B></FONT></TD></TR>");
	                                objFile.write("<TR COLS=5><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + sTestCaseName + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + sDesc + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2>" + sExpected +"</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2>" + sActual + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE='WINGDINGS 2' SIZE=5 COLOR=GREEN></FONT><FONT FACE=VERDANA SIZE=2 COLOR=GREEN><B><A HREF='" + I_sFile +"'>" + sResult + "</A></B></FONT></TD></TR>");
	                        }
	                        else
	                        {
	                        	objFile.write("<TR COLS=5><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + sTestCaseName + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + sDesc + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2>" + sExpected +"</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2>" + sActual + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE='WINGDINGS 2' SIZE=5 COLOR=GREEN></FONT><FONT FACE=VERDANA SIZE=2 COLOR=GREEN><B>" + sResult + "</B></FONT></TD></TR>");
	                        }
	                    }
				        else if(sResult.equalsIgnoreCase("FAIL"))
	                    {
						        g_Flag = 1;
						        g_SummaryFlag = 1;
						        g_iFail_Count = g_iFail_Count + 1;
						        if (properties.getProperty("CaptureScreenShotforFail").equalsIgnoreCase("YES"))
		                        {
						        	String I_sFile="";
							        g_iCapture_Count="Screen" + d.getHours() +d.getMinutes() + d.getSeconds();
	                                I_sFile = properties.getProperty("FolderName") + "\\Screen\\" + g_iCapture_Count + ".png";
	                                if(webDriver != null)
	                                {
		                                File scrFile=((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
		                                FileUtils.copyFile(scrFile, new File(I_sFile));	                                	
	                                }
	                                //objFile.write("<TR COLS=5><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + sTestCaseName +"</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + sDesc +"</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2>" + sExpected +"</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=WINGDINGS SIZE=4></FONT><FONT FACE=VERDANA SIZE=2>" + sActual +"</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE='WINGDINGS 2' SIZE=5 COLOR=RED>O</FONT><FONT FACE=VERDANA SIZE=2 COLOR=RED><B><A HREF='" + I_sFile +"'>" + sResult + "</A></B></FONT></TD></TR>");
	                                //objFile.write("<TR COLS=5><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + sTestCaseName +"</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + sDesc +"</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2>" + sExpected +"</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2>" + sActual +"</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE='WINGDINGS 2' SIZE=5 COLOR=RED>O</FONT><FONT FACE=VERDANA SIZE=2 COLOR=RED><B><A HREF='" + I_sFile +"'>" + sResult + "</A></B></FONT></TD></TR>");
	                                objFile.write("<TR COLS=5><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + sTestCaseName + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + sDesc + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2>" + sExpected +"</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2>" + sActual + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE='WINGDINGS 2' SIZE=5 COLOR=RED></FONT><FONT FACE=VERDANA SIZE=2 COLOR=RED><B><A HREF='" + I_sFile +"'style=color:#ff0000>" + sResult + "</A></B></FONT></TD></TR>");
	                            }
	                            else
	                            {
	                            	objFile.write("<TR COLS=5><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + sTestCaseName + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + sDesc + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2>" + sExpected +"</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2>" + sActual + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE='WINGDINGS 2' SIZE=5 COLOR=RED></FONT><FONT FACE=VERDANA SIZE=2 COLOR=RED><B>" + sResult + "</B></FONT></TD></TR>");
	                            }
				        }
	                    else if(sResult.equalsIgnoreCase("NOT COMPLETED"))
	                    {
	                    	 if (properties.getProperty("CaptureScreenShotforWarning").equalsIgnoreCase("YES"))
		                        {
	                    		 	String I_sFile="";
							        g_iCapture_Count="Screen" + d.getHours() +d.getMinutes() + d.getSeconds();
	                                I_sFile = properties.getProperty("FolderName") + "\\Screen\\" + g_iCapture_Count + ".jpeg";
	                                if(webDriver != null){
		                                File scrFile=((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
		                                FileUtils.copyFile(scrFile, new File(I_sFile));	                                	
	                                }
	                                //objFile.write("<TR COLS=5><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + sTestCaseName +"</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + sDesc +"</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2>" + sExpected +"</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=WINGDINGS SIZE=4>2></FONT><FONT FACE=VERDANA SIZE=2><A HREF='" + I_sFile +"'>" + sActual + "</A></FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE='WINGDINGS 2' SIZE=5 COLOR=RED>O</FONT><FONT FACE=VERDANA SIZE=2 COLOR=RED><B>" + sResult + "</B></FONT></TD></TR>");
	                                objFile.write("<TR COLS=5><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + sTestCaseName + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + sDesc + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2>" + sExpected +"</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2>" + sActual + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE='WINGDINGS 2' SIZE=5 COLOR=RED></FONT><FONT FACE=VERDANA SIZE=2 COLOR=RED><B><A HREF='" + I_sFile +"'>" + sResult + "</A></B></FONT></TD></TR>");
	                            }
	                            else
	                            {
	                                objFile.write("<TR COLS=5><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + sTestCaseName + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA SIZE=2>" + sDesc + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2>" + sExpected +"</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2>" + sActual + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE='WINGDINGS 2' SIZE=5 COLOR=RED></FONT><FONT FACE=VERDANA SIZE=2 COLOR=RED><B>" + sResult + "</B></FONT></TD></TR>");
	                            }
				        }
				        objFile.close();
	                    }
	        }
////////////////////////////Write Results End//////////////////////////////////////////////////////////////////
	        /*public static void fnSummaryInsertTestCase() throws IOException
	        {
	        	
                String tCaseID = properties.getProperty("TestCaseID");
                String tSetID = properties.getProperty("TestSetID");
                
	        		Date d = new Date();
	        		String gsTempFile = properties.getProperty("SummaryFileName");
	        		Path objPath=Paths.get(gsTempFile); 
			        if(gsTempFile =="")
	                {
				                gsTempFile = properties.getProperty("SummaryFolderName") +d.getYear() + d.getMonth() + d.getDay() + "_" + d.getHours() + d.getMinutes() + d.getSeconds()+ ".htm";	
			        }
			        g_SummaryTotal_TC = g_SummaryTotal_TC+1;
			        if (g_SummaryFlag==0)
	                {
					        g_SummaryTotal_Pass = g_SummaryTotal_Pass+1;
	                }
			        else
	                {
					        g_SummaryTotal_Fail = g_SummaryTotal_Fail+1;
	                } 
			        g_tSummaryTCEnd_Time = d;
	                    String strStatus="";
	                    switch (g_SummaryFlag)
	                    {
	                        case 0:
					            strStatus = "PASSED";
	                            break;
	                        case 1:
					            strStatus = "FAILED";
	                           break;
	                        default:
					            strStatus = "FAILED";
	                            break;
	                    }
	                    String intDateDiff = "";
	                    long diff = g_tSummaryTCEnd_Time.getTime() - g_tSummaryTCStart_Time.getTime();
	                    long intDateDiff1 = diff / (60 * 1000) % 60;
//	                    Date ts = d;

	                    if (intDateDiff1 == 0)
	                    {
	                            //intDateDiff = d.getSeconds() + " Seconds";
	                    		intDateDiff1 = diff / 1000 % 60;
	                    		intDateDiff = Long.toString(intDateDiff1) + " Seconds";
	                    }
				        else
	                    {
	                            //intDateDiff = d.getMinutes()+ " Minutes";
				        		intDateDiff1 = diff / (60 * 1000) % 60;
				        		intDateDiff = Long.toString(intDateDiff1) + " Minutes";
				        }

	                    if(Files.exists(objPath)) 
			        	  {
			        		 FileWriter objFile=new FileWriter(gsTempFile,true);
			                    if(strStatus.toUpperCase()== "PASSED")
	                            {
			                    	objFile.write("<TR COLS=6><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2>" + properties.getProperty("TCID") + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=45%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><A HREF='" + properties.getProperty("FileName") + "'>" + properties.getProperty("testcasename") + "</A></FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=20%><FONT FACE=VERDANA COLOR=BLACK SIZE=2>" + properties.getProperty("testcasename") + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2>" + intDateDiff + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=WINGDINGS 2' SIZE=5 COLOR=GREEN>P</FONT><FONT FACE=VERDANA SIZE=2 COLOR=GREEN><B>" + strStatus + "</B></FONT></TD></TR>");
	                            }
			                    else if (strStatus.toUpperCase()== "FAILED")
	                            {
			                    	objFile.write("<TR COLS=6><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2>" + properties.getProperty("TCID") + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=45%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><A HREF='" + properties.getProperty("FileName") + "'>" + properties.getProperty("testcasename") + "</A></FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=20%><FONT FACE=VERDANA COLOR=BLACK SIZE=2>" + properties.getProperty("testcasename") + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2>" + intDateDiff + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=WINGDINGS 2' SIZE=5 COLOR=RED>O</FONT><FONT FACE=VERDANA SIZE=2 COLOR=RED><B>" + strStatus + "</B></FONT></TD></TR>");
			                    }
			                   // int intDiff = 0;
			                    g_tSummaryTCStart_Time = d;
			                    objFile.close();
			                    String  ALMUpdate = properties.getProperty("ALMUpdate");
			                    if(ALMUpdate.contains("YES"))
			                    {
				                    Common.RunScript(tCaseID,tSetID,Integer.toString(g_SummaryFlag), properties.getProperty("FileName"), properties.getProperty("testcasename"),properties.getProperty("ALMUserName"),properties.getProperty("ALMPassword"),properties.getProperty("sQCURL"),properties.getProperty("sDomain"),properties.getProperty("sProject"),properties.getProperty("ALMDraftRun"));
//				                    ,properties.getProperty("ALMDraftRun")
			                    }
			                    g_SummaryFlag = 0;
	                     }
	        }       
	        
	        
*/	        
	        @SuppressWarnings("deprecation")
			public static void fnSummaryInsertTestCase(String moduleName,String feature,String scenarioName, String TCName, String strReportFileName, String Time, String strStatus, String strStopTime ) throws IOException
	        {
	        	
//                String tCaseID = properties.getProperty("TestCaseID");
//                String tSetID = properties.getProperty("TestSetID");
                
	        	
	        	
	        		Date d = new Date();
	        		String gsTempFile = properties.getProperty("SummaryFileName");
	        		String gsTempFile1 = properties.getProperty("FileName");
	        		//String feature1=properties.getProperty("TCModule");
	        		Path objPath=Paths.get(gsTempFile); 
	        		Path objPath1=Paths.get(gsTempFile1); 
			    	        	
	        		
	        		String[] arrTime = Time.split(" ");
	        		for (int i=0; i< arrTime.length; i++)
	        		{
	        			if (arrTime[i].contains("Hour"))
	        			{
	        				int temphour = Integer.parseInt(arrTime[i-1]);
	        				hour = hour + temphour;
	        			}
	        			if (arrTime[i].contains("Minute"))
	        			{
	        				int tempmin = Integer.parseInt(arrTime[i-1]);
	        				minute = minute + tempmin;
	        			}
	        			if (arrTime[i].contains("Second"))
	        			{
	        				int tempseconds = Integer.parseInt(arrTime[i-1]);
	        				second = second + tempseconds;
	        			}
	        		}
	        		
	        		
	        		if(gsTempFile =="")
	                {
				                gsTempFile = properties.getProperty("SummaryFolderName") +d.getYear() + d.getMonth() + d.getDay() + "_" + d.getHours() + d.getMinutes() + d.getSeconds()+ ".htm";	
			        }
			        if(gsTempFile1 =="")
	                {
				                gsTempFile1 = properties.getProperty("FolderName") +d.getYear() + d.getMonth() + d.getDay() + "_" + d.getHours() + d.getMinutes() + d.getSeconds()+ ".htm";	
			        }
			        g_SummaryTotal_TC = g_SummaryTotal_TC+1;
	/*		        if (g_SummaryFlag==0)
	                {
					        g_SummaryTotal_Pass = g_SummaryTotal_Pass+1;
	                }
			        else
	                {
					        g_SummaryTotal_Fail = g_SummaryTotal_Fail+1;
	                } */
			        g_tSummaryTCEnd_Time = d;
	                    /*String strStatus="";
	                    switch (g_SummaryFlag)
	                    {
	                        case 0:
					            strStatus = "PASSED";
	                            break;
	                        case 1:
					            strStatus = "FAILED";
	                           break;
	                        default:
					            strStatus = "FAILED";
	                            break;
	                    }*/
	                   /* String intDateDiff = "";
	                    long diff = g_tSummaryTCEnd_Time.getTime() - g_tSummaryTCStart_Time.getTime();
	                    long intDateDiff1 = diff / (60 * 1000) % 60;
//	                    Date ts = d;

	                    if (intDateDiff1 == 0)
	                    {
	                            //intDateDiff = d.getSeconds() + " Seconds";
	                    		intDateDiff1 = diff / 1000 % 60;
	                    		intDateDiff = Long.toString(intDateDiff1) + " Seconds";
	                    }
				        else
	                    {
	                            //intDateDiff = d.getMinutes()+ " Minutes";
				        		intDateDiff1 = diff / (60 * 1000) % 60;
				        		intDateDiff = Long.toString(intDateDiff1) + " Minutes";
				        }
*/
	                    if(Files.exists(objPath)) 
			        	  {
			        		 FileWriter objFile=new FileWriter(gsTempFile,true);
			                    if(strStatus.equalsIgnoreCase("PASS"))
	                            {
			                    	 g_SummaryTotal_Pass = g_SummaryTotal_Pass+1;
			                    	objFile.write("<TR COLS=6><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2>" + moduleName + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2>" + feature + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2>" + scenarioName + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><A HREF='" + strReportFileName + "'>" + TCName + "</A></FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2>" + Time + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=WINGDINGS 2' SIZE=5 COLOR=GREEN>P</FONT><FONT FACE=VERDANA SIZE=2 COLOR=GREEN><B>" + strStatus + "</B></FONT></TD></TR>");
	                            }
			                    else if (strStatus.equalsIgnoreCase("FAIL"))
	                            {
			                    	 g_SummaryTotal_Fail = g_SummaryTotal_Fail+1;
			                    	objFile.write("<TR COLS=6><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2>" + moduleName + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2>" + feature + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2>" + scenarioName + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><A HREF='" + strReportFileName + "'>" + TCName + "</A></FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2>" + Time + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=WINGDINGS 2' SIZE=5 COLOR=RED>O</FONT><FONT FACE=VERDANA SIZE=2 COLOR=RED><B>" + strStatus + "</B></FONT></TD></TR>");
			                    }
			                    else if (strStatus.equalsIgnoreCase("NOT COMPLETED"))
	                            {
			                    	 g_SummaryTotal_Fail = g_SummaryTotal_Fail+1;
			                    	objFile.write("<TR COLS=6><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2>" + moduleName + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2>" + feature + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2>" + scenarioName + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><A HREF='" + strReportFileName + "'>" + TCName + "</A></FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=15%><FONT FACE=VERDANA COLOR=BLACK SIZE=2>" + Time + "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=WINGDINGS 2' SIZE=5 COLOR=RED>O</FONT><FONT FACE=VERDANA SIZE=2 COLOR=RED><B>" + strStatus + "</B></FONT></TD></TR>");
			                    }
			                    
			        	 objFile.close();
			        	  }       
			                    if(Files.exists(objPath1)) 
					        	  {
					        		 FileWriter objFile1=new FileWriter(gsTempFile1,true);
					                    if(strStatus.equalsIgnoreCase("PASS"))
			                            {
					                    	 //g_SummaryTotal_Pass = g_SummaryTotal_Pass+1;
					                    	
					                    	objFile1.write("<TABLE BORDER=0 BGCOLOR=BLACK CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");					                    	
					                    	objFile1.write("<TR><TD BGCOLOR=#66699 WIDTH=15%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Execution Status : " + strStatus + "</B></FONT></B></FONT></TD><TD COLSPAN=16 BGCOLOR=#66699 ><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Execution Time: "+ Time + "</B></FONT></TD><TD COLSPAN=16 BGCOLOR=#66699 ><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Execution Stop Time : "+ strStopTime + "</B></FONT></TD></TR>");
					                    	
					                    	
			                            }
					                    else if (strStatus.equalsIgnoreCase("FAIL"))
			                            {
					                    	 //g_SummaryTotal_Fail = g_SummaryTotal_Fail+1;
					                    	
					                    	objFile1.write("<TABLE BORDER=0 BGCOLOR=BLACK CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");					                    	
					                    	objFile1.write("<TR><TD BGCOLOR=#66699 WIDTH=15%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Execution Status : <FONT FACE=VERDANA SIZE=2 COLOR=RED><B>" + strStatus + "</B></FONT></B></FONT></TD><TD COLSPAN=16 BGCOLOR=#66699 ><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Execution Time: "+ Time + "</B></FONT></TD><TD COLSPAN=16 BGCOLOR=#66699 ><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Execution Stop Time : "+ strStopTime + "</B></FONT></TD></TR>");	                    }
					                    
					                    else if (strStatus.equalsIgnoreCase("NOT COMPLETED"))
					                    {
					                    	 //g_SummaryTotal_Fail = g_SummaryTotal_Fail+1;
					                    	
					                    	objFile1.write("<TABLE BORDER=0 BGCOLOR=BLACK CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");					                    	
					                    	objFile1.write("<TR><TD BGCOLOR=#66699 WIDTH=15%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Execution Status : <FONT FACE=VERDANA SIZE=2 COLOR=RED><B>" + strStatus + "</B></FONT></B></FONT></TD><TD COLSPAN=16 BGCOLOR=#66699 ><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Execution Time: "+ Time + "</B></FONT></TD><TD COLSPAN=16 BGCOLOR=#66699 ><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Execution Stop Time : "+ strStopTime + "</B></FONT></TD></TR>");	                    }
					                    
					                    objFile1.close();
					        	  }
			                    
			                    
			                   // int intDiff = 0;
			            
			                    g_tSummaryTCStart_Time = d;
			                    
			                    
/*			                    String  ALMUpdate = properties.getProperty("ALMUpdate");
			                    if(ALMUpdate.contains("YES"))
			                    {
				                    Common.RunScript(tCaseID,tSetID,Integer.toString(g_SummaryFlag), properties.getProperty("FileName"), properties.getProperty("testcasename"),properties.getProperty("ALMUserName"),properties.getProperty("ALMPassword"),properties.getProperty("sQCURL"),properties.getProperty("sDomain"),properties.getProperty("sProject"),properties.getProperty("ALMDraftRun"));
//				                    ,properties.getProperty("ALMDraftRun")
			                    }*/
			                   // g_SummaryFlag = 0;
	                     }
	            
////////////////////////////Write Test Case Summary Results End//////////////////////////////////////////////////////////////////	   	        
	        
////////////////////////////Close Summary Report Start///////////////////////////////////////////////////////////////////////////
	    /*    public static void fnSummaryCloseHTML(String strRelease) throws IOException
	        {
	        		Date d = new Date();
             	String gsTempFile = properties.getProperty("SummaryFileName");
	        		//String gsTempFile ="C:\\Automation\\workspace\\HLPayroll_V1\\results\\ClientDocuments\\ClientDocuments_testClientDocuments_Report_2017-05-19-03.50.04.HTML";
	             	System.out.println("gsTempFile value " + gsTempFile);
	        	 	Path objPath=Paths.get(gsTempFile); 
	       			if(gsTempFile =="")
	                {
				                gsTempFile = properties.getProperty("SummaryFolderName") +d.getYear() + d.getMonth() + d.getDay() + "_" + d.getHours() + d.getMinutes() + d.getSeconds()+ ".htm";	
			        }
	            
			        g_tSummaryEnd_Time = d;
                   // String sTime ;
                   ////Date ts = g_tSummaryStart_Time - g_tSummaryEnd_Time;
                    Date ts = d;
                    //// Difference in days.
                    ////int differenceInDays = ts.Minutes;

                    //if (ts.getMinutes() == 0)
                    //{
                    //        sTime = (String)(ts.getSeconds() + " Seconds");
                    //}
			        //else
                   // {
                   //         sTime =  (String)(ts.getMinutes()  + " Minutes");
			       // }

                    
                    String sTime = "";
                    long diff = g_tSummaryEnd_Time.getTime() - g_tSummaryStart_Time.getTime();
                    long intDateDiff1 = diff / (60 * 1000) % 60;
//                    Date ts = d;
                    System.out.println("summary start time " + g_tSummaryStart_Time);
                    System.out.println("summary End time " + g_tSummaryEnd_Time);
                    if (intDateDiff1 == 0)
                    {
                            //intDateDiff = d.getSeconds() + " Seconds";
                    		intDateDiff1 = diff / 1000 % 60;
                    		sTime = Long.toString(intDateDiff1) + " Seconds";
                    }
			        else
                    {
                            //intDateDiff = d.getMinutes()+ " Minutes";
			        		intDateDiff1 = diff / (60 * 1000) % 60;
			        		sTime = Long.toString(intDateDiff1) + " Minutes";
			        }
                    
                    
                    if(Files.exists(objPath)) 
		        	  {
		        		 FileWriter objFile=new FileWriter(gsTempFile,true);    
		        		 objFile.write("<HTML><BODY><TABLE BORDER=1 CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
		        		 objFile.write("<TR><TD BGCOLOR=#66699 WIDTH=15%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Release :</B></FONT></TD><TD COLSPAN=6 BGCOLOR=#66699><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>" + strRelease + "</B></FONT></TD></TR>");
		        		 objFile.write("<TR COLS=5><TD BGCOLOR=#66699 WIDTH=25%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Total Test Case Executed : " + g_SummaryTotal_TC + "</B></FONT></TD><TD BGCOLOR=#66699 WIDTH=15%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Total Test Cases Passed : " + g_SummaryTotal_Pass + "</B></FONT></TD><TD BGCOLOR=#66699 WIDTH=25%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B> Total Test Cases Failed : " + g_SummaryTotal_Fail + "</B></FONT></TD><TD BGCOLOR=#66699 WIDTH=25%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Total Execution Time : " + sTime + " </B></FONT></TD></TR>");
		        		 objFile.write("</TABLE></BODY></HTML>");
		        		 objFile.close();
                     }
                    //String  SendMail = properties.getProperty("SendMail");
                    //if(SendMail.contains("mail"))
                    //{
                    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                    //Common.SendMail(properties.getProperty("MailTo"), properties.getProperty("MailCC"), gsTempFile, formatter.format(g_tSummaryEnd_Time),formatter.format(g_tSummaryStart_Time),strRelease, properties.getProperty("ModuleName"), String.valueOf(g_SummaryTotal_TC), String.valueOf(g_SummaryTotal_Pass),String.valueOf(g_SummaryTotal_Fail), properties.getProperty("Region"));
                   // 	Common.SendMail(properties.getProperty("MailTo"), properties.getProperty("MailCC"), gsTempFile, formatter.format(g_tSummaryEnd_Time),formatter.format(g_tSummaryStart_Time),strRelease, properties.getProperty("ModuleName"), String.valueOf(g_SummaryTotal_TC), String.valueOf(g_SummaryTotal_Pass),String.valueOf(g_SummaryTotal_Fail), properties.getProperty("Region"));
                    	//Common.SendMail("hemavathi.lakshmanan@e-hps.com", "leela.prasad@globalpay.com", gsTempFile, "2017-05-19 16:30:00","2017-05-19 16:00:00","v1", "ModuleName", "5", "4","1", "QA");
                    //}
	        }*/
	        
	        @SuppressWarnings("deprecation")
			public static void fnSummaryCloseHTML() throws IOException
	        {
	        	
	        		Date d = new Date();
             	String gsTempFile = properties.getProperty("SummaryFileName");
	        		//String gsTempFile ="C:\\Automation\\workspace\\HLPayroll_V1\\results\\ClientDocuments\\ClientDocuments_testClientDocuments_Report_2017-05-19-03.50.04.HTML";
	             	//System.out.println("gsTempFile value " + gsTempFile);
	        	 	Path objPath=Paths.get(gsTempFile); 
	       		String strRelease = properties.getProperty("Release");
	       		String strSprint = properties.getProperty("Sprint");
	        	 	if(gsTempFile =="")
	                {
				                gsTempFile = properties.getProperty("SummaryFolderName") +d.getYear() + d.getMonth() + d.getDay() + "_" + d.getHours() + d.getMinutes() + d.getSeconds()+ ".htm";	
			        }
	            
			        g_tSummaryEnd_Time = d;
                   // String sTime ;
                   ////Date ts = g_tSummaryStart_Time - g_tSummaryEnd_Time;
                  //  Date ts = d;
                    //// Difference in days.
                    ////int differenceInDays = ts.Minutes;

                    //if (ts.getMinutes() == 0)
                    //{
                    //        sTime = (String)(ts.getSeconds() + " Seconds");
                    //}
			        //else
                   // {
                   //         sTime =  (String)(ts.getMinutes()  + " Minutes");
			       // }

/*                    
                   String sTime = "";
                //    long diff = g_tSummaryEnd_Time.getTime() - g_tSummaryStart_Time.getTime();
               //     long intDateDiff1 = diff / (60 * 1000) % 60;
//                    Date ts = d;
                    System.out.println("summary start time " + g_tSummaryStart_Time);
                    System.out.println("summary End time " + g_tSummaryEnd_Time);
                    if (intDateDiff1 == 0)
                    {
                            //intDateDiff = d.getSeconds() + " Seconds";
                    		intDateDiff1 = diff / 1000 % 60;
                    		sTime = Long.toString(intDateDiff1) + " Seconds";
                    }
			        else
                    {
                            //intDateDiff = d.getMinutes()+ " Minutes";
			        		//intDateDiff1 = diff / (60 * 1000) % 60;
			        		//sTime = Long.toString(intDateDiff1) + " Minutes";
			        }*/
                    System.out.println("Total hours in summary close " + hour);
                    System.out.println("Total minutes in summary close " + minute);
                    System.out.println("Total seconds in summary close " + second);
                    int hours;
                    int minutes;
                    
                    int newSeconds;
                    int newSecond1;
                    String strExecutiontime="";
                    
                    int seconds= (hour*60*60)+(minute*60)+second;
                    
                    if (seconds >= 3600) {
            			hours = seconds / 3600;
            			minutes = (seconds % 3600) / 60;
            			newSeconds = (seconds % 3600) % 60;
            			if (minutes == 0) {
            				newSecond1 = (seconds % 3600) % 60;
            				if (newSecond1 != 0) {
            					strExecutiontime = hours + " Hour(s)" + newSecond1 + " Second(s)";
            				} else {
            					strExecutiontime = hours + " Hour(s)";
            				}
            			}

            			if (minutes > 0 && minutes < 60) {
            				if (newSeconds > 0 && minutes > 0) {
            					strExecutiontime = hours + " Hour(s) " + minutes + " Minute(s)" + newSeconds + " Second(s)";
            				}
            				if (newSeconds == 0 && minutes > 0) {
            					strExecutiontime = hours + " Hour(s) " + minutes + " Minute(s)";
            				}
            			}

            			if (minutes > 60) {
            				seconds = minutes % 60;
            				minutes = minutes / 60;
            				strExecutiontime = hours + " Hour(s) " + minutes + " Minutes " + seconds + " Seconds";
            			}

            		} else {
            			minutes = seconds / 60;
            			seconds = seconds % 60;
            			if (minutes > 0 && seconds == 0) {
            				strExecutiontime = minutes + " Minute(s)";
            			}
            			if (minutes > 0 && seconds > 0) {
            				strExecutiontime = minutes + " Minute(s) " + seconds + " Second(s)";
            			}
            			if (minutes == 0) {
            				strExecutiontime = seconds + " Second(s)";
            			}
            		}
                    
                 String   sTime = strExecutiontime;
                    
                    if(Files.exists(objPath)) 
		        	  {
		        		 FileWriter objFile=new FileWriter(gsTempFile,true);    
		        		 objFile.write("<HTML><BODY><TABLE BORDER=1 CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
		        		 objFile.write("<TR><TD BGCOLOR=#66699 WIDTH=15%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Release : "+ strRelease + "</B></FONT></TD><TD COLSPAN=6 BGCOLOR=#66699><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Sprint Number : "  + strSprint + "</B></FONT></TD></TR>");
		        		 objFile.write("<TR COLS=5><TD BGCOLOR=#66699 WIDTH=25%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Total Test Case Executed : " + g_SummaryTotal_TC + "</B></FONT></TD><TD BGCOLOR=#66699 WIDTH=15%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Total Test Cases Passed : " + g_SummaryTotal_Pass + "</B></FONT></TD><TD BGCOLOR=#66699 WIDTH=25%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B> Total Test Cases Failed : " + g_SummaryTotal_Fail + "</B></FONT></TD><TD BGCOLOR=#66699 WIDTH=25%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Total Execution Time : " + sTime + " </B></FONT></TD></TR>");
		        		 objFile.write("</TABLE></BODY></HTML>");
		        		 objFile.close();
                     }
                    String  SendMail = properties.getProperty("SendMail");
                    if(SendMail.equalsIgnoreCase("yes"))
                    {
                    //	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	         
                   	Common.SendMail(properties.getProperty("MailTo"), properties.getProperty("MailCC"), gsTempFile, sTime, strRelease, properties.getProperty("ModuleName"), String.valueOf(g_SummaryTotal_TC), String.valueOf(g_SummaryTotal_Pass),String.valueOf(g_SummaryTotal_Fail), properties.getProperty("Region"));
                    }
             
	        }
////////////////////////////Close Summary Report End/////////////////////////////////////////////////////////////////////////////
	        public static String fnSecondsToTime(int intSeconds)
	        {
				        int hours, minutes, seconds;
				        hours = intSeconds / 3600;
				        intSeconds = intSeconds % 3600;
				        minutes = intSeconds / 60;
				        seconds = intSeconds % 60;
				        return hours + ":" + minutes + ":" + seconds;
	        }
////////////////////////////Send Email End/////////////////////////////////////////////////////////////////////////////////////	        
}