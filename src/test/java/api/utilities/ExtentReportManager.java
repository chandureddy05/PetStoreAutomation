package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.xmlbeans.SystemProperties;
import org.codehaus.groovy.tools.ErrorReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener
{
	
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String repName;
	
	public void onStart(ITestContext testContext)
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.ss").format(new Date());
		repName = "Test-Report-"+timeStamp+".html";
		
		sparkReporter=new ExtentSparkReporter(".\\reports\\"+repName);
		
		sparkReporter.config().setDocumentTitle("RestAssuredAutomationProject");
		sparkReporter.config().setReportName("Pet Store User API");
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Pet Store User API");
		extent.setSystemInfo("OPerating System", System.getProperty("os.name"));
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("user", "chandu");
		
	}
	
	public void onTestSuccess(ITestContext result){
		
		test=extent.createTest(result.getName());
		test.assignCategory(result.getIncludedGroups());
		//test.assignCategory(result.getMethods().getGroups());
		test.createNode(result.getName());
		test.log(Status.PASS, "Test Passed");
	}
	
		public void onTestFailure(ITestContext result){
				
				test=extent.createTest(result.getName());
				test.assignCategory(result.getIncludedGroups());
				//test.assignCategory(result.getMethods().getGroups());
				test.createNode(result.getName());
				test.log(Status.FAIL, "Test Failed");
				//test.log(Status.FAIL, result.getThrowable().getMesssage());
		}
		
		public void onTestSkipped(ITestContext result){
			
			test=extent.createTest(result.getName());
			test.assignCategory(result.getIncludedGroups());
			//test.assignCategory(result.getMethods().getGroups());
			test.createNode(result.getName());
			test.log(Status.SKIP, "Test SKIPPED");
			//test.log(Status.FAIL, result.getThrowable().getMesssage());
		}
		
		public void onFinish(ITestContext testContext)
		{
			extent.flush();
		}
	
}
