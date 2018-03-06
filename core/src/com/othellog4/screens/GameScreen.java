package com.othellog4.screens;

import java.lang.reflect.GenericArrayType;
import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.othellog4.Othello;
import com.othellog4.graphics.BoardRenderer;
import com.othellog4.graphics.GraphicsUtil;
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
	
	int buttonWidth = 100;
	int buttonHeight = 100;
	int xPos = 0;
	int yPos = Othello.GAME_WORLD_HEIGHT - buttonHeight;
	Viewport viewport;
	OrthographicCamera cam;
	private Texture mascotButton;
	
	//=========================================================================
	//Constructors.
	public GameScreen(final GameModel model, Othello game) {
		this.model = model;
		this.model.addObserver(this);
		this.game = game;
		spriteBatch = game.getSpriteBatch();
		boardRenderer = new BoardRenderer(spriteBatch, model);
		
		mascotButton = GraphicsUtil.createMipMappedTex("backButton.png");
		cam = new OrthographicCamera();
		cam.position.set(Othello.GAME_WORLD_WIDTH / 2, Othello.GAME_WORLD_HEIGHT / 2, 0);
		viewport = new FitViewport(Othello.GAME_WORLD_WIDTH, Othello.GAME_WORLD_HEIGHT, cam);
		viewport.apply();
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
		viewport.update(width, height);
		cam.position.set(Othello.GAME_WORLD_WIDTH / 2, Othello.GAME_WORLD_HEIGHT / 2, 0);
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
		Vector2 mousePos  = GraphicsUtil.getUnprojectedMousePos(viewport);
		if (mousePos.x > xPos && mousePos.x < xPos + buttonWidth && mousePos.y > yPos && mousePos.y < yPos + buttonHeight) {
			if(Gdx.input.justTouched()){
				this.dispose();
				game.switchToMenu();
			}
		}
	}
	//=========================================================================
	//Overidden methods.
	@Override
	public final void render(float delta) {
		update(delta);
		boardRenderer.render(delta);
		postRender(delta);
		spriteBatch.setProjectionMatrix(cam.combined);
		viewport.apply();
		spriteBatch.begin();
		spriteBatch.setColor(1.0f, 1.0f, 1.0f, 0.20f);
		spriteBatch.draw(mascotButton, xPos, yPos, buttonWidth, buttonHeight);
		spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		spriteBatch.end();	
	}
	@Override
	public final void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
}
