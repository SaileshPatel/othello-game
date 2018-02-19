package com.othellog4;

import java.util.Optional;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.othellog4.game.GameModel;
import com.othellog4.game.GameSession;
import com.othellog4.game.board.GameBoard;
import com.othellog4.game.player.AutomaticPlayer;
import com.othellog4.game.player.Participant;
import com.othellog4.game.player.Player;
import com.othellog4.game.player.Participant.Control;
import com.othellog4.game.player.ai.DelayStrategies;
import com.othellog4.game.player.ai.SearchStrategies;
import com.othellog4.graphics.BoardRenderer;
import com.othellog4.screens.GameScreen;
import com.othellog4.screens.MainMenuScreen;
import com.othellog4.screens.NormalGameScreen;
import com.othellog4.screens.OptionScreen;
import com.othellog4.screens.TutorialScreen;

public class Othello extends Game {
	private SpriteBatch spriteBatch;
	//private GameBoard gameBoard;
	private Participant p1;
	private Participant p2;
	private GameModel model;
	private GameScreen normalScreen;
	private TutorialScreen tutorialScreen;

	private MainMenuScreen menuScreen;
	private OptionScreen optionScreen;
	//private com.othellog4.game.Game game;
	//private BoardRenderer boardRenderer;
	public static final int GAME_WORLD_WIDTH = 1600;
	public static final int GAME_WORLD_HEIGHT = 900;
	public Music music;
	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		//Settings.load()
		//Assets.load()
		//gameBoard = new GameBoard(8);
		//game = new com.othellog4.game.Game(new GameBoard(8));
		p1 = new AutomaticPlayer(
				EvaluationStrategies.WINNER,
				SearchStrategies.BEST_IMMIDIATE_OUTCOME,
				DelayStrategies.WAIT_ONE_SEC);
		//p1 = new Player();
		p2 = new AutomaticPlayer(
				EvaluationStrategies.COUNT,
				SearchStrategies.BEST_IMMIDIATE_OUTCOME,
				DelayStrategies.WAIT_ONE_SEC);
		
		model = new GameModel(
				new com.othellog4.game.Game(new GameBoard(8 , 0)),
				p1,
				p2);
		
		normalScreen = new NormalGameScreen(model, this);
		tutorialScreen = new TutorialScreen(model, this);
		optionScreen = new OptionScreen(this);
		menuScreen = new MainMenuScreen(this);
		setScreen(menuScreen);
		
		
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
		//boardRenderer.resize(width, height);
		getScreen().resize(width, height);
	}
	public void switchToGame() {
		final GameModel model = new GameModel(
				new com.othellog4.game.Game(new GameBoard(8 , 0)),
				p1,
				p2);
		setScreen(new NormalGameScreen(model, this));
		model.start();
		
	}
	
	public void switchToTutorial() {
		setScreen(new TutorialScreen(new GameModel(
				new com.othellog4.game.Game(new GameBoard(8 , 0)),
				p1,
				p2), this));
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
