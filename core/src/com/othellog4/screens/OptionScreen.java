package com.othellog4.screens;

import com.badlogic.gdx.Gdx;
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
import com.othellog4.graphics.GraphicsUtil;

public class OptionScreen extends ScreenAdapter {
	final int GAME_WORLD_WIDTH = 1600;
	final int GAME_WORLD_HEIGHT = 900;

	Othello othello;

	private BitmapFont titleFont;
	private BitmapFont optionsFont;
	private boolean isEasy;
	private boolean isMedium;
	private boolean isHard;
	private boolean isOn;
	private boolean isOff;

	OrthographicCamera cam;
	Viewport viewport;

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
		titleparameter.size = 160; // Size in px
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

		background = new Texture("newOptionsMenu2.png");
		background.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		greyPiece = GraphicsUtil.createMipMappedTex("emptypiece.png");
		whitePiece = GraphicsUtil.createMipMappedTex("whitepiece.png");

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

		Vector2 mousePos = getUnprojectedMousePos();

		//initialising the individual fonts
		optionsFont.setColor(1f, 1f, 1f, 1f);
	
		optionsFont.draw(othello.getSpriteBatch(), "Sound Effects", 400, 300, 500, Align.left, true);
		optionsFont.draw(othello.getSpriteBatch(), "Music SFX", 400, 400, 500, Align.left, true);
		optionsFont.draw(othello.getSpriteBatch(), "Difficulty", 400, 200, 500, Align.left, true);
		optionsFont.draw(othello.getSpriteBatch(), "Easy", 800, 200, 500, Align.left, true);
		optionsFont.draw(othello.getSpriteBatch(), "Medium", 950, 200, 500, Align.left, true);
		optionsFont.draw(othello.getSpriteBatch(), "Hard", 1185, 200, 500, Align.left, true);
		optionsFont.draw(othello.getSpriteBatch(), "Back", 0, 40, 500, Align.left, true);
		optionsFont.draw(othello.getSpriteBatch(), "On", 800, 300, 500, Align.left, true);
		optionsFont.draw(othello.getSpriteBatch(), "Off", 950, 300, 500, Align.left, true);

		
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
		
		
		//mouse hover settings for the difficults settings
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
			optionsFont.draw(othello.getSpriteBatch(), "Easy", 800, 200, 500, Align.left, true);
		}
		if (isMedium) {
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(othello.getSpriteBatch(), "Medium", 950, 200, 500, Align.left, true);
		}
		if (isHard) {
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(othello.getSpriteBatch(), "Hard", 1185, 200, 500, Align.left, true);
		}

		if (isOn) {
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(othello.getSpriteBatch(), "On", 800, 300, 500, Align.left, true);
		}
		if (isOff) {
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(othello.getSpriteBatch(), "Off", 950, 300, 500, Align.left, true);
		}

		if (mousePos.x > 0 && mousePos.x < 110 && mousePos.y < 40 && mousePos.y > 0) {
			optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
			optionsFont.draw(othello.getSpriteBatch(), "Back", 0, 40, 500, Align.left, true);
			if (Gdx.input.justTouched()) {
				this.dispose();
				othello.switchToMenu();
			}
		}

		int xPos = 800;
		int yPos = 360;
		int size = 40;

		for (int i = 0; i <= 4; i++) {
			othello.getSpriteBatch().draw(greyPiece, xPos, yPos, size, size);
			float volume = (float) i / (float) 4;
			if (mousePos.x > xPos && mousePos.x < xPos + size && mousePos.y > yPos && mousePos.y < yPos + 80) {
				othello.getSpriteBatch().draw(whitePiece, xPos, yPos, size, size);
				if (Gdx.input.justTouched()) {
					othello.setMusic(volume);
				}
			}

			xPos += size + 15;
			size += 10;
		}

		othello.getSpriteBatch().end();
	}

	// public void test(){
	// othello.getSpriteBatch().begin();
	// int i = 5;
	// switch (i){
	// case 0: othello.getSpriteBatch().draw(whitePiece, xPos, yPos, size, size);
	// case 1: othello.getSpriteBatch().draw(whitePiece, xPos, yPos, size, size);
	// case 2: othello.getSpriteBatch().draw(whitePiece, xPos, yPos, size, size);
	// case 3: othello.getSpriteBatch().draw(whitePiece, xPos, yPos, size, size);
	// case 4: othello.getSpriteBatch().draw(whitePiece, xPos, yPos, size, size);
	// break
	// }
	// othello.getSpriteBatch().end();
	// }

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
