package com.othellog4.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.othellog4.Othello;
import com.othellog4.graphics.GraphicsUtil;

public class OptionScreen extends BaseScreen {

	Othello othello;

	private BitmapFont titleFont;
	private BitmapFont optionsFont;
	private boolean isEasy;
	private boolean isMedium;
	private boolean isHard;
	private boolean isOn;
	private boolean isOff;

	Texture background;
	Texture greyPiece;
	Texture whitePiece;
	Texture highlightedPiece;


	public OptionScreen(Othello othello) {
		this.othello = othello;

		isEasy = true;
		isMedium = false;
		isHard = false;

		isOn = true;
		isOff = false;

		FreeTypeFontGenerator titlegenerator = new FreeTypeFontGenerator(Gdx.files.internal("segoeuib.ttf"));
		FreeTypeFontParameter titleparameter = new FreeTypeFontParameter();
		titleparameter.size = 120; // Size in px
		titleparameter.spaceY = -25; // Vertical spacing
		titleFont = titlegenerator.generateFont(titleparameter);
		titleFont.setUseIntegerPositions(false);
		titlegenerator.dispose();
		titleFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		titleFont.setColor(1f, 1f, 1f, 1f);

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("segoeuib.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 50; // Size in px
		parameter.spaceY = -25; // Vertical spacing
		optionsFont = generator.generateFont(parameter);
		optionsFont.setUseIntegerPositions(false);
		generator.dispose();
		optionsFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		optionsFont.setColor(1f, 1f, 1f, 1f);

		background = new Texture("optionsMenu.png");
		background.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		greyPiece = GraphicsUtil.createMipMappedTex("emptypiece.png");
		whitePiece = GraphicsUtil.createMipMappedTex("whitepiece.png");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		SPRITE_BATCH.begin();
		SPRITE_BATCH.draw(background, 0, 0, Othello.GAME_WORLD_WIDTH, Othello.GAME_WORLD_HEIGHT);

		Vector2 mousePos = GraphicsUtil.getMousePos();

		optionsFont.setColor(1f, 1f, 1f, 1f);
		optionsFont.draw(SPRITE_BATCH, "Sound Effects", 400, 300, 500, Align.left, true);
		optionsFont.draw(SPRITE_BATCH, "Music SFX", 400, 400, 500, Align.left, true);
		optionsFont.draw(SPRITE_BATCH, "Difficulty", 400, 200, 500, Align.left, true);
		optionsFont.draw(SPRITE_BATCH, "Easy", 800, 200, 500, Align.left, true);
		optionsFont.draw(SPRITE_BATCH, "Medium", 950, 200, 500, Align.left, true);
		optionsFont.draw(SPRITE_BATCH, "Hard", 1185, 200, 500, Align.left, true);
		optionsFont.draw(SPRITE_BATCH, "Back", 0, 40, 500, Align.left, true);
		optionsFont.draw(SPRITE_BATCH, "On", 800, 300, 500, Align.left, true);
		optionsFont.draw(SPRITE_BATCH, "Off", 950, 300, 500, Align.left, true);


		if (mousePos.x > 400 && mousePos.x < 640 && mousePos.y < 400 && mousePos.y > 340) {
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(SPRITE_BATCH, "Music SFX", 400, 400, 500, Align.left, true);
			if (Gdx.input.justTouched()) {
				if (othello.getMusic() < 1.0f) {
					float newVolume = othello.getMusic() + 0.1f;
					othello.setMusic(newVolume);
				}
			}

		}

		if (mousePos.x > 400 && mousePos.x < 620 && mousePos.y < 200 && mousePos.y > 150) {
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(SPRITE_BATCH, "Difficulty", 400, 200, 500, Align.left, true);

			if (Gdx.input.justTouched() && (isEasy)) {
				isEasy = false;
				isMedium = true;
			} else if (Gdx.input.justTouched() && (isMedium)) {
				isMedium = false;
				isHard = true;
			} else if (Gdx.input.justTouched() && (isHard)) {
				isHard = false;
				isEasy = true;
			}

		}

		if (isEasy) {
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(SPRITE_BATCH, "Easy", 800, 200, 500, Align.left, true);
		}
		if (isMedium) {
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(SPRITE_BATCH, "Medium", 950, 200, 500, Align.left, true);
		}
		if (isHard) {
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(SPRITE_BATCH, "Hard", 1185, 200, 500, Align.left, true);
		}



		if (mousePos.x > 400 && mousePos.x < 620 && mousePos.y < 300 && mousePos.y > 250) {
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(SPRITE_BATCH, "Sound Effects", 400, 300, 500, Align.left, true);

			if (Gdx.input.justTouched() && (isOn)) {
				isOn = false;
				isOff = true;
			} else if (Gdx.input.justTouched() && (isOff)) {
				isOff = false;
				isOn = true;
			}

		}

		if (isOn) {
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(SPRITE_BATCH, "On", 800, 300, 500, Align.left, true);
		}
		if (isOff) {
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(SPRITE_BATCH, "Off", 950, 300, 500, Align.left, true);
		}


		if (mousePos.x > 0 && mousePos.x < 110 && mousePos.y < 40 && mousePos.y > 0) {
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(SPRITE_BATCH, "Back", 0, 40, 500, Align.left, true);
			if (Gdx.input.justTouched()) {
				this.dispose();
				othello.switchToMenu();
			}
		}
		//System.out.println(mousePos.x + " " + mousePos.y);

		int xPos = 800;
		int yPos = 360;
		int size = 40;

		for (int i = 0; i <=4 ; i++) {
			SPRITE_BATCH.draw(greyPiece, xPos, yPos, size, size);
			float volume = (float)i /(float) 4;
			if (mousePos.x > xPos && mousePos.x < xPos + size && mousePos.y > yPos && mousePos.y < yPos + 80 ) {
				SPRITE_BATCH.draw(whitePiece, xPos, yPos, size, size);
				if (Gdx.input.justTouched()) {
					othello.setMusic(volume);
				}
			}

			xPos += size + 15;
			size += 10;
		}


		SPRITE_BATCH.end();
	}

//	public void test(){
//		SPRITE_BATCH.begin();
//		int i = 5;
//		switch (i){
//			case 0: SPRITE_BATCH.draw(whitePiece, xPos, yPos, size, size);
//			case 1: SPRITE_BATCH.draw(whitePiece, xPos, yPos, size, size);
//			case 2: SPRITE_BATCH.draw(whitePiece, xPos, yPos, size, size);
//			case 3: SPRITE_BATCH.draw(whitePiece, xPos, yPos, size, size);
//			case 4: SPRITE_BATCH.draw(whitePiece, xPos, yPos, size, size);
//				break
//		}
//		SPRITE_BATCH.end();
//	}
}
