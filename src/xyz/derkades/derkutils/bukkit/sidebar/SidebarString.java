package xyz.derkades.derkutils.bukkit.sidebar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

public class SidebarString implements ConfigurationSerializable {

	/**
	 * Generates a scrolling animation for the text. For example, this:
	 * {@code generateScrollingAnimation("Hello", 10)} will generate a SidebarString
	 * with the following variations: "He", "el", "ll", "lo"
	 * 
	 * @param text         (String) - the text
	 * @param displayWidth (int) - how many letters to fit into one animation
	 * @return (SidebarString) - the generated SidebarString, ready for use in a
	 *         Sidebar.
	 */
	public static SidebarString generateScrollingAnimation(final String text, final int displayWidth) {
		if (text.length() <= displayWidth) {
			return new SidebarString(text);
		}

		final SidebarString sidebarString = new SidebarString();

		for (int i = 0; i <= text.length() - displayWidth; i++) {
			sidebarString.addVariation(text.substring(i, displayWidth + i));
		}

		return sidebarString;
	}

	static {
		ConfigurationSerialization.registerClass(SidebarString.class);
	}

	private List<String> animated = new ArrayList<>();
	private transient int i = 0, curStep;
	private int step = 1;

	/**
	 * Constructs a new SidebarString.
	 * 
	 * @param variations (String...) - the variations (for animated text)
	 */
	public SidebarString(final String... variations) {
		if (variations != null && variations.length > 0) {
			this.animated.addAll(Arrays.asList(variations));
		}

		this.curStep = this.step;
	}

	/**
	 * Constructs a new SidebarString.
	 * 
	 * @param step       (int) - see {@link #setStep(int)}
	 * @param variations (String...) - the variations (for animated text)
	 * @since 2.8
	 */
	public SidebarString(final int step, final String... variations) {
		if (step <= 0) {
			throw new IllegalArgumentException("step cannot be smaller than or equal to 0!");
		}

		this.step = step;

		if (variations != null && variations.length > 0) {
			this.animated.addAll(Arrays.asList(variations));
		}

		this.curStep = step;
	}

	@SuppressWarnings("unchecked")
	public SidebarString(final Map<String, Object> map) {
		this.animated = (List<String>) map.get("data");

		try {
			this.step = map.get("step") == null ? 0 : (Integer) map.get("step");
		} catch (ClassCastException | NullPointerException e) {
			this.step = 0;
		}
	}

	@Override
	public Map<String, Object> serialize() {
		final Map<String, Object> map = new HashMap<>();

		map.put("data", this.animated);
		map.put("step", this.step);

		return map;
	}

	/**
	 * Gets the text that comes after the last one, for animated text. This method
	 * only returns the next variant if the step permits it; which is always by
	 * default.
	 * 
	 * @return (String) - the next text.
	 */
	public String getNext() {
		if (this.curStep == this.step) {
			this.i++;
		}

		this.curStep++;

		if (this.curStep > this.step) {
			this.curStep = 0;
		}

		if (this.i > this.animated.size()) {
			this.i = 1;
		}

		return this.animated.get(this.i - 1);
	}

	/**
	 * Resets the animation to the starting point. Since 2.8, this also resets the
	 * current step value so the next call of {@link #getNext()} returns the next
	 * variation.
	 * 
	 * @return (SidebarString) - this SidebarString Object, for chaining.
	 */
	public SidebarString reset() {
		this.i = 0;
		this.curStep = this.step;
		return this;
	}

	/**
	 * Returns the step that is currently active.
	 * 
	 * @return (SidebarString) - this SidebarString Object, for chaining.
	 * @see #setStep(int)
	 * @since 2.8
	 */
	public int getStep() {
		return this.step;
	}

	/**
	 * Sets the step of this SidebarString. The "step" defines how many times the
	 * method {@link #getNext()} needs to be run before the actual new variant will
	 * be returned.
	 * 
	 * @param step (int) - the step, must be > 0
	 * @return (SidebarString) - this SidebarString Object, for chaining.
	 * @since 2.8
	 */
	public SidebarString setStep(final int step) {
		if (step <= 0) {
			throw new IllegalArgumentException("step cannot be smaller than or equal to 0!");
		}

		this.step = step;
		this.curStep = step;

		return this;
	}

	/**
	 * Gets all variations of this text.
	 * 
	 * @return (List: String) - all animations.
	 */
	public List<String> getVariations() {
		return this.animated;
	}

	/**
	 * Adds a variation.
	 * 
	 * @param variations (String...) - the variations to add
	 * @return (SidebarString) - this SidebarString Object, for chaining.
	 */
	public SidebarString addVariation(final String... variations) {
		this.animated.addAll(Arrays.asList(variations));
		return this;
	}

	/**
	 * Removes a variation.
	 * 
	 * @param variation (String) - the variation
	 * @return (SidebarString) - this SidebarString Object, for chaining.
	 */
	public SidebarString removeVariation(final String variation) {
		this.animated.remove(variation);
		return this;
	}

}
