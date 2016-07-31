package newCore;

import java.text.SimpleDateFormat;
import java.util.Date;

import newCore.utils.FileUtils;

public class ExecutionLog {

	private String logLocation = "C:/Log/";
	private String logFileName;
	
	public ExecutionLog(Object object){
		String pattern = "HH-mm-ss-SSS";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date = simpleDateFormat.format(new Date());
		logFileName = object.getClass().getSimpleName()+"_"+date+".csv";
		createLogFile();
	}
	
	private void createLogFile(){
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date = simpleDateFormat.format(new Date());
		FileUtils.crtFile(logLocation+date+"/"+logFileName);
	}
	
	public void logNote(String noteName, String noteComment){
		
	}
}
