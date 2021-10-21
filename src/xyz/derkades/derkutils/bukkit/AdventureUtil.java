package xyz.derkades.derkutils.bukkit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;
import xyz.derkades.derkutils.Colors;

import static net.kyori.adventure.text.Component.text;

public class AdventureUtil {

	/**
	 * Generate gradient
	 * @param string Input string
	 * @param gradientMargin 0.0-0.5, lower value means more visible gradient. With a higher value, more of the edges
	 *                       are "cropped off".
	 * @return Component where the text is the input string with gradient color
	 */
	public static @NotNull Component gradient(@NotNull String string, float gradientMargin) {
		if (gradientMargin < 0.0f || gradientMargin >= 0.5f) {
			throw new IllegalArgumentException("gradientMargin must be between 0 (inclusive) and 0.5 (exclusive)");
		}

		final int rgb1 = xyz.derkades.derkutils.Colors.randomPastelColor().getRGB();
		final int rgb2 = Colors.randomPastelColor().getRGB();
		final TextComponent.Builder b = text();
		final float f_step = (1.0f - 2*gradientMargin) / (string.length() - 1);
		float f = gradientMargin;
		for (final char c : string.toCharArray()) {
			final int r1 = (int) ((1-f) * (0xFF & rgb1));
			final int g1 = (int) ((1-f) * ((0xFF00 & rgb1) >> 8)) << 8;
			final int b1 = (int) ((1-f) * ((0xFF0000 & rgb1) >> 16)) << 16;
			final int r2 = (int) (f * (0xFF & rgb2));
			final int g2 = (int) (f * ((0xFF00 & rgb2) >> 8)) << 8;
			final int b2 = (int) (f * ((0xFF0000 & rgb2) >> 16)) << 16;
			b.append(text(c, TextColor.color(r1 + g1 + b1 + r2 + g2 + b2)));
			f += f_step;
			if (f > 1.0f) {
				f = 1.0f;
			}
		}

		return b.asComponent();
	}

}
