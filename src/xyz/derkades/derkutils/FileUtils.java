package xyz.derkades.derkutils;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileUtils {

	/**
	 * @param file
	 * @return File name without extension. For extensions like .tar.gz only .gz will be returned.
	 */
	@NotNull
	public static String getFileName(@NotNull final File file){
		String name = file.getName();
		final int pos = name.lastIndexOf(".");
		if (pos > 0) {
		    name = name.substring(0, pos);
		}
		return name;
	}

	/**
	 * Adds a string to the end of a file
	 */
	public static void appendStringToFile(@NotNull final File file, @NotNull final String string){
		try (final Writer writer = new BufferedWriter(new FileWriter(file, true))){
			writer.append(string);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Copies any file in a jar file to an outside locations. Fails silently if the file already exists.
	 * @param clazz Class this method is called from
	 * @param pathToFileInJar
	 * @param outputFile
	 */
	public static void copyOutOfJar(@NotNull final Class<?> clazz, @NotNull final String pathToFileInJar,
									@NotNull final File outputFile) throws IOException {
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
	public static void copyOutOfJar(@NotNull final Class<?> clazz, @NotNull final String pathToFileInJar,
									@NotNull final Path outputFile) throws IOException {
		Objects.requireNonNull(clazz, "Class is null");
		Objects.requireNonNull(pathToFileInJar, "File path is null");
		Objects.requireNonNull(outputFile, "Output file is null");

		if (!Files.exists(outputFile)) {
			final URL inputUrl = clazz.getResource(pathToFileInJar);
			try (InputStream in = inputUrl.openStream()) {
				Files.copy(in, outputFile);
			}
		}
	}

}
