package com.othellog4.screens;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.othellog4.Othello;
import com.othellog4.graphics.BoardRenderer;
import com.othellog4.game.GameModel;
import com.othellog4.game.GameSession;
import com.othellog4.game.board.GameBoard;
import com.othellog4.game.player.Participant;
import com.othellog4.game.player.Player;

public class GameScreen extends ScreenAdapter implements Observer {
	Othello game;
	SpriteBatch spriteBatch;
	BoardRenderer boardRenderer;
	private GameModel model;
	
	public GameScreen(final GameModel model) {
		
		this.model = model;
		this.model.addObserver(this);
		//Board created in main menu
		boardRenderer = new BoardRenderer(spriteBatch, model.getBoard());
	}
	
	public void updatedGameSession() {
		
	}
	
	public void update() {
		
	}
	
	public void draw() {
		
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}
}
