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
 *
 * @author James Shorthouse
 * @version 06/03/2017
 *
 */
public class BaseScreen extends ScreenAdapter {
	public final static Camera CAM;
	public final static Viewport VIEWPORT;
	public final static SpriteBatch SPRITE_BATCH;
	public final static ShapeRenderer SHAPE_RENDER;


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

	public BaseScreen() {
		super();
	}

	final public void resize(int width, int height) {
		VIEWPORT.update(width, height);
		CAM.position.set(Othello.GAME_WORLD_WIDTH / 2,
				Othello.GAME_WORLD_HEIGHT / 2, 0);
		updateMatricies();
		postResize(width, height);
	}

	protected void postResize(int width, int height) {
		// Empty default implementation
	}

	private static void updateMatricies() {
		SPRITE_BATCH.setProjectionMatrix(CAM.combined);
		SHAPE_RENDER.setProjectionMatrix(CAM.combined);
	}

}
