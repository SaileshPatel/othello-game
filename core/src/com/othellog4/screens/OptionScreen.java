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
import com.othellog4.environment.Launcher;
import com.othellog4.game.GameModel;
import com.othellog4.graphics.GraphicsUtil;

/**
 * Provides an option menu for users to adjust the background music volume and whether sound effects should be 
 * on or off
 * @author Zak Hirsi
 * @author BRUNO ZORIMA
 * @since 06/02/2018
 * @version 06/02/2018
 */
public class OptionScreen extends BaseScreen {

	Othello othello;

	private BitmapFont titleFont;
	private BitmapFont optionsFont;
	private boolean isOn;
	private boolean isOff;
	
	int buttonWidth = 100;
	int buttonHeight = 100;
	int xPosition = 0;
	int yPosition = Othello.GAME_WORLD_HEIGHT - buttonHeight;
	private Texture backButton;

	Texture background;
	Texture greyPiece;
	Texture whitePiece;
	Texture highlightedPiece;

	/**
	 * The constructor where everything is initialised
	 * 
	 * @param othello
	 */
	public OptionScreen(Othello othello) {
		this.othello = othello;

		isOn = true;
		isOff = false;
		
		backButton = GraphicsUtil.createMipMappedTex("backButton.png");

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

		background = new Texture("improvedMenu1.png");
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
		SPRITE_BATCH.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		SPRITE_BATCH.draw(backButton, xPosition, yPosition, buttonWidth, buttonHeight);
		SPRITE_BATCH.setColor(1.0f, 1.0f, 1.0f, 1.0f);

		Vector2 mousePos = GraphicsUtil.getMousePos();

		optionsFont.setColor(1f, 1f, 1f, 1f);
		optionsFont.draw(SPRITE_BATCH, "Sound Effects", 400, 300, 500, Align.left, true);
		optionsFont.draw(SPRITE_BATCH, "Music SFX", 400, 400, 500, Align.left, true);

		optionsFont.draw(SPRITE_BATCH, "On", 800, 300, 500, Align.left, true);
		optionsFont.draw(SPRITE_BATCH, "Off", 950, 300, 500, Align.left, true);
		


		// mouse hover settings for the sound effects
		if (mousePos.x > 800 && mousePos.x < 864 && mousePos.y < 300 && mousePos.y > 260) {
			if (Gdx.input.justTouched()) {
				isOn = true;
				isOff = false;
			}
		}
		if (mousePos.x > 950 && mousePos.x < 1025 && mousePos.y < 300 && mousePos.y > 260) {
			if (Gdx.input.justTouched()) {
				isOn = false;
				isOff = true;
			}
		}

		//mouse hover settings for the sound effects
				if (mousePos.x > 800 && mousePos.x < 864 && mousePos.y < 300 && mousePos.y > 260) {
					if (Gdx.input.justTouched()) {
						isOn=true;
						isOff=false;
					}
				}
				if (mousePos.x > 950 && mousePos.x < 1025 && mousePos.y < 300 && mousePos.y > 260) {
					if (Gdx.input.justTouched()) {		
						isOn=false;
						isOff=true;
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
				//back button logic to exit the screen
				if (mousePos.x > xPosition && mousePos.x < xPosition + buttonWidth && mousePos.y > yPosition && mousePos.y < yPosition + buttonHeight) {
					if(Gdx.input.justTouched()){
						this.dispose();
						othello.switchToMenu();
					}
				}

//				if (mousePos.x > 0 && mousePos.x < 110 && mousePos.y < 40 && mousePos.y > 0) {
//					optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
//					optionsFont.draw(SPRITE_BATCH, "Back", 0, 40, 500, Align.left, true);
//					if (Gdx.input.justTouched()) {
//						this.dispose();
//						othello.switchToMenu();
//					}
//				}
				

				
		//System.out.println(mousePos.x + " " + mousePos.y);

		int xPos = 800;
		int yPos = 360;
		int size = 40;

		for (int i = 0; i <= 4; i++) {
			SPRITE_BATCH.draw(greyPiece, xPos, yPos, size, size);
			float volume = (float) i / (float) 4;
			if (mousePos.x > xPos && mousePos.x < xPos + size && mousePos.y > yPos && mousePos.y < yPos + 80) {
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
//	 SPRITE_BATCH.begin();
//	 int i = 5;
//	 switch (i){
//		 case 0: SPRITE_BATCH.draw(whitePiece, xPos, yPos, size, size);
//		 case 1: SPRITE_BATCH.draw(whitePiece, xPos, yPos, size, size);
//		 case 2: SPRITE_BATCH.draw(whitePiece, xPos, yPos, size, size);
//		 case 3: SPRITE_BATCH.draw(whitePiece, xPos, yPos, size, size);
//		 case 4: SPRITE_BATCH.draw(whitePiece, xPos, yPos, size, size);
//		 break;
//	 }
//	 SPRITE_BATCH.end();
//	 }
//	}
}
