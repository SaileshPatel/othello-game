package com.othellog4;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.othellog4.environment.Launcher;
import com.othellog4.environment.GameMode;
import com.othellog4.environment.PlayerType;
import com.othellog4.game.GameModel;
import com.othellog4.graphics.BoardRenderer;
import com.othellog4.screens.MainMenuScreen;
import com.othellog4.screens.NormalGameScreen;
import com.othellog4.screens.OptionScreen;
import com.othellog4.screens.TutorialScreen;

public class Othello extends Game {
	private SpriteBatch spriteBatch;
	public static final int GAME_WORLD_WIDTH = 1600;
	public static final int GAME_WORLD_HEIGHT = 900;
	public Music music;
	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		//Settings.load()
		//Assets.load()
		setScreen(new MainMenuScreen(this));
		//boardRenderer = new BoardRenderer(spriteBatch, model.getBoard());
		music = Gdx.audio.newMusic(Gdx.files.internal("OthelloMusic.mp3"));
		playMusic();
	}

	@Override
	public void render () {
		super.render();
	}
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	@Override
	public void resize(int width, int height) {
		getScreen().resize(width, height);
	}
	public void switchToGame() {
		final GameModel model = Launcher.get().newGame(
				PlayerType.USER,
				PlayerType.AI_MEDIUM,
				GameMode.DEBUGGING);
		setScreen(new NormalGameScreen(model, this));
		model.start();
	}
	/**
	 * Change from the current {@link Screen} to the {@link TutorialScreen}.
	 * 
	 * <p>
	 * Switching to the tutorial will launch a new game.
	 * </p>
	 */
	public void switchToTutorial() {
		setScreen(new TutorialScreen(Launcher.get().newGame(
						PlayerType.USER,
						PlayerType.USER,
						GameMode.BASIC),
				this));
	}
	
	public void switchToMenu() {
		setScreen(new MainMenuScreen(this));
	}
	
	public void switchToOption(){
		setScreen (new OptionScreen (this));
	}
	
	public void playMusic() {
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();	
		
	}
	
	public void setMusic(float volume) {
		music.setVolume(volume);
	
		//TO DO
		//change the decibel scale
	}
	
	public float getMusic() {
		return music.getVolume();
	}
	
//	@Override
//	public void dispose () {
//		batch.dispose();
//		img.dispose();
//	}
}
