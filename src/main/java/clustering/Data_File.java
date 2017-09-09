package clustering;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class File {

	String Path;
	String Name;

	List<String> Values = new ArrayList<String>();

	public File(String path, String name) {
		super();
		Path = path;
		Name = name;
		Name += ".txt";
	}

	public File() {
		super();

		Path = "/Users/lukaskrabbe/Desktop/TwitterData/";
		Name = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		Name += ".txt";

	}

	public List<String> addVal(String val) {

		Values.add(val);
		return Values;

	}

	public List<String> getValues() {
		return Values;
	}

	public boolean toFile() {
		
		FileWriter filWr = null; 
		BufferedWriter bufWr = null;
		
		try {

			filWr = new FileWriter(Path + Name);
			bufWr = new BufferedWriter(filWr);

			for (String val : Values) {
				bufWr.write(val);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (bufWr != null)
					bufWr.close();

				if (filWr != null)
					filWr.close();

			} catch (IOException ex) {
				ex.printStackTrace();
				return false;
			}
		}

		return true;
	}

}
