package core.pom;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;

public class ObjectCSVReader

{
	private final String objectcsv_filepath = System.getProperty("user.dir") + "/src/main/resources/ObjectRep.csv"; 

	public List<ObjectRepReader> csvreader () throws IOException
	{
		// creating jar class objects

		CsvReader csvreader = new CsvReader(objectcsv_filepath);
		csvreader.setDelimiter('$');
		csvreader.readHeaders();

		// to create collection object to hold the values of while loop:

		List<ObjectRepReader> objectlist = new ArrayList<ObjectRepReader>();
		while(csvreader.readRecord())
		{
			String page = csvreader.get("Page");
			String logicalName = csvreader.get("logicalname");
			String locatorType = csvreader.get("locatortype");
			String value = csvreader.get("value");

			ObjectRepReader obj = new ObjectRepReader();

			obj.setPage(page);
			obj.setLogicalName(logicalName);
			obj.setLocatorType(locatorType);
			obj.setValue(value);

			objectlist.add(obj);//store value of objectrepreader in object list 

		}

		return objectlist;
	}

}
