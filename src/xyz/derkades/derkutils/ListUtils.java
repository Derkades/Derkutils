package xyz.derkades.derkutils;

import com.google.common.base.Preconditions;
import io.netty.util.internal.ThreadLocalRandom;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
	public static boolean stringListContainsString(final List<String> list, final String string, final boolean caseSensitive) {
		Objects.requireNonNull(list, "List is null");
		Objects.requireNonNull(string, "Contains string is null");
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
		Objects.requireNonNull(list, "List is null");
		Objects.requireNonNull(before, "Before array is null");
		Objects.requireNonNull(after, "After array is null");
		Preconditions.checkArgument(before.length == after.length, "before[] length %s must be equal to after[] length %s",
				before.length, after.length);

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
		list.addAll(Arrays.asList(items));

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
		Objects.requireNonNull(collections, "Collections collection is null");
		int size = 0;
		for (final Collection<T> col: collections) {
			Objects.requireNonNull(col, "A collection is null");
			size += col.size();
		}
		return size;
	}

	public static <T> int sizeSum(final T[][] arrays) {
		Objects.requireNonNull(arrays, "Arrays array is null");
		int size = 0;
		for (final T[] array : arrays) {
			Objects.requireNonNull(array, "An array is null");
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

		Preconditions.checkArgument(collection.size() == 1,
				"Size of collection must be 1 if it is not empty, but it is %s", collection.size());

		return Optional.of(collection.iterator().next());
	}

	public static <T> T choice(@NotNull final Set<T> set) {
		Preconditions.checkNotNull(set, "Set is null");
		Preconditions.checkArgument(set.size() > 0, "Set must contain at least one element");
	    return set.stream().skip(ThreadLocalRandom.current().nextInt(set.size())).findFirst().orElseThrow(llegalStateException::new);
	}

	public static <T> T choice(@NotNull final List<T> list) {
		return list.get(ThreadLocalRandom.current().nextInt(list.size()));
	}

	@NotNull
	public static <T> T choice(@NotNull final T[] array) {
		return array[ThreadLocalRandom.current().nextInt(array.length)];
	}

	public static <T> List<T> chooseMultiple(final T[] array, final int amount) {
		Preconditions.checkArgument(amount <= array.length, "Amount to pick from array must not exceed array size, but is %s", amount);
	    return Collections.unmodifiableList(
			IntStream
	            .generate(() -> ThreadLocalRandom.current().nextInt(array.length))
	            .distinct()
	            .limit(amount)
	            .mapToObj(i -> array[i])
	            .collect(Collectors.toList()));
	}

	public static <T> List<T> chooseMultiple(final List<T> list, final int amount) {
		Preconditions.checkArgument(amount <= list.size(), "Amount to pick from array must not exceed array size, but is %s", amount);
	    return Collections.unmodifiableList(
			IntStream
	            .generate(() -> ThreadLocalRandom.current().nextInt(list.size()))
	            .distinct()
	            .limit(amount)
	            .mapToObj(list::get)
	            .collect(Collectors.toList()));
	}

}
