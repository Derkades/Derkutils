package xyz.derkades.derkutils;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

public class ListUtils {

	/**
	 * Not case sensitive
	 * @see #stringListContainsString(List, String, boolean)
	 */
	public static boolean stringListContainsString(final List<String> list, final String string){
		return stringListContainsString(list, string, false);
	}

	/**
	 * @param list
	 * @param string
	 * @param caseSensitive
	 * @return
	 * 		True if any string inside the list contains the specified string
	 * 		<br><br>Example:
	 * 		<ul>
	 * 			<li>Hello world</li>
	 * 			<li>Foo</li>
	 * 		</ul>
	 * 		For string "oo" - Returns true
	 * 		<br>For string "Hello world" - Returns true
	 * 		<br>For string "Bar" - Returns false
	 */
	public static boolean stringListContainsString(final List<String> list, final String string, final boolean caseSensitive){
		boolean contains = false;
		for (final String entry : list){
			if (caseSensitive) {
				if (entry.contains(string)){
					contains = true;
				}
			} else {
				if (entry.toLowerCase().contains(string.toLowerCase())) {
					contains = true;
				}
			}
		}
		return contains;
	}

	/**
	 * @param resultSet
	 * @param column
	 * @return A list of all values in a column as returned by an SQL query
	 * @throws SQLException
	 */
	@Deprecated
	public static List<String> getStringListFromResultSet(final ResultSet resultSet, final String column) throws SQLException {
		final List<String> list = new ArrayList<>();

		while (resultSet.next()){
			list.add(resultSet.getString(column));
		}

		return list;
	}

	/**
	 * @param array
	 * @return A random element from an array
	 * @see #getRandomValueFromList(List)
	 */
	@Deprecated
	public static <T> T getRandomValueFromArray(final T[] array){
		Validate.notEmpty(array, "Array is null or empty");

		if (array.length == 1) {
			return array[0];
		}

		final int size = array.length;
		final int index = Random.getRandomInteger(0, size - 1);
		return array[index];
	}

	
	/**
	 * @param list
	 * @return A random element from the provided list
	 */
	@Deprecated
	public static <T> T getRandomValueFromList(final List<T> list) {
		Validate.notEmpty(list, "List is null or empty");

		if (list.size() == 1) {
			return list.get(0);
		}

		final int size = list.size();
		final int index = Random.getRandomInteger(0, size - 1); //Size -1 because if the list has 1 entry (entry 0) the length is 1.
		return list.get(index);
	}

	/**
	 * Removes the first string from a string array
	 * @param array
	 * @return Array without the first string
	 * @deprecated Use Arrays.copyOfRange(array, 1, array.length);
	 */
	@Deprecated
	public static String[] removeFirstStringFromArray(final String[] array){
		final int n = array.length - 1;
		final String[] newArray = new String[n];
		System.arraycopy(array, 1, newArray, 0, n);
		return newArray;
	}

	/**
	 * Replaces strings with other strings in a string list.
	 * @param list ["Hello world", "Lorem ipsum"]
	 * @param before ["world", "ipsum"]
	 * @param after ["there", "dolor"]
	 * @return ["Hello there", "Lorem dolor"]
	 */
	public static List<String> replaceInStringList(final List<String> list, final Object[] before, final Object[] after) {
		Validate.isTrue(before.length == after.length, "before[] length must be equal to after[] length");

		final List<String> newList = new ArrayList<>();

		for (String string : list) {
			for (int i = 0; i < before.length; i++) {
				string = string.replace(before[i].toString(), after[i].toString());
			}
			newList.add(string);
		}

		return newList;
	}

	public static List<String> replaceInStringList(final List<String> list, final Object before, final Object after){
		return replaceInStringList(list, new Object[] {before}, new Object[] {after});
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] mergeArrays(final T[]... arrays) {
		final List<T> list = new ArrayList<>();
		for (final T[] array : arrays) {
			list.addAll(Arrays.asList(array));
		}
		return (T[]) list.toArray();
	}

	@SafeVarargs
	@Deprecated
	public static <T> List<T> addToList(final List<T> list, final T... items) {
		for (final T item : items) {
			list.add(item);
		}

		return list;
	}

	@SafeVarargs
	@Deprecated
	public static <T> List<T> addToList(final List<T> list, final List<T>... listsToAdd){
		for (final List<T> listToAdd : listsToAdd) {
			list.addAll(listToAdd);
		}

		return list;
	}

	/**
	 * Fill a list with numbers between min and max
	 * @param min Inclusive
	 * @param max Inclusive
	 * @return
	 */
	@Deprecated
	public static List<Integer> getNumbersList(final int min, final int max) {
		final List<Integer> list = new ArrayList<>();
		for (int i = min; i <= max; i++) {
			list.add(i);
		}
		return list;
	}

	@Deprecated
	public static List<Integer> getRandomizedNumbersList(final int min, final int max) {
		final List<Integer> list = getNumbersList(min, max);
		Collections.shuffle(list);
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] combineArrays(final T[]... arrays) {
		final List<T> list = new ArrayList<>();
		for (final T[] array : arrays) {
			list.addAll(Arrays.asList(array));
		}
		return (T[]) list.toArray();
	}

	/**
	 * Removes the first element from the list {@code amount} times. If {@code amount} is greater than {@code list.size()}, the list is cleared.
	 * @param <T>
	 * @param amount Amount of times to remove the first element from {@code list}
	 * @param list
	 * @return
	 */
	@Deprecated
	public static <T> void removeLeadingElementsFromList(final int amount, final List<T> list){
		if (amount > list.size()) {
			list.clear();
		}

		for (int i = 0; i < amount; i++) {
			list.remove(0);
		}
	}

	/**
	 * @deprecated Untested
	 */
	@Deprecated
	public static <T> T[] removeLeadingElementsFromArray(final int amount, final T[] array) {
		@SuppressWarnings("unchecked")
		final
		T[] copy = (T[]) Array.newInstance(array.getClass(), array.length - amount);
		for (int i = amount - 1; i < array.length - amount; i++) {
			copy[i] = array[i + amount];
		}
		return copy;
	}

	@Deprecated
	public static <T> List<T> copyArrayToList(final T[] array){
		final List<T> list = new ArrayList<>();
		for (final T element : array) {
			list.add(element);
		}
		return list;
	}

	@Deprecated
	public static <T> T[] subarray(final T[] array, final int startIndexInclusive, final int endIndexExclusive){
		return (T[]) ArrayUtils.subarray(array, startIndexInclusive, endIndexExclusive);
	}

	@Deprecated
	public static <T> List<T> inFirstNotSecond(final List<T> first, final List<T> second){
		first.removeAll(second);
		return first;
	}

	@Deprecated
	public static <T> List<T> inFirstAndSecond(final List<T> first, final List<T> second){
		first.removeIf((a) -> !second.contains(a));
		return first;
	}

}
