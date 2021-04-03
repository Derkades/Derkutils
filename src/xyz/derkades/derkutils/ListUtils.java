package xyz.derkades.derkutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import io.netty.util.internal.ThreadLocalRandom;

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

	public static <T> int sizeSum(final Collection<Collection<T>> collections) {
		int size = 0;
		for (final Collection<T> col: collections) {
			size += col.size();
		}
		return size;
	}

	public static <T> int sizeSum(final T[][] arrays) {
		int size = 0;
		for (final T[] array : arrays) {
			size += array.length;
		}
		return size;
	}

	@SuppressWarnings("unchecked")
	@Deprecated
	public static <T> T[] mergeArrays(final T[]... arrays) {
		final List<T> list = new ArrayList<>();
		for (final T[] array : arrays) {
			list.addAll(Arrays.asList(array));
		}
		return (T[]) list.toArray();
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] combineArrays(final T[]... arrays) {
		final List<T> list = new ArrayList<>(sizeSum(arrays));
		for (final T[] array : arrays) {
			list.addAll(Arrays.asList(array));
		}
		return (T[]) list.toArray();
	}

	/**
	 * Convert an empty collection to empty optional and a collection with one item to an optional of that item.
	 * @param <T>
	 * @param collection
	 * @throws IllegalArgumentException collection is not empty and size is not == 1
	 * @return
	 */
	public static <T> Optional<T> toOptional(final Collection<T> collection) {
		if (collection.isEmpty()) {
			return Optional.empty();
		}

		Validate.isTrue(collection.size() == 1);
		return Optional.of(collection.iterator().next());
	}

	public static <T> T choice(final Set<T> set) {
		Validate.notEmpty(set, "Set is null or contains no elements");
	    return set.stream().skip(ThreadLocalRandom.current().nextInt(set.size())).findFirst().orElse(null);
	}

	public static <T> T choice(final List<T> list) {
		return list.get(ThreadLocalRandom.current().nextInt(list.size()));
	}

	public static <T> T choice(final T[] array) {
		return array[ThreadLocalRandom.current().nextInt(array.length)];
	}

}
