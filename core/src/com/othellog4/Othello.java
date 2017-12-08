package com.othellog4;

import java.util.Optional;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
import com.othellog4.screens.NormalGameScreen;

public class Othello extends Game {
	private SpriteBatch spriteBatch;
	//private GameBoard gameBoard;
	private Participant p1;
	private Participant p2;
	private GameModel model;
	private GameScreen screen;
	//private com.othellog4.game.Game game;
	//private BoardRenderer boardRenderer;
	
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
		setScreen(screen);
		//boardRenderer = new BoardRenderer(spriteBatch, model.getBoard());
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
	
//	@Override
//	public void dispose () {
//		batch.dispose();
//		img.dispose();
//	}
}
