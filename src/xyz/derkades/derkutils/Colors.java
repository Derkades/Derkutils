package xyz.derkades.derkutils;

import com.google.common.base.Preconditions;

import java.awt.Color;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Colors {

	public static int[] hexToRGB(String hex){
		Objects.requireNonNull(hex, "Hex is null");
		if (hex.startsWith("#")) {
			hex = hex.substring(1);
		}
		Preconditions.checkArgument(hex.length() == 6,
				"Length of hex string must be 6 (it is %s)", hex.length());

		return new int[]{
				Integer.valueOf(hex.substring(1, 3), 16),
	            Integer.valueOf(hex.substring(3, 5), 16),
	            Integer.valueOf(hex.substring(5, 7), 16)
		};
	}

	public static Color hexToColor(String hex){
		Objects.requireNonNull(hex, "Hex is null");
		if (hex.startsWith("#")) {
			hex = hex.substring(1);
		}
		Preconditions.checkArgument(hex.length() == 6,
				"Length of hex string must be 6 (it is %s)", hex.length());

		return new Color(
				Integer.valueOf(hex.substring(1, 3), 16),
	            Integer.valueOf(hex.substring(3, 5), 16),
	            Integer.valueOf(hex.substring(5, 7), 16)
		);
	}

	public static Color getColorFromHex(final String hex) {
		final int[] rgb = hexToRGB(hex);
		return new Color(rgb[0], rgb[1], rgb[2]);
	}

	public static Color randomColor() {
		final float r = ThreadLocalRandom.current().nextFloat();
		final float g = ThreadLocalRandom.current().nextFloat();
		final float b = ThreadLocalRandom.current().nextFloat();
		return new Color(r, g, b);
	}

	public static Color randomPastelColor() {
		final float hue = ThreadLocalRandom.current().nextFloat();
		// Saturation between 0.2 and 0.4
		final float saturation = (ThreadLocalRandom.current().nextInt(0, 3000) + 2000) / 10_000f;
		final float luminance = 0.9f;
		return Color.getHSBColor(hue, saturation, luminance);
	}

}
