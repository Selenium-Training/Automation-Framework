package newCore;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;

import newCore.utils.FileUtils;

public class ExecutionLog {

	private String logLocation = "C:/Log/";
	private String logFileName;
	private String completeLogFileName;
	
	private int totalRunChecks = 0;
	private int passedRunChecks = 0;
	
	public ExecutionLog(Object object){
		String pattern = "HH-mm-ss-SSS";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date = simpleDateFormat.format(new Date());
		String scriptName = object.getClass().getSimpleName();
		String testSet = scriptName.split("_", 2)[0];
		String testScript = scriptName.split("_", 2)[1];
		logFileName = testSet + "/" + testScript+"_"+date+".csv";
		
		createLogFile();
		logNote("Started Execution For Script", testScript);
	}
	
	private void createLogFile(){ 
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String folderName = simpleDateFormat.format(new Date());
		completeLogFileName = logLocation+folderName+"/"+logFileName;
		FileUtils.crtFile(completeLogFileName);
		
		/* Add Headers To Log File*/
		String headers = "LogType,Description,Comment,Result,TimeStamp";
		FileUtils.appendLine(completeLogFileName, headers);
	}
	
	public void logNote(String description, String noteComment){
		String pattern = "yyyy-MM-dd HH-mm-ss-SSS"; 
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String timeStamp = simpleDateFormat.format(new Date());
		
		String note = "Note,"+description +"," +noteComment +",,"+timeStamp;
		FileUtils.appendLine(completeLogFileName, note);
		
	}
	
	public void logStep(String description, String comment){
		String pattern = "yyyy-MM-dd HH-mm-ss-SSS"; 
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String timeStamp = simpleDateFormat.format(new Date());
		
		String logStep = "Step,"+description +"," +comment +",,"+timeStamp;
		FileUtils.appendLine(completeLogFileName, logStep);
		
	}
	
	public void logRunCheck (String description, Boolean result , String comment){
		totalRunChecks++;
		
		if(result){
			passedRunChecks++;
		}
		
		String pattern = "yyyy-MM-dd HH-mm-ss-SSS"; 
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String timeStamp = simpleDateFormat.format(new Date());
		
		String logRunCheck = "Run Check,"+description +"," +comment +"," +result +"," +timeStamp;
		FileUtils.appendLine(completeLogFileName, logRunCheck);
	} 
	
	public void logException (Exception exception) {
		String stackTrace = exception.getStackTrace().toString();
		String message = exception.getMessage();
		logNote(message, stackTrace);
	}
	
	private void logFinalResult(boolean flag){

		String pattern = "yyyy-MM-dd HH-mm-ss-SSS"; 
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String timeStamp = simpleDateFormat.format(new Date());
		
		String status;
		if(flag == true){
			status = "PASSED";
		} else {
			status = "FAILED";
		}
		
		String result = "Result,"+status+",Script "+status+" Successfully,,"+timeStamp;
		FileUtils.appendLine(completeLogFileName, result);
		
	}
	
	public void logScriptStatus(){
		if(totalRunChecks != passedRunChecks){
			logFinalResult(false);
			Assert.fail("Script Failed!!");
		} else {
			logFinalResult(true);
		}
		
		
		
	}
}
