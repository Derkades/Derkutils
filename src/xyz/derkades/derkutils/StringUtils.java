package xyz.derkades.derkutils;

import java.util.Objects;

public class StringUtils {

	/**
	 * @param string
	 * @param allowSpaces False if spaces should be considered as non-alphanumeric characters. (e.g. "hello world" will return false but "helloworld" will return true)
	 * @return True if the specified string contains characters other than A-Z, a-z, 0-9 and optionally spaces.
	 */
	public static boolean containsNonAlphanumericalCharacters(final String string,
															  final boolean allowSpaces){
		Objects.requireNonNull(string, "String is null");

		final String withoutSpecialCharacters;

		if (allowSpaces){
			withoutSpecialCharacters = string.replaceAll("[^A-Za-z0-9 ]", "");
		} else {
			withoutSpecialCharacters = string.replaceAll("[^A-Za-z0-9]", "");
		}

		//If the string is the same as the original it did not contain any non-alphanumeric characters
		return !withoutSpecialCharacters.equals(string);
	}

	/**
	 * @param string
	 * @return true if the string contains only numbers, letters and underscores.
	 */
	public static boolean validateString(final String string) {
		Objects.requireNonNull(string,"Provided string is null");

		for (final char c : string.toCharArray()){
			if (!Character.isLetterOrDigit(c) & c != '_'){
				return false;
			}
		}
		return true;
	}

	/**
	 * Adds a dot to a string if it does not end with ? ! or .
	 * @param string
	 * @return
	 */
	public static String addDotIfNecessary(final String string) {
		Objects.requireNonNull(string, "Provided string is null");

		if (string.endsWith(".") || string.endsWith("?") || string.endsWith("!")){
			return string;
		} else {
			return string + ".";
		}
	}

	/**
	 * Adds <i>append</i> to <i>string</i> if <i>string</i> does not end with <i>append</i>.
	 * @param string
	 * @param append
	 * @return
	 */
	public static String appendIfNotPresent(final String string,
											final String append) {
		Objects.requireNonNull(string, "Provided string is null");
		Objects.requireNonNull(append, "Append string is null");
		if (string.endsWith(append)) {
			return string;
		} else {
			return string + append;
		}
	}

}
