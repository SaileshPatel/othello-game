package com.othellog4;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.othellog4.environment.GameMode;
import com.othellog4.environment.Launcher;
import com.othellog4.environment.PlayerType;
import com.othellog4.game.GameModel;
import com.othellog4.screens.MainMenuScreen;
import com.othellog4.screens.NormalGameScreen;
import com.othellog4.screens.OptionScreen;
import com.othellog4.screens.PlayerSelectScreen;
import com.othellog4.screens.TutorialScreen;

public class Othello extends Game {
	public static final int GAME_WORLD_WIDTH = 1600;
	public static final int GAME_WORLD_HEIGHT = 900;
	public Music music;
	@Override
	public void create () {
		// Set blend function for alpha rendering
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		//Settings.load()
		//Assets.load()
		setScreen(new MainMenuScreen(this));
		music = Gdx.audio.newMusic(Gdx.files.internal("OthelloMusic.mp3"));
		playMusic();
	}

	@Override
	public void render () {
		super.render();
	}
	@Override
	public void resize(int width, int height) {
		getScreen().resize(width, height);
	}
	public final void runGame(final GameModel model)
	{
		setScreen(new NormalGameScreen(model, this));
		model.start();
	}
	public final void switchToPlayerSelect()
	{
		setScreen(new PlayerSelectScreen(this));
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
		final GameModel model = Launcher.get().newGame(
				PlayerType.USER,
				PlayerType.USER,
				GameMode.BASIC);
		setScreen(new TutorialScreen(model, this));
		model.start();
	}
	/**
	 * Change to a {@link NormalGameScreen} object which will resume
	 * a {@link GameModel} which has been cached.
	 */
	public void continueGame()
	{
		setScreen(new NormalGameScreen(Launcher.get().release(), this));
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

		//TODO
		//change the decibel scale
	}

	public float getMusic() {
		return music.getVolume();
	}
}
