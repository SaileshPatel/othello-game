package com.othellog4.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
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

	int buttonWidth = 100;
	int buttonHeight = 100;
	float xPos = 0;
	float yPos = Othello.GAME_WORLD_HEIGHT - buttonHeight;
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
			System.out.println("Disposing of " + obj.getClass());
			obj.dispose();
		}
		System.out.println();
	}

	/**
	 * Creates the back button logic but this method is only relevant for the
	 * GameScreen as it needs a back button which stores the current game in a
	 * cache. Therefore this variation has a model in it constructor
	 * @param model
	 * @param game
	 */
	public void backButton(GameModel model, Othello game) {
		backButton = GraphicsUtil.createMipMappedTex("backButton.png");
		
		Vector2 mousePos = GraphicsUtil.getMousePos();
		if (mousePos.x > xPos && mousePos.x < xPos + buttonWidth && mousePos.y > yPos
				&& mousePos.y < yPos + buttonHeight) {
			if (Gdx.input.justTouched()) {
				this.dispose();
				Launcher.get().cache(model);
				game.switchToMenu();
			}
		}
	}

	/**
	 * Creates back button logic for every other screen than the game screen
	 */
	public void backButton(Othello game) {
		backButton = GraphicsUtil.createMipMappedTex("backButton.png");
		Vector2 mousePos = GraphicsUtil.getMousePos();
		if (mousePos.x > xPos && mousePos.x < xPos + buttonWidth && mousePos.y > yPos
				&& mousePos.y < yPos + buttonHeight) {
			if (Gdx.input.justTouched()) {
				this.dispose();
				game.switchToMenu();
			}
		}
	}

	/**
	 * Dispose of all static objects used by the screen
	 */
	public static void cleanupStaticObjects() {
		SPRITE_BATCH.dispose();
		SHAPE_RENDER.dispose();
	}
}
