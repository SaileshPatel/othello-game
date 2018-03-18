package com.othellog4.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.othellog4.Othello;
import com.othellog4.environment.Launcher;
import com.othellog4.game.GameModel;
import com.othellog4.graphics.GraphicsUtil;

/**
 * A base screen which can be built upon later. All new screens should inherit
 * from here.
 *
 * @author James Shorthouse
 * @version 06/03/2017
 *
 */
public abstract class BaseScreen extends ScreenAdapter {
	public final static Camera CAM;
	public final static Viewport VIEWPORT;
	public final static SpriteBatch SPRITE_BATCH;
	public final static ShapeRenderer SHAPE_RENDER;

	protected boolean backButtonEnabled = true;

	int buttonWidth = 100;
	private float backXPos = 20;
	private float backYPos = Othello.GAME_WORLD_HEIGHT - (20 + buttonWidth);
	private Texture backButton;

	protected ArrayList<Disposable> disposables;

	/**
	 * All of the static values needed are initialised here.
	 */
	static {
		CAM = new OrthographicCamera();
		CAM.position.set((Othello.GAME_WORLD_WIDTH / 1) / 2, (Othello.GAME_WORLD_HEIGHT / 1) / 2, 0);
		VIEWPORT = new FitViewport(Othello.GAME_WORLD_WIDTH / 1, Othello.GAME_WORLD_HEIGHT / 1, CAM);
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
		disposables = new ArrayList<Disposable>();

		backButton = GraphicsUtil.createMipMappedTex("backButton.png");
		disposables.add(backButton);
	}

	/**
	 * Used to resize the screen. Updates the
	 * {@link com.badlogic.gdx.utils.viewport.Viewport Viewport} and the
	 * {@link com.badlogic.gdx.graphics.Camera Camera} position.
	 *
	 * @param width
	 *            the intended width of the screen
	 * @param height
	 *            the intended height of the screen
	 */
	@Override
	final public void resize(int width, int height) {
		VIEWPORT.update(width, height);
		CAM.position.set(Othello.GAME_WORLD_WIDTH / 2, Othello.GAME_WORLD_HEIGHT / 2, 0);
		updateMatricies();
		postResize(width, height);
	}

	/**
	 * Perform additional implementation specific resizing
	 *
	 * @param width
	 * @param height
	 */
	protected void postResize(int width, int height) {
		// Empty default implementation
	}

	/**
	 * Sets {@link com.badlogic.gdx.graphics.g2d.SpriteBatch.setProjectionMatrix
	 * Projection Matrix} of the
	 * {@link com.othellog4.screens.BaseScreen#SPRITE_BATCH Sprite Batch} and
	 * the {@link com.othellog4.screens.BaseScreen#SHAPE_RENDER Shape Renderer}
	 */
	private static void updateMatricies() {
		SPRITE_BATCH.setProjectionMatrix(CAM.combined);
		SHAPE_RENDER.setProjectionMatrix(CAM.combined);
	}

	/**
	 * Dispose of all non-static objects used by the screen
	 */
	@Override
	public void dispose() {
		for (Disposable obj : disposables) {
			obj.dispose();
		}
	}

	/**
	 * Creates the back button logic but this method is only relevant for the
	 * GameScreen as it needs a back button which stores the current game in a
	 * cache. Therefore this variation has a model in it constructor
	 * @param model
	 * @param game
	 */
	protected void renderBackButton() {
		//if(!backButtonEnabled) return;
		SPRITE_BATCH.draw(backButton, backXPos, backYPos, buttonWidth, buttonWidth);
	}

	protected void updateBackButton(Othello game) {
		if(!backButtonEnabled) return;
		if(backButtonClicked()) {
			this.dispose();
			game.switchToMenu();
		}
	}

	protected void updateBackButton(Othello game, GameModel model) {
		if(!backButtonEnabled) return;
		if(backButtonClicked()) {
			Launcher.get().cache(model);
			this.dispose();
			game.switchToMenu();
		}
	}

	private boolean backButtonClicked() {
		Vector2 mousePos = GraphicsUtil.getMousePos();
		return ((mousePos.x > backXPos && mousePos.x < backXPos + buttonWidth && mousePos.y > backYPos
				&& mousePos.y < backYPos + buttonWidth) && Gdx.input.justTouched())
				|| Gdx.input.isKeyPressed(Input.Keys.ESCAPE);
	}

	/**
	 * Dispose of all static objects used by the screen
	 */
	public static void cleanupStaticObjects() {
		SPRITE_BATCH.dispose();
		SHAPE_RENDER.dispose();
	}
}
