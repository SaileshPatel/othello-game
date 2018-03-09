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
import com.othellog4.screens.MultiplayerScreen;
import com.othellog4.screens.NormalGameScreen;
import com.othellog4.screens.OptionScreen;
import com.othellog4.screens.PlayerSelectScreen;
import com.othellog4.screens.TutorialScreen;
/**
 * This class deals with rendering the screen and elements from other classes. 
 * <br />
 * It runs the game, as well as deals with the backing track and switching screens.
 * 
 * @author John Berg
 * @author James Shorthouse
 * @author Zakeria Hirsi
 * @since 27/11/2017
 * @version 08/03/2018
 */
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
		setScreen(new TutorialScreen(Launcher.get().newGame(
						PlayerType.USER,
						PlayerType.USER,
						GameMode.BASIC),
				this));
	}
	/**
	 * Change to a {@link NormalGameScreen} object which will resume
	 * a {@link GameModel} which has been cached.
	 */
	public void continueGame()
	{
		setScreen(new NormalGameScreen(Launcher.get().release(), this));
	}
	/**
	 * Change to a {@link MainMenuScreen} object which will go to the main menu
	 */
	public void switchToMenu() {
		setScreen(new MainMenuScreen(this));
	}
	/**
	 * Change to a {@link OptionScreen} object which will go to the options screen
	 */
	public void switchToOption(){
		setScreen (new OptionScreen (this));
	}
	/**
	 * Change to a {@link MultiplayerScreen} object which will go to the multi-player screen
	 */
	public void switchToMultiplaer(){
		setScreen (new MultiplayerScreen(this));
	}

	/**
	 * This method plays the backing track. This method does the following:
	 * <ul>
	 * 	<li>Loops the music</li>
	 * 	<li>Sets the initial volume</li>
	 * 	<li>Plays the music</li>
	 * </ul>
	 */
	public void playMusic() {
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();

	}
	
	/**
	 * Sets the volume of the backing track music
	 * @param volume the value of the volume you want
	 */
	public void setMusic(float volume) {
		music.setVolume(volume);

		//TODO
		//change the decibel scale
	}

	/**
	 * Gets the volume of the backing track music
	 * @return the volume 
	 */
	public float getMusic() {
		return music.getVolume();
	}
}
