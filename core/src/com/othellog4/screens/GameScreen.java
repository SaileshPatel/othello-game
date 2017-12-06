package com.othellog4.screens;

import java.lang.reflect.GenericArrayType;
import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.othellog4.Othello;
import com.othellog4.graphics.BoardRenderer;
import com.othellog4.game.GameException;
import com.othellog4.game.GameModel;
import com.othellog4.game.board.Position;

public class GameScreen extends ScreenAdapter implements Observer {
	Othello game;
	SpriteBatch spriteBatch;
	BoardRenderer boardRenderer;
	private GameModel model;
	
	public GameScreen(final GameModel model) {
		
		this.model = model;
		this.model.addObserver(this);
		//Board created in main menu
		spriteBatch = new SpriteBatch();
		boardRenderer = new BoardRenderer(spriteBatch, model.getBoard());
		boardRenderer.resize(700, 700);
		new Thread(() -> {
			for(;;)
				update();
		}).start();
	}
	
	public void updatedGameSession() {
		
	}
	public final void resize(int width, int height)
	{
		boardRenderer.resize(width, height);
	}
	public void update() {
		boardRenderer.update();
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
		{
			final Position position = boardRenderer.getPosUnderMouse();
			if(position != null)
				try
				{
					model.put(position.col, position.row);
				}
				catch (GameException e)
				{
					e.printStackTrace();
				}
		}
		//System.out.println(boardRenderer.getPosUnderMouse());
		
	}
	
	public void draw() {
		boardRenderer.render();
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}
}
