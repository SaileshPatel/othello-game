package com.othellog4;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.othellog4.game.GameSession;
import com.othellog4.game.board.GameBoard;
import com.othellog4.game.player.Player;
import com.othellog4.graphics.BoardRenderer;
import com.othellog4.screens.GameScreen;

public class Othello extends Game {
	private SpriteBatch spriteBatch;
	private GameBoard gameBoard;
	private Player p1;
	private Player p2;
	private GameSession session;
	private com.othellog4.game.Game game;
	private BoardRenderer boardRenderer;
	
	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		//Settings.load()
		//Assets.load()
		//setScreen(new GameScreen(this));
		gameBoard = new GameBoard(8);
	
		game = new com.othellog4.game.Game(gameBoard);
		
		p1 = new Player();
		p2 = new Player();
		
		session = new GameSession(game, p1, p2);
		
		boardRenderer = new BoardRenderer(spriteBatch, session);
	}

	@Override
	public void render () {
		super.render();
		boardRenderer.render();
		
	
		//Gdx.gl.glClearColor(1, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//spriteBatch.begin();
		//spriteBatch.draw(img, 0, 0);
		//spriteBatch.end();
	}
	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	@Override
	public void resize(int width, int height) {
		boardRenderer.resize(width, height);
	}
	
//	@Override
//	public void dispose () {
//		batch.dispose();
//		img.dispose();
//	}
}
