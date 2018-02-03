package com.othellog4.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * Utility class containing commonly used functions
 * @author  James Shorthouse
 * @version 03/02/2018
 *
 */
public class GraphicsUtil {
	
	/**
	 * Create a texture with mipmaps and filtering
	 * @param  filePath relative path of texture
	 * @return          created texture
	 */
	public static Texture createMipMappedTex(String filePath) {
		Texture tex = new Texture(Gdx.files.internal(filePath), true);
		tex.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Nearest);
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
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Overpass-Regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size; // Size in px
		parameter.spaceY = vertSpacing;
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
}
