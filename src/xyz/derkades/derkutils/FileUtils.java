package xyz.derkades.derkutils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileUtils {

	/**
	 * @param file
	 * @return File name without extension. For extensions like .tar.gz only .gz will be returned.
	 */
	public static String getFileName(final File file){
		String name = file.getName();
		final int pos = name.lastIndexOf(".");
		if (pos > 0) {
		    name = name.substring(0, pos);
		}
		return name;
	}

	/**
	 * Copies any file in a jar file to an outside locations. Fails silently if the file already exists.
	 * @param clazz Class this method is called from
	 * @param pathToFileInJar
	 * @param outputFile
	 */
	public static void copyOutOfJar(final Class<?> clazz,
									final String pathToFileInJar,
									final File outputFile) throws IOException {
		Objects.requireNonNull(clazz, "Class is null");
		Objects.requireNonNull(pathToFileInJar, "File path is null");
		Objects.requireNonNull(outputFile, "Output file is null");

		if (!outputFile.exists()){
			final URL inputUrl = clazz.getResource(pathToFileInJar);
			try (InputStream in = inputUrl.openStream()) {
				Files.copy(in, outputFile.toPath());
			}
		}
	}

	/**
	 * Copies any file in a jar file to an outside locations. Fails silently if the file already exists.
	 * @param clazz Class this method is called from
	 * @param pathToFileInJar
	 * @param outputFile
	 */
	public static void copyOutOfJar(final Class<?> clazz,
									final String pathToFileInJar,
									final Path outputFile) throws IOException {
		Objects.requireNonNull(clazz, "Class is null");
		Objects.requireNonNull(pathToFileInJar, "File path is null");
		Objects.requireNonNull(outputFile, "Output file is null");

		if (!Files.exists(outputFile)) {
			try (InputStream in = Objects.requireNonNull(clazz.getClassLoader().getResourceAsStream(pathToFileInJar),
					"path does not exist in jar: " + pathToFileInJar)) {
				Files.copy(in, outputFile);
			}
		}
	}

}
