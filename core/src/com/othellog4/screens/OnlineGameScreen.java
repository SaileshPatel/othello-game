package com.othellog4.screens;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.othello.network.MyRunnable;
import com.othellog4.Othello;
import com.othellog4.game.GameException;
import com.othellog4.game.GameModel;
import com.othellog4.game.board.Position;
import com.othellog4.graphics.BoardRenderer;

/**
 * The screen which displays a game of Othello.
 *
 * @author John Berg
 * @author James Shorthouse
 * @author Bruno Zorima
 * @author Sailesh Patel
 * @since 07/03/2018
 * @version 08/03/2018
 */
public abstract class OnlineGameScreen extends BaseScreen implements Observer {
	//=========================================================================
	//Fields.
	private boolean isPressed = false;
	protected Othello game;
	protected BoardRenderer boardRenderer;
	protected GameModel model;
	private boolean placementEnabled, waiting, test;
	private MyRunnable myRunnable;
	private Thread t;

	//=========================================================================
	//Constructors.
	/**
	 * The constructor for the game screen
	 * @param model the {@link com.othellog4.game.GameModel GameModel} used
	 * @param game the {@link com.othellog4.Othello Game} of Othello used
	 */
	public OnlineGameScreen(final GameModel model, Othello game, String IP) {

		if(IP.startsWith("host!:")) {
			waiting = true;
		} else {
			waiting = false;
		}
		test = false;
		myRunnable = new MyRunnable(IP);
		t = new Thread(myRunnable);
		this.model = model;
		this.model.addObserver(this);
		this.game = game;
		boardRenderer = new BoardRenderer(model);

	}
	//=========================================================================
	//Methods.
	/**
	 * This method is intended to check whether an input is a valid position or not
	 * @param position the position to check
	 * @return whether the position is valid or not (true/false)
	 */
	protected abstract boolean checkInput(final Position position);
	protected abstract void postRender(float delta);
	protected abstract void postUpdate(float delta);



	/**
	 * This method updates the screen whenever based on player inputs.
	 * @param delta
	 */
	public final void update(final float delta) {



		boardRenderer.update();
		if(waiting) {
			if(!t.isAlive()&& !test) {
				t.start();
				test = true;
				//Thread t = new Thread(networkMove());
				// place a position in a col/rol

			} else {
				Position position = myRunnable.messageIn;
				if(position != null && !t.isAlive()) {

					if(placementEnabled && checkInput(position)) {
						try
						{
							// place a position in a col/rol
							model.put(position.col, position.row);
							game.piecePlacedSound();
							myRunnable.messageIn = null;;
							waiting = false;
							test = false;
						}
						catch (GameException e)
						{
							//printMessage(e.toString());
						}
					}
				}
			}
		} else if(!isPressed)
		{
			// when a left click happens
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
			{
				isPressed = true;
				Position position = boardRenderer.getPosUnderMouse();
				if(position != null && !t.isAlive()) {
					if(placementEnabled && checkInput(position)) {
						try
						{
							// place a position in a col/rol
							model.put(position.col, position.row);
							game.piecePlacedSound();
							myRunnable.messageOut = position;
							waiting = true;
							t = new Thread(myRunnable);
						}
						catch (GameException e)
						{
							//printMessage(e.toString());
						}
					}
				}
			}
		}
		else if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT))
			isPressed = false;
		postUpdate(delta);

		updateBackButton(game);

	}


	@Override
	public final void dispose()
	{
		super.dispose();
		model.deleteObserver(this);
		myRunnable.close();
	}

	/**
	 * Enable or disable piece placement and ghosting under cursor
	 * @param enabled placement enabled
	 */
	protected void setPlacementEnabled(Boolean enabled) {
		placementEnabled = enabled;
		boardRenderer.setDrawHighlight(enabled);
	}

	//=========================================================================
	//Overidden methods.
	@Override
	public final void render(float delta) {
		update(delta);
		boardRenderer.render(delta);
		postRender(delta);
		SPRITE_BATCH.begin();
		renderBackButton();
		SPRITE_BATCH.end();
	}
	@Override
	public final void update(Observable o, Object arg) {

	}
}
