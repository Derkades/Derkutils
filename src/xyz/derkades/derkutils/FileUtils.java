package xyz.derkades.derkutils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;

public class FileUtils {
	
	/**
	 * @param file
	 * @return File name without extension. For extensions like .tar.gz only .gz will be returned.
	 */
	public static String getFileName(File file){
		String name = file.getName();
		int pos = name.lastIndexOf(".");
		if (pos > 0) {
		    name = name.substring(0, pos);
		}
		return name;
	}
	
	/**
	 * Adds a string to the end of a file
	 */
	public static void appendStringToFile(File file, String string){
		try (Writer writer = new BufferedWriter(new FileWriter(file, true))){
			writer.append(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Copies any file in a jar file to an outside locations. Fails silently if the file already exists.
	 * @param clazz Class this method is called from
	 * @param pathToFileInJar
	 * @param outputFile
	 */
	public static void copyOutOfJar(Class<?> clazz, String pathToFileInJar, File outputFile) throws IOException {
		if (!outputFile.exists()){
			URL inputUrl = clazz.getResource(pathToFileInJar);
			org.apache.commons.io.FileUtils.copyURLToFile(inputUrl, outputFile);
		}
	}

}
