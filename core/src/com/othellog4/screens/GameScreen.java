package com.othellog4.screens;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.othellog4.Othello;
import com.othellog4.environment.Launcher;
import com.othellog4.game.GameException;
import com.othellog4.game.GameModel;
import com.othellog4.game.board.Position;
import com.othellog4.graphics.BoardRenderer;
import com.othellog4.graphics.GraphicsUtil;

/**
 *
 * @author John Berg, James Shorthouse
 * @version 07/03/2018
 */
public abstract class GameScreen extends BaseScreen implements Observer {
	//=========================================================================
	//Fields.
	private boolean isPressed = false;
	protected Othello game;
	protected BoardRenderer boardRenderer;
	private GameModel model;

	int buttonWidth = 100;
	int buttonHeight = 100;
	int xPos = 0;
	int yPos = Othello.GAME_WORLD_HEIGHT - buttonHeight;
	private Texture mascotButton;
	private boolean placementEnabled;

	//=========================================================================
	//Constructors.
	public GameScreen(final GameModel model, Othello game) {
		this.model = model;
		this.model.addObserver(this);
		this.game = game;
		boardRenderer = new BoardRenderer(model);

		mascotButton = GraphicsUtil.createMipMappedTex("backButton.png");
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
					if(placementEnabled && checkInput(position))
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
		Vector2 mousePos  = GraphicsUtil.getMousePos();
		if (mousePos.x > xPos && mousePos.x < xPos + buttonWidth && mousePos.y > yPos && mousePos.y < yPos + buttonHeight) {
			if(Gdx.input.justTouched()){
				this.dispose();
				Launcher.get().cache(model);
				game.switchToMenu();
			}
		}
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
		SPRITE_BATCH.setColor(1.0f, 1.0f, 1.0f, 0.20f);
		SPRITE_BATCH.draw(mascotButton, xPos, yPos, buttonWidth, buttonHeight);
		SPRITE_BATCH.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		SPRITE_BATCH.end();
	}
	@Override
	public final void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
}
