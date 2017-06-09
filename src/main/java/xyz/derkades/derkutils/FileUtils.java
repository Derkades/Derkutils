package xyz.derkades.derkutils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FileUtils {
	
	public static String getFileName(File file){
		String name = file.getName();
		int pos = name.lastIndexOf(".");
		if (pos > 0) {
		    name = name.substring(0, pos);
		}
		return name;
	}
	
	public static void appendStringToFile(File file, String string){
		try {
			Writer writer = new BufferedWriter(new FileWriter(file, true));
			writer.append(string);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
