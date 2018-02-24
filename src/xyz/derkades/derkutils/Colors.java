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

	public static Color getColorFromHex(String hex) {
		int[] rgb = hexToRGB(hex);
		return new Color(rgb[0], rgb[1], rgb[2]);
	}

}
