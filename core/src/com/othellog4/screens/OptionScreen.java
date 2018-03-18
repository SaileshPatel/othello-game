package com.othellog4.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.othellog4.Othello;
import com.othellog4.graphics.GraphicsUtil;

/**
 * Provides an option menu for users to adjust the background music volume and
 * whether sound effects should be on or off
 *
 * @author Zak Hirsi
 * @author BRUNO ZORIMA
 * @since 06/02/2018
 * @version 06/02/2018
 */
public class OptionScreen extends BaseScreen {

	Othello othello;


	private BitmapFont optionsFont;

	int buttonWidth = 100;
	int buttonHeight = 100;
	int xPosition = 0;
	int yPosition = Othello.GAME_WORLD_HEIGHT - buttonHeight;

	Texture background;
	Texture greyPiece;
	Texture whitePiece;
	Texture blackPiece;
	//Texture highlightedPiece;

	long soundValue;

	/**
	 * The constructor where everything is initialised
	 *
	 * @param othello
	 */
	public OptionScreen(Othello othello) {
		this.othello = othello;


		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/overpass-bold.otf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 50; // Size in px
		parameter.spaceY = -25; // Vertical spacing
		optionsFont = generator.generateFont(parameter);
		optionsFont.setUseIntegerPositions(false);
		generator.dispose();
		optionsFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		optionsFont.setColor(1f, 1f, 1f, 1f);

		background = GraphicsUtil.createMipMappedTex("improvedMenu2.png");
		background.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		greyPiece = GraphicsUtil.createMipMappedTex("emptypiece.png");
		whitePiece = GraphicsUtil.createMipMappedTex("whitepiece.png");
		blackPiece =  GraphicsUtil.createMipMappedTex("blackpiece.png");

		// Add disposable objects to cleanup list
		disposables.add(optionsFont);
		disposables.add(background);
		disposables.add(greyPiece);
		disposables.add(whitePiece);
		disposables.add(blackPiece);
		//disposables.add(highlightedPiece);
	}

	public void update(float delta) {
		updateBackButton(othello);
	}

	@Override
	public void render(float delta) {
		update(delta);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		SPRITE_BATCH.begin();
		SPRITE_BATCH.draw(background, 0, 0, Othello.GAME_WORLD_WIDTH, Othello.GAME_WORLD_HEIGHT);
		SPRITE_BATCH.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		SPRITE_BATCH.setColor(1.0f, 1.0f, 1.0f, 1.0f);

		Vector2 mousePos = GraphicsUtil.getMousePos();

		optionsFont.setColor(0f, 0f, 0f, 1f);
		optionsFont.draw(SPRITE_BATCH, "Music", 400, 350, 500, Align.left, true);
		optionsFont.draw(SPRITE_BATCH, "Sound Effects", 400, 250, 500, Align.left, true);
		optionsFont.setColor(1f, 1f, 1f, 1f);
		optionsFont.draw(SPRITE_BATCH, "Credits", 1420, 50, 500, Align.left, true);

		if (mousePos.x > 1420 && mousePos.x < 1580 && mousePos.y > 10
				&& mousePos.y <48) {
			if (Gdx.input.justTouched()) {
				BaseScreen.SHAPE_RENDER.begin(ShapeType.Filled);
				BaseScreen.SHAPE_RENDER.setColor(0f, 0f,0f, 1);
				BaseScreen.SHAPE_RENDER.rect(0, 0, 1600, 900);
				BaseScreen.SHAPE_RENDER.end();
				this.dispose();
				othello.switchCredits();
			}
		}
		// Slider for music

		int xPos = 800;
		int yPos = 310;
		int size = 40;

		for (int i = 0; i <= 4; i++) {
			SPRITE_BATCH.draw(greyPiece, xPos, yPos, size, size);

			float volume = (float) i / (float) 4;
			if (mousePos.x > xPos && mousePos.x < xPos + size && mousePos.y > yPos && mousePos.y < yPos + 80) {
				if (Gdx.input.justTouched()) {
					othello.setMusic(volume);

				}
			}

			xPos += size + 15;
			size += 10;
		}

		xPos = 800;
		yPos = 310;
		size = 40;
		for (double i = 0; i <= othello.getMusic(); i = i + 0.25) {
			SPRITE_BATCH.draw(blackPiece, xPos, yPos, size, size);
			xPos += size + 15;
			size += 10;

		}

		// Slider for sound

		xPos = 800;
		int yPosFX = 210;
		size = 40;

		for (int i = 0; i <= 4; i++) {
			SPRITE_BATCH.draw(greyPiece, xPos, yPosFX, size, size);

			float volume = (float) i / (float) 4;

			if (mousePos.x > xPos && mousePos.x < xPos + size && mousePos.y > yPosFX && mousePos.y < yPosFX + 80) {
				if (Gdx.input.justTouched()) {
					othello.setSound(volume);
				}
			}

			xPos += size + 15;
			size += 10;
		}

		xPos = 800;
		yPosFX = 210;
		size = 40;
		for (double i = 0; i <= othello.getCurrentVolume(); i = i + 0.25) {
			SPRITE_BATCH.draw(blackPiece, xPos, yPosFX, size, size);
			xPos += size + 15;
			size += 10;

		}

		renderBackButton();

		SPRITE_BATCH.end();
	}

}
