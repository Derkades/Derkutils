package xyz.derkades.derkutils;

public class StringFormatUtils {

	private static final String[] SIZE_SUFFIX = {
			"B",
			"KB",
			"MB",
			"GB",
			"TB",
			"PB",
	};

	private static final double[] SIZE_DIV = {
			1,
			1e3,
			1e6,
			1e9,
			1e12,
			1e15,
			1e18,
	};

	public static String formatByteCount(final long size) {
		for (int i = 0; i < SIZE_SUFFIX.length; i++) {
			if (size < SIZE_DIV[i+1]) {
				return String.format("%.2f%s", size / SIZE_DIV[i], SIZE_SUFFIX[i]);
			}
		}
		return "extremely large";
	}

}
