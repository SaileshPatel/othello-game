package com.othellog4.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.othellog4.Othello;
import com.othellog4.graphics.BoardRenderer;

import com.othellog4.game.board.GameBoard;

public class GameScreen extends ScreenAdapter {
	Othello game;
	SpriteBatch spriteBatch;
	BoardRenderer boardRenderer;
	
	public GameScreen(Othello game, GameBoard board) {
		this.game = game;
		spriteBatch = game.getSpriteBatch();
		
		//Board created in main menu
		boardRenderer = new BoardRenderer(spriteBatch, board);
	}
	
	public void update() {
		
	}
	
	public void draw() {
		
	}
}
