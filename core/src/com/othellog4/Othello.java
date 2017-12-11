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
import com.othellog4.game.player.AIStrategy;
import com.othellog4.game.player.AutomaticPlayer;
import com.othellog4.game.player.Participant;
import com.othellog4.game.player.Player;
import com.othellog4.game.player.Participant.Control;
import com.othellog4.graphics.BoardRenderer;
import com.othellog4.screens.GameScreen;
import com.othellog4.screens.MainMenuScreen;
import com.othellog4.screens.NormalGameScreen;
import com.othellog4.screens.TutorialScreen;

public class Othello extends Game {
	private SpriteBatch spriteBatch;
	//private GameBoard gameBoard;
	private Participant p1;
	private Participant p2;
	private GameModel model;
	private GameScreen screen;
	private TutorialScreen tutorialScreen;

	private MainMenuScreen menuScreen;
	//private com.othellog4.game.Game game;
	//private BoardRenderer boardRenderer;
	public static final int GAME_WORLD_WIDTH = 1600;
	public static final int GAME_WORLD_HEIGHT = 900;
	
	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		//Settings.load()
		//Assets.load()
		//gameBoard = new GameBoard(8);
		//game = new com.othellog4.game.Game(new GameBoard(8));
		p1 = new Player();
		p2 = new AutomaticPlayer(AIStrategy.RANDOM_SELECTION);
		
		model = new GameModel(
				new com.othellog4.game.Game(new GameBoard(8)),
				p1,
				p2);
		
		screen = new NormalGameScreen(model, this);
		tutorialScreen = new TutorialScreen(model, this);

		menuScreen = new MainMenuScreen(this);
		setScreen(menuScreen);
		
		
		//boardRenderer = new BoardRenderer(spriteBatch, model.getBoard());
		
		Music music = Gdx.audio.newMusic(Gdx.files.internal("OthelloMusic.mp3"));
		music.setLooping(true);
		music.setVolume(0.2f);
		music.play();
		
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
		screen.resize(width, height);
	}
	public void switchToGame() {
		setScreen(screen);
	}
	
	public void switchToTutorial() {
		setScreen(tutorialScreen);
	}
	
//	@Override
//	public void dispose () {
//		batch.dispose();
//		img.dispose();
//	}
}
