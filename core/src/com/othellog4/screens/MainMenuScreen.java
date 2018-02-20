package com.othellog4.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.othellog4.Othello;

/**
 * This is the main menu screen where
 * 
 * @author Zakeria Hirsi
 * @since 09/12/2017
 * @version 09/12/2017
 */
public class MainMenuScreen extends ScreenAdapter {
	private static final int PLAYBUTTON_WIDTH = 300;
	private static final int PLAYBUTTON_HEIGHT = 45;
	private static final int TUTORIALBUTTON_WIDTH = 195;
	private static final int TUTORIALBUTTON_HEIGHT = 45;
	private static final int EXITBUTTOON_WIDTH = 105;
	private static final int EXITBUTTON_HEIGHT = 45;

	final int GAME_WORLD_WIDTH = 1600;
	final int GAME_WORLD_HEIGHT = 900;

	Othello othello;
	Texture playButton;
	Texture playButtonInactive;
	Texture tutorialButton;
	Texture tutorialButtonInactive;
	Texture exitButton;
	Texture exitButtonInactive;
	Texture background;
	Texture OthelloText;

	private BitmapFont titleFont;
	private BitmapFont optionsFont;

	OrthographicCamera cam;
	Viewport viewport;

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

		playButton = new Texture("NewGameActive.png");
		playButtonInactive = new Texture("NewGame.png");
		tutorialButton = new Texture("TutorialActive.png");
		tutorialButtonInactive = new Texture("Tutorial.png");
		exitButton = new Texture("ExitActive.png");
		exitButtonInactive = new Texture("Exit.png");
		OthelloText = new Texture("Othello.png");
		background = new Texture("backgroundNew.png");
		cam = new OrthographicCamera();
		cam.position.set(GAME_WORLD_WIDTH / 2, GAME_WORLD_HEIGHT / 2, 0);
		viewport = new FitViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, cam);
		viewport.apply();
	}

	public void render(float delta) {
		cam.update();
		viewport.apply();
		othello.getSpriteBatch().setProjectionMatrix(cam.combined);
		// shape.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		othello.getSpriteBatch().begin();
		othello.getSpriteBatch().draw(background, 0, 0, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);

		titleFont.setColor(1f, 1f, 1f, 1f);
		titleFont.draw(othello.getSpriteBatch(), "Othello", 365, 250, 1000, Align.left, true);

		// othello.getSpriteBatch().draw(OthelloText, 400, 100);
		Vector2 mousePos = getUnprojectedMousePos();
		if (mousePos.x > 850 && mousePos.x < 1180 && mousePos.y > 410 && mousePos.y < 460) {
			// othello.getSpriteBatch().draw(playButton, 850, 360);
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(othello.getSpriteBatch(), "New Game", 850, 460, 500, Align.left, true);
			
			if (Gdx.input.isTouched()) {
//				this.dispose();
//				othello.switchToGame();
			}

		} else {
			// othello.getSpriteBatch().draw(playButtonInactive, 850, 360);
			optionsFont.setColor(1f, 1f, 1f, 1f);
			optionsFont.draw(othello.getSpriteBatch(), "New Game", 850, 460, 500, Align.left, true);

		}

		if (mousePos.x > 850 && mousePos.x < 1082  && mousePos.y > 350 && mousePos.y < 400) {
			// othello.getSpriteBatch().draw(tutorialButton, 850, 300);
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(othello.getSpriteBatch(), "Tutorial", 850, 400, 500, Align.left, true);
			if (Gdx.input.isTouched()) {
				this.dispose();
				othello.switchToTutorial();
			}

		} else {
			// othello.getSpriteBatch().draw(tutorialButtonInactive, 850, 300);
			optionsFont.setColor(1f, 1f, 1f, 1f);
			optionsFont.draw(othello.getSpriteBatch(), "Tutorial", 850, 400, 500, Align.left, true);
		}
		
		if (mousePos.x > 850 && mousePos.x < 1080 && mousePos.y > 290 && mousePos.y < 340) {
			// othello.getSpriteBatch().draw(tutorialButton, 850, 300);
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(othello.getSpriteBatch(), "Options", 850, 340, 500, Align.left, true);
			if (Gdx.input.isTouched()) {
				this.dispose();
				othello.switchToOption();
			}

		} else {
			// othello.getSpriteBatch().draw(tutorialButtonInactive, 850, 300);
			optionsFont.setColor(1f, 1f, 1f, 1f);
			optionsFont.draw(othello.getSpriteBatch(), "Options", 850, 340, 500, Align.left, true);
		}
		
		
		
		
		
		

		if (mousePos.x >850 && mousePos.x < 960 && mousePos.y > 240 && mousePos.y < 280) {
			// othello.getSpriteBatch().draw(exitButton, 850, 240);
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(othello.getSpriteBatch(), "Exit", 850, 280, 500, Align.left, true);
			if (Gdx.input.isTouched()) {
				Gdx.app.exit();
			}

		} else {
			optionsFont.setColor(1f, 1f, 1f, 1f);
			optionsFont.draw(othello.getSpriteBatch(), "Exit", 850, 280, 500, Align.left, true);
		}

		othello.getSpriteBatch().end();

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		cam.position.set(GAME_WORLD_WIDTH / 2, GAME_WORLD_HEIGHT / 2, 0);
	}

	private Vector2 getUnprojectedMousePos() {
		Vector3 mouseActualPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		viewport.unproject(mouseActualPos);
		return new Vector2(mouseActualPos.x, mouseActualPos.y);
	}

}
