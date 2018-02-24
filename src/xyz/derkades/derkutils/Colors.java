package xyz.derkades.derkutils;

import java.awt.Color;

public class Colors {
	
	public static int[] hexToRGB(String hex){
		hex = hex.replace("#", "");
		
		if (hex.length() != 6){
			throw new IllegalArgumentException("Hex length must be 6");
		}
		
		return new int[]{
				Integer.valueOf(hex.substring(1, 3), 16),
	            Integer.valueOf(hex.substring(3, 5), 16),
	            Integer.valueOf(hex.substring(5, 7), 16)
		};
	}

	public static Color getColorFromHex(final String hex) {
		final int[] rgb = hexToRGB(hex);
		return new Color(rgb[0], rgb[1], rgb[2]);
	}
	
	public static Color randomColor() {
		final float r = Random.getRandomFloat();
		final float g = Random.getRandomFloat();
		final float b = Random.getRandomFloat();
		return new Color(r, g, b);
	}
	
	public static Color randomPastelColor() {
		final float hue = Random.getRandomFloat();
		// Saturation between 0.1 and 0.3
		final float saturation = (Random.getRandomInteger(0, 2000) + 1000) / 10000f;
		final float luminance = 0.9f;
		return Color.getHSBColor(hue, saturation, luminance);
	}

}
