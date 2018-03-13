package com.othellog4.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.othellog4.Othello;

/**
 * A base screen which can be built upon later. All new screens should inherit from here. 
 * @author James Shorthouse
 * @version 06/03/2017
 *
 */
public class BaseScreen extends ScreenAdapter {
	public final static Camera CAM;
	public final static Viewport VIEWPORT;
	public final static SpriteBatch SPRITE_BATCH;
	public final static ShapeRenderer SHAPE_RENDER;

	/**
	 * All of the static values needed are initialised here.
	 */
	static {
		CAM = new OrthographicCamera();
		CAM.position.set((Othello.GAME_WORLD_WIDTH/1) / 2,
				(Othello.GAME_WORLD_HEIGHT/1) / 2, 0);
		VIEWPORT = new FitViewport(Othello.GAME_WORLD_WIDTH/1,
				Othello.GAME_WORLD_HEIGHT/1, CAM);
		SPRITE_BATCH = new SpriteBatch();
		SHAPE_RENDER = new ShapeRenderer();
		SPRITE_BATCH.setProjectionMatrix(CAM.combined);
		SHAPE_RENDER.setProjectionMatrix(CAM.combined);
		updateMatricies();
	}

	/**
	 * Calls the {@link com.badlogic.gdx.ScreenAdapter super-class} constructor
	 */
	public BaseScreen() {
		super();
	}

	/**
	 * Used to resize the screen. Updates the {@link com.badlogic.gdx.utils.viewport.Viewport Viewport} and
	 * the {@link com.badlogic.gdx.graphics.Camera Camera} position.
	 * 
	 * @param width the intended width of the screen
	 * @param height the intended height of the screen
	 */
	final public void resize(int width, int height) {
		VIEWPORT.update(width, height);
		CAM.position.set(Othello.GAME_WORLD_WIDTH / 2,
				Othello.GAME_WORLD_HEIGHT / 2, 0);
		updateMatricies();
		postResize(width, height);
	}

	/**
	 * Currently an empty default implementation required. 
	 * @param width
	 * @param height
	 */
	protected void postResize(int width, int height) {
		// Empty default implementation
	}

	/**
	 * Sets {@link com.badlogic.gdx.graphics.g2d.SpriteBatch.setProjectionMatrix Projection Matrix} of
	 * the {@link com.othellog4.screens.BaseScreen#SPRITE_BATCH Sprite Batch} and the 
	 * {@link com.othellog4.screens.BaseScreen#SHAPE_RENDER Shape Renderer}
	 */
	private static void updateMatricies() {
		SPRITE_BATCH.setProjectionMatrix(CAM.combined);
		SHAPE_RENDER.setProjectionMatrix(CAM.combined);
	}

}
