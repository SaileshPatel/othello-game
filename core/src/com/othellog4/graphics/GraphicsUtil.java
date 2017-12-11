package com.othellog4.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

/**
 * Utility class containing commonly used functions
 * @author  James Shorthouse
 * @version 11/12/2017
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
}
