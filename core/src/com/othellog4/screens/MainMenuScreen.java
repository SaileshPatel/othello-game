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
import com.othellog4.graphics.GraphicsUtil;
import com.othellog4.graphics.ScreenBoxField;

/**
 * This is the main menu screen where the player can decide on whether they want to :
 * <ul>
 * 	<li>Play online (takes user to {@link MultiplayerScreen Multiplayer Screen})</li>
 * 	<li>Play locally (takes user to {@link PlayerSelectScreen Selection Screen})</li>
 * 	<li>Use a tutorial (takes user to {@link TutorialScreen Tutorial Screen})</li>
 * 	<li>Access the options screen (takes user to {@link OptionScreen Options Screen})</li>
 * 	<li>Exit the game</li>
 * </ul>
 * 
 * @author Zakeria Hirsi
 * @since 09/12/2017
 * @version 09/08/2017
 */
public class MainMenuScreen extends BaseScreen {


	final int GAME_WORLD_WIDTH = 1600;
	final int GAME_WORLD_HEIGHT = 900;

	Othello othello;
	Texture background;
	Texture OthelloText;

	private BitmapFont titleFont;
	private BitmapFont optionsFont;

	/**
	 * The constructor where everything is initially initialised
	 * @param othello an instance of {@link Othello} to be passed
	 */
	public MainMenuScreen(Othello othello) {
		this.othello = othello;
		FreeTypeFontGenerator titlegenerator = new FreeTypeFontGenerator(Gdx.files.internal("segoeuib.ttf"));
		FreeTypeFontParameter titleparameter = new FreeTypeFontParameter();
		titleparameter.size = 170; // Size in px
		titleparameter.spaceY = -25; // Vertical spacing
		titleFont = titlegenerator.generateFont(titleparameter);
		titleFont.setUseIntegerPositions(false);
		titlegenerator.dispose();
		titleFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		titleFont.setColor(0f, 0f, 0f, 1f);
		titleFont.setColor(0f, 0f, 0f, 1f);

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("segoeuib.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 64; // Size in px
		parameter.spaceY = -25; // Vertical spacing
		optionsFont = generator.generateFont(parameter);
		optionsFont.setUseIntegerPositions(false);
		generator.dispose();
		optionsFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		optionsFont.setColor(1f, 1f, 1f, 1f);

		OthelloText = new Texture("Othello.png");
		background = new Texture("backgroundNew.png");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		SPRITE_BATCH.begin();
		SPRITE_BATCH.draw(background, 0, 0, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);

		titleFont.setColor(1f, 1f, 1f, 1f);
		titleFont.draw(SPRITE_BATCH, "Othello", 365, 250, 1000, Align.left, true);

		// SPRITE_BATCH.draw(OthelloText, 400, 100);
		Vector2 mousePos = GraphicsUtil.getMousePos();
		if(Launcher.get().hasCache())
			new ScreenBoxField(850, 530, 1180, 50)
			.onHover(box ->
			{
				setColourHover();
				if(Gdx.input.isTouched())
					othello.continueGame();
			})
			.noHover(box -> setColourNoHover())
			.after(box -> drawTextInBox("Continue", box))
			.hover(mousePos.x, mousePos.y);
		
		if (mousePos.x > 850 && mousePos.x < 1180 && mousePos.y > 470 && mousePos.y < 520) {
			// SPRITE_BATCH.draw(playButton, 850, 360);
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(SPRITE_BATCH, "New Game", 850, 520, 500, Align.left, true);
			
			if (Gdx.input.isTouched()) {
				this.dispose();
				othello.switchToPlayerSelect();
			}

		} else {
			// SPRITE_BATCH.draw(playButtonInactive, 850, 360);
			optionsFont.setColor(1f, 1f, 1f, 1f);
			optionsFont.draw(SPRITE_BATCH, "New Game", 850, 520, 500, Align.left, true);

		}
		
		if (mousePos.x > 850 && mousePos.x < 1180 && mousePos.y > 410 && mousePos.y < 460) {
			// SPRITE_BATCH.draw(playButton, 850, 360);
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(SPRITE_BATCH, "Multiplayer", 850, 460, 500, Align.left, true);
			
			if (Gdx.input.isTouched()) {
				this.dispose();
				othello.switchToMultiplaer();
			}

		} else {
			// SPRITE_BATCH.draw(playButtonInactive, 850, 360);
			optionsFont.setColor(1f, 1f, 1f, 1f);
			optionsFont.draw(SPRITE_BATCH, "Multiplayer", 850, 460, 500, Align.left, true);

		}

		if (mousePos.x > 850 && mousePos.x < 1082  && mousePos.y > 345 && mousePos.y < 395) {
			// SPRITE_BATCH.draw(tutorialButton, 850, 300);
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(SPRITE_BATCH, "Tutorial", 850, 395, 500, Align.left, true);
			if (Gdx.input.isTouched()) {
				this.dispose();
				othello.switchToTutorial();
			}

		} else {
			// SPRITE_BATCH.draw(tutorialButtonInactive, 850, 300);
			optionsFont.setColor(1f, 1f, 1f, 1f);
			optionsFont.draw(SPRITE_BATCH, "Tutorial", 850, 395, 500, Align.left, true);
		}
		
		if (mousePos.x > 850 && mousePos.x < 1080 && mousePos.y > 290 && mousePos.y < 340) {
			// SPRITE_BATCH.draw(tutorialButton, 850, 300);
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(SPRITE_BATCH, "Options", 850, 340, 500, Align.left, true);
			if (Gdx.input.isTouched()) {
				this.dispose();
				othello.switchToOption();
			}

		} else {
			// SPRITE_BATCH.draw(tutorialButtonInactive, 850, 300);
			optionsFont.setColor(1f, 1f, 1f, 1f);
			optionsFont.draw(SPRITE_BATCH, "Options", 850, 340, 500, Align.left, true);
		}
		
		new ScreenBoxField(850, 240, 110, 40)
		.onHover(box ->
		{
			setColourHover();
			if(Gdx.input.isTouched())
				Gdx.app.exit();
		})
		.noHover(box -> setColourNoHover())
		.after(box -> drawTextInBox("Exit", box))
		.hover(mousePos.x, mousePos.y);
		SPRITE_BATCH.end();

	}
	/**
	 * Set the colour to the colour which is used to draw objects which are
	 * being hovered over.
	 */
	private void setColourHover()
	{
		optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
	}
	/**
	 * Set the colour to the colour which is used to draw objects which are
	 * not hovered over.
	 */
	private void setColourNoHover()
	{
		optionsFont.setColor(1f, 1f, 1f, 1f);
	}
	/**
	 * Draw a {@link String} into at a location which is defined by a
	 * {@link ScreenBoxField} object.
	 * 
	 * @param text The {@link String} to be drawn.
	 * @param box The {@link ScreenBoxField} which contains the details of
	 * 			where to draw the <code>text</code>.
	 */
	private void drawTextInBox(
			final String text,
			final ScreenBoxField box)
	{
		optionsFont.draw(
				SPRITE_BATCH,
				text,
				box.getX(),
				box.getY() + box.getHeight(),
				box.getWidth(),
				Align.left,
				true);
	}
}
