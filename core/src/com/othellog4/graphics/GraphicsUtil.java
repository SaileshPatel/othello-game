package com.othellog4.graphics;

import java.time.LocalTime;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.othellog4.screens.BaseScreen;

/**
 * Utility class containing commonly used functions
 * @author  James Shorthouse
 * @version 10/03/2018
 *
 */
public final class GraphicsUtil {

	private GraphicsUtil() {
	}

	/**
	 * Create a texture with mipmaps and filtering
	 * @param  filePath relative path of texture
	 * @return          created texture
	 */
	public static Texture createMipMappedTex(String filePath) {
		Texture tex = new Texture(Gdx.files.internal(filePath), true);
		tex.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
		return tex;
	}

	/**
	 * Generate a smooth bitmap font from a .ttf file with a specified size and vertical spacing
	 * @param path        location of the font
	 * @param size        desired height of characters in pixels
	 * @param vertSpacing offset in pixels to add to the default vertical spacing
	 * @return            created bitmap font
	 */
	public static BitmapFont generateFont(String path, int size, int vertSpacing) {
		FreeTypeFontGenerator.setMaxTextureSize(2048); //TODO move this somewhere else
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(path));

		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size; // Size in px
		parameter.spaceY = vertSpacing;
		//parameter.genMipMaps = true;
		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();

		// Change options for smoother rendering
		font.setUseIntegerPositions(false);
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

		return font;
	}

	/**
	 * Generate a smooth bitmap font from a .ttf file with a specified size
	 * @param path        location of the font
	 * @param size        desired height of characters in pixels
	 * @return            created bitmap font
	 */
	public static BitmapFont generateFont(String path, int size) {
		return generateFont(path, size, 0);
	}

	/**
	 * Calculate a smoothed position value for an animated object given the animation progress
	 * @param time      the current time into the animation
	 * @param start     the start time of the animation
	 * @param duration  the duration of the animation
	 * @param steepness the steepness of the smoothing
	 * @return          smoothed position as a value between 0 and 1
	 */
	public static float smoothAnimationBetween(float time, float start, float duration, float steepness) {
		float percent = (time - start) / duration;
		// If animation hasn't started or has finished, return 1 or 0 respectively.
		if(percent < 0) return 0f;
		if(percent > 1) return 1f;

		// Apply smoothing function derived from https://math.stackexchange.com/a/121755
		float smoothed = (float) ((Math.pow(percent, steepness)) / (Math.pow(percent, steepness) +
				Math.pow(1-percent, steepness)));

		return smoothed;
	}

	/**
	 * Calculate a smoothed position value for an animated object given the animation progress
	 * @param time      the current time into the animation
	 * @param start     the start time of the animation
	 * @param duration  the duration of the animation
	 * @return          smoothed position as a value between 0 and 1
	 */
	public static float smoothAnimationBetween(float time, float start, float duration) {
		return smoothAnimationBetween(time, start, duration, 2); // Use 2 as default steepness
	}

	/**
	 * Get the relative mouse coordinates within the screen
	 * @return			mouse coordinates
	 */
	public static Vector2 getMousePos() {
		Vector2 mouseActualPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
		BaseScreen.VIEWPORT.unproject(mouseActualPos);
		return mouseActualPos;
	}

	/**
	 * Format a given number of seconds into hours, minutes, and seconds
	 * @param seconds	Number of seconds
	 * @return			Formatted string
	 */
	public static String formatTime(int seconds) {
		LocalTime localTime = LocalTime.ofSecondOfDay(seconds);

		String formattedTime = localTime.getMinute() + "m " +
				localTime.getSecond() + "s ";

		// Only show hours if time was > 1h
		if(localTime.getHour() > 0) {
			formattedTime = localTime.getHour() + "h " + formattedTime;
		}
		return formattedTime;
	}
}
