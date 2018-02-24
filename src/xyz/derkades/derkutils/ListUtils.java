package xyz.derkades.derkutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	public static List<String> getStringListFromResultSet(final ResultSet resultSet, final String column) throws SQLException {
		final List<String> list = new ArrayList<String>();
		
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
	public static <T> T getRandomValueFromArray(final T[] array){
		if (array == null) {
			throw new IllegalArgumentException("Array must not be null");
		}
		
		if (array.length == 1) {
			return array[0];
		}
		
		if (array.length == 0) {
			throw new IllegalArgumentException("Array must not be empty");
		}
		
		final int size = array.length;
		final int index = Random.getRandomInteger(0, size - 1);
		return array[index];
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * @param list
	 * @return A random element from the provided collection
	 */
	public static <T> T getRandomValueFromList(final Collection<? extends T> list) {
		if (list == null) {
			throw new IllegalArgumentException("List must not be null");
		}
		
		if (list.size() == 0) {
			throw new IllegalArgumentException("List must not be empty");
		}
		
		if (list.size() == 1) {
			return (T) list.toArray()[0];
		}
		
		final int size = list.size();
		final int index = Random.getRandomInteger(0, size - 1); //Size -1 because if the list has 1 entry (entry 0) the length is 1.
		return (T) list.toArray()[index];
	}
	
	/**
	 * Removes the first string from a string array
	 * @param array
	 * @return Array without the first string
	 */
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
		if (before.length != after.length) {
			throw new IllegalArgumentException("before[] length must be equal to after[] length");
		}
		
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

}
