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
<<<<<<< HEAD
 * Provides an option menu for users to adjust the background music volume and whether sound effects should be 
 * on or off
 * @author Zak Hirsi
 * @author BRUNO ZORIMA
=======
 * Provides an option menu for users to adjust the background music volume and
 * whether sound effects should be on or off
 * 
 * @author Zakeria Hirsi
>>>>>>> 2a0b9c6f7506dfec4805a3c18d41f337ff48a81b
 * @since 06/02/2018
 * @version 06/02/2018
 */
public class OptionScreen extends BaseScreen {

	Othello othello;

	private BitmapFont titleFont;
	private BitmapFont optionsFont;
<<<<<<< HEAD
=======
	private boolean isEasy;
	private boolean isMedium;
	private boolean isHard;
	private boolean isOn;
	private boolean isOff;
	
	int buttonWidth = 100;
	int buttonHeight = 100;
	int xPosition = 0;
	int yPosition = Othello.GAME_WORLD_HEIGHT - buttonHeight;
	private Texture backButton;
>>>>>>> c03eeb79bf040b7a6c0991ea75d1518e5f1a0e6b

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

		isEasy = true;
		isMedium = false;
		isHard = false;

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
		// optionsFont.draw(SPRITE_BATCH, "Sound Effects", 400, 200, 500,
		// Align.left, true);
		optionsFont.draw(SPRITE_BATCH, "Music", 400, 400, 500, Align.left, true);
		optionsFont.draw(SPRITE_BATCH, "Sound Effects", 400, 300, 500, Align.left, true);
<<<<<<< HEAD
		optionsFont.draw(SPRITE_BATCH, "Back", 0, 40, 500, Align.left, true);
		// optionsFont.draw(SPRITE_BATCH, "On", 800, 200, 500, Align.left,
		// true);
		// optionsFont.draw(SPRITE_BATCH, "Off", 950, 200, 500, Align.left,
		// true);

		// mouse hover settings for the sound effects
		// if (mousePos.x > 800 && mousePos.x < 864 && mousePos.y < 200 &&
		// mousePos.y > 160) {
		// if (Gdx.input.justTouched()) {
		// isOn = true;
		// isOff = false;
		// }
		// }
		// if (mousePos.x > 950 && mousePos.x < 1025 && mousePos.y < 200 &&
		// mousePos.y > 160) {
		// if (Gdx.input.justTouched()) {
		// isOn = false;
		// isOff = true;
		// }
		// }
		//
		//
		//
		// if (isOn) {
		// optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
		// optionsFont.draw(SPRITE_BATCH, "On", 800, 200, 500, Align.left,
		// true);
		// }
		// if (isOff) {
		// optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
		// optionsFont.draw(SPRITE_BATCH, "Off", 950, 200, 500, Align.left,
		// true);
		// }
=======
		optionsFont.draw(SPRITE_BATCH, "Music SFX", 400, 400, 500, Align.left, true);
<<<<<<< HEAD
		optionsFont.draw(SPRITE_BATCH, "Difficulty", 400, 200, 500, Align.left, true);
		optionsFont.draw(SPRITE_BATCH, "Easy", 800, 200, 500, Align.left, true);
		optionsFont.draw(SPRITE_BATCH, "Medium", 950, 200, 500, Align.left, true);
		optionsFont.draw(SPRITE_BATCH, "Hard", 1185, 200, 500, Align.left, true);
=======
//		optionsFont.draw(SPRITE_BATCH, "Difficulty", 400, 200, 500, Align.left, true);
//		optionsFont.draw(SPRITE_BATCH, "Easy", 800, 200, 500, Align.left, true);
//		optionsFont.draw(SPRITE_BATCH, "Medium", 950, 200, 500, Align.left, true);
//		optionsFont.draw(SPRITE_BATCH, "Hard", 1185, 200, 500, Align.left, true);
		optionsFont.draw(SPRITE_BATCH, "Back", 0, 40, 500, Align.left, true);
>>>>>>> 2a0b9c6f7506dfec4805a3c18d41f337ff48a81b
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

<<<<<<< HEAD
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
				
				
				//mouse hover settings for the difficult settings
				if (mousePos.x > 800 && mousePos.x < 904 && mousePos.y < 200 && mousePos.y > 150) {
					if (Gdx.input.justTouched()) {
						System.out.println("Pressed1");
						isEasy=true;
						isMedium=false;
						isHard=false;
					}
				}
				if (mousePos.x > 950 && mousePos.x < 1143 && mousePos.y < 200 && mousePos.y > 150) {
					if (Gdx.input.justTouched()) {
						System.out.println("Pressed2");
						isEasy=false;
						isMedium=true;
						isHard=false;
					}
				}
				if (mousePos.x > 1187 && mousePos.x < 1300 && mousePos.y < 200 && mousePos.y > 150) {
					if (Gdx.input.justTouched()) {
						System.out.println("Pressed3");
						isEasy=false;
						isMedium=false;
						isHard=true;
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
=======
		// mouse hover settings for the difficults settings
//		if (mousePos.x > 800 && mousePos.x < 904 && mousePos.y < 200 && mousePos.y > 150) {
//			if (Gdx.input.justTouched()) {
//				isEasy = true;
//				isMedium = false;
//				isHard = false;
//			}
//		}
//		if (mousePos.x > 950 && mousePos.x < 1143 && mousePos.y < 200 && mousePos.y > 150) {
//			if (Gdx.input.justTouched()) {
//				isEasy = false;
//				isMedium = true;
//				isHard = false;
//			}
//		}
//		if (mousePos.x > 1187 && mousePos.x < 1300 && mousePos.y < 200 && mousePos.y > 150) {
//			if (Gdx.input.justTouched()) {
//				isEasy = false;
//				isMedium = false;
//				isHard = true;
//			}
//		}
//
//		if (isEasy) {
//			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
//			optionsFont.draw(SPRITE_BATCH, "Easy", 800, 200, 500, Align.left, true);
//		}
//		if (isMedium) {
//			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
//			optionsFont.draw(SPRITE_BATCH, "Medium", 950, 200, 500, Align.left, true);
//		}
//		if (isHard) {
//			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
//			optionsFont.draw(SPRITE_BATCH, "Hard", 1185, 200, 500, Align.left, true);
//		}

		if (isOn) {
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(SPRITE_BATCH, "On", 800, 300, 500, Align.left, true);
		}
		if (isOff) {
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(SPRITE_BATCH, "Off", 950, 300, 500, Align.left, true);
		}
>>>>>>> c03eeb79bf040b7a6c0991ea75d1518e5f1a0e6b

		if (mousePos.x > 0 && mousePos.x < 110 && mousePos.y < 40 && mousePos.y > 0) {
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(SPRITE_BATCH, "Back", 0, 40, 500, Align.left, true);
			if (Gdx.input.justTouched()) {
				this.dispose();
				othello.switchToMenu();
			}
		}
>>>>>>> 2a0b9c6f7506dfec4805a3c18d41f337ff48a81b

		// Slider for music

		int xPos = 800;
		int yPos = 360;
		int yPosFX = 260;
		int size = 40;

		for (int i = 0; i <= 4; i++) {
			SPRITE_BATCH.draw(greyPiece, xPos, yPos, size, size);
			SPRITE_BATCH.draw(greyPiece, xPos, yPosFX, size, size);

			float volume = (float) i / (float) 4;
			if (mousePos.x > xPos && mousePos.x < xPos + size && mousePos.y > yPos && mousePos.y < yPos + 80) {
				if (Gdx.input.justTouched()) {
					othello.setMusic(volume);

				}
			}

			if (mousePos.x > xPos && mousePos.x < xPos + size && mousePos.y > yPosFX && mousePos.y < yPosFX + 80) {
				if (Gdx.input.justTouched()) {
					othello.setSound(volume);
				}
			}

			xPos += size + 15;
			size += 10;
		}

		xPos = 800;
		yPos = 360;
		size = 40;
		for (double i = 0; i <= othello.getMusic(); i = i + 0.25) {
			SPRITE_BATCH.draw(whitePiece, xPos, yPos, size, size);
			xPos += size + 15;
			size += 10;

		}

		SPRITE_BATCH.end();
	}

}
