package xyz.derkades.derkutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListUtils {
	
	/**
	 * Returns true if one of the strings in the string list contains the string
	 * @param list
	 * @param string
	 * @return
	 */
	public static boolean stringListContainsString(List<String> list, String string){
		return stringListContainsString(list, string, false);
	}
	
	/**
	 * @param list
	 * @param string
	 * @param caseSensitive
	 * @return True if any string inside the list contains the specified string
	 */
	public static boolean stringListContainsString(List<String> list, String string, boolean caseSensitive){
		boolean contains = false;
		for (String entry : list){
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
	public static List<String> getStringListFromResultSet(ResultSet resultSet, String column) throws SQLException {
		List<String> list = new ArrayList<String>();
		
		while (resultSet.next()){
			list.add(resultSet.getString(column));
		}
		
		return list;
	}
	
	/**
	 * @param list
	 * @return A random element from a list
	 * @see #getRandomValueFromArray(Object[])
	 */
	public static <T> T getRandomValueFromList(List<T> list){
		int size = list.size();
		int index = Random.getRandomInteger(0, size - 1); //Size -1 because if the list has 1 entry (entry 0) the length is 1.
		return list.get(index);
	}
	
	/**
	 * @param array
	 * @return A random element from an array
	 * @see #getRandomValueFromList(List)
	 */
	public static <T> T getRandomValueFromArray(T[] array){
		if (array == null) {
			throw new IllegalArgumentException("Array must not be null");
		}
		
		if (array.length == 1) {
			return array[0];
		}
		
		if (array.length == 0) {
			throw new IllegalArgumentException("Array must not be empty");
		}
		
		int size = array.length;
		int index = Random.getRandomInteger(0, size - 1); //Size -1 because if the list has 1 entry (entry 0) the length is 1.
		return array[index];
	}
	
	/**
	 * Removes the first string from a string array
	 * @param array
	 * @return Array without the first string
	 */
	public static String[] removeFirstStringFromArray(String[] array){
		int n = array.length - 1;
		String[] newArray = new String[n];
		System.arraycopy(array, 1, newArray, 0, n);
		return newArray;
	}

}
