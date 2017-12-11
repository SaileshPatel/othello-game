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


public abstract class GameScreen extends ScreenAdapter implements Observer {
	//=========================================================================
	//Fields.
	private boolean isPressed = false;
	protected Othello game;
	protected SpriteBatch spriteBatch;
	protected BoardRenderer boardRenderer;
	private GameModel model;
	//=========================================================================
	//Constructors.
	public GameScreen(final GameModel model, Othello game) {
		this.model = model;
		this.model.addObserver(this);
		this.game = game;
		spriteBatch = game.getSpriteBatch();
		boardRenderer = new BoardRenderer(spriteBatch, model);
	}
	//=========================================================================
	//Methods.
	/**
	 * 
	 * @param message
	 */
	protected void printMessage(final String message)
	{
		//TODO implement
	}
	/**
	 * 
	 * @param position
	 * @return
	 */
	protected abstract boolean checkInput(final Position position);
	protected abstract void postRender(float delta);
	protected abstract void postUpdate(float delta);
	/**
	 * 
	 * @param width
	 * @param height
	 */
	public void resize(int width, int height)
	{
		boardRenderer.resize(width, height);
	}
	/**
	 * 
	 * @param delta
	 */
	public final void update(final float delta) {
		boardRenderer.update();
		if(!isPressed)
		{
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
			{
				isPressed = true;
				final Position position = boardRenderer.getPosUnderMouse();
				if(position != null)
					if(checkInput(position))
						try
						{
							model.put(position.col, position.row);
						}
						catch (GameException e)
						{
							printMessage(e.toString());
						}
			}
		}
		else if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT))
			isPressed = false;
		postUpdate(delta);
	}
	//=========================================================================
	//Overidden methods.
	@Override
	public final void render(float delta) {
		update(delta);
		boardRenderer.render(delta);
		postRender(delta);
	}
	@Override
	public final void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}
}
