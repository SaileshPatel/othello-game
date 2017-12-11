package com.othellog4.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.othellog4.Othello;
/**
 * This is the main menu screen where 
 * 
 * @author 	Zakeria Hirsi
 * @since 	09/12/2017
 * @version 09/12/2017
 */
public class MainMenuScreen extends ScreenAdapter {
	private static final int PLAYBUTTON_WIDTH = 265;
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
	
	OrthographicCamera cam;
	Viewport viewport;
	
	public MainMenuScreen (Othello othello) {
		this.othello = othello;
		playButton = new Texture("NewGameActive.png");
		playButtonInactive = new Texture("NewGame.png");
		tutorialButton = new Texture("TutorialActive.png");
		tutorialButtonInactive = new Texture("Tutorial.png");
		exitButton = new Texture("ExitActive.png");
		exitButtonInactive = new Texture("Exit.png");
		OthelloText = new Texture ("Othello.png");
		background = new Texture ("backgroundNew.png");
		cam = new OrthographicCamera();
		cam.position.set(GAME_WORLD_WIDTH / 2, GAME_WORLD_HEIGHT / 2, 0);
		viewport = new FitViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, cam);
		viewport.apply();
	}
	
	public void render(float delta) {
		cam.update();
		viewport.apply();
		othello.getSpriteBatch().setProjectionMatrix(cam.combined);
		//shape.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		othello.getSpriteBatch().begin();
		othello.getSpriteBatch().draw(background, 0,0, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
		
		//System.out.println(Gdx.input.getX()+", " +  Gdx.input.getY());
		othello.getSpriteBatch().draw(OthelloText, 400, 100);
		Vector2 mousePos = getUnprojectedMousePos();
		if (mousePos.x < 850 + PLAYBUTTON_WIDTH && mousePos.x > 850 && mousePos.y > 320 && mousePos.y < 360) {
			othello.getSpriteBatch().draw(playButton, 850, 360);
			if(Gdx.input.isTouched()) {
				this.dispose();
				othello.switchToGame();
			}
			
		}else {
			othello.getSpriteBatch().draw(playButtonInactive, 850, 360);
		}
		
		if (Gdx.input.getX() < 850 + TUTORIALBUTTON_WIDTH && Gdx.input.getX() > 850 && Gdx.input.getY() > 380 && Gdx.input.getY() < 420) {
			othello.getSpriteBatch().draw(tutorialButton, 850, 300);
			if(Gdx.input.isTouched()) {
				this.dispose();
				othello.switchToTutorial();
			}
			
		}else {
			othello.getSpriteBatch().draw(tutorialButtonInactive, 850, 300);
		}

		
		if (Gdx.input.getX() < 850 + EXITBUTTOON_WIDTH && Gdx.input.getX() > 850 && Gdx.input.getY() > 448 && Gdx.input.getY() < 480) {
			othello.getSpriteBatch().draw(exitButton, 850, 240);
			if(Gdx.input.isTouched()) {
				Gdx.app.exit();
			}
			
		}else {
			othello.getSpriteBatch().draw(exitButtonInactive, 850, 240);
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
