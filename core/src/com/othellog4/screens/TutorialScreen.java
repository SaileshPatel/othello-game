package com.othellog4.screens;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.othellog4.Othello;
import com.othellog4.game.GameModel;
import com.othellog4.game.board.Position;
import com.othellog4.graphics.GraphicsUtil;
import com.othellog4.tutorial.AssistedMoveState;
import com.othellog4.tutorial.DialogueState;
import com.othellog4.tutorial.HighlightableState;
import com.othellog4.tutorial.MascotExpression;
import com.othellog4.tutorial.PieceHighlightState;
import com.othellog4.tutorial.TutorialSequence;
import com.othellog4.tutorial.TutorialState;

/**
 * Screen for running tutorials.
 * <p>
 * Renders, updates, and handles input for the current {@code TutorialState} given by a {@code TutorialSequence} object.
 * <p>
 * Once one state is completed, the next will be advanced to. After the list of states has been completed, the user is
 * returned to the main menu.
 * <p>
 * @author James Shorthouse
 * @version 11/12/2017
 */
public class TutorialScreen extends GameScreen {
	
	private Texture mascotDefault, mascotShocked, enterKey, dialogueBox;
	private final int DIALOGUE_PADDING;
	private final int MASCOT_HEIGHT;
	private final int ENTER_HEIGHT;
	private final int ENTER_PADDING;
	private Vector2 mascotPos;
	private Vector2 dialoguePos;
	private Vector2 fontPos;
	private Vector2 enterPos;
	private float dialogueWidth;
	private float enterWidth;
	private BitmapFont dialogueFont;
	private int textWidth;
	
	private TutorialSequence sequence;
	private TutorialState currentState;
	private boolean placementEnabled;
	private float timer;
	private boolean enterPreviouslyPressed;
	
	Color gradientTop;
	Color gradientBottom;
	
	ShapeRenderer shape;
	
	Camera cam;
	Viewport viewport;

	/**
	 * Constructor
	 * 
	 * @param model game to be rendered
	 * @param game	main class for use when switching scenes
	 */
	public TutorialScreen(GameModel model, Othello game) {
		super(model, game);
		
		DIALOGUE_PADDING = 50;
		MASCOT_HEIGHT = 400;
		ENTER_HEIGHT = 75;
		ENTER_PADDING = 50;
		
		shape = new ShapeRenderer();
		
		setPlacementEnabled(false);
		mascotDefault = GraphicsUtil.createMipMappedTex("mascot/default.png");
		mascotShocked = GraphicsUtil.createMipMappedTex("mascot/shocked.png");
		enterKey = GraphicsUtil.createMipMappedTex("key_icons/enter.png");
		dialogueBox = GraphicsUtil.createMipMappedTex("gui/dialogue_box.png");
		
		cam = new OrthographicCamera();
		cam.position.set(Othello.GAME_WORLD_WIDTH / 2, Othello.GAME_WORLD_HEIGHT / 2, 0);
		viewport = new FitViewport(Othello.GAME_WORLD_WIDTH, Othello.GAME_WORLD_HEIGHT, cam);
		viewport.apply();
		shape.setProjectionMatrix(cam.combined);
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Overpass-Regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 64; // Size in px
		parameter.spaceY = -25; // Vertical spacing
		dialogueFont = generator.generateFont(parameter);
		dialogueFont.setUseIntegerPositions(false);
		generator.dispose();
		
		dialogueFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		dialogueFont.getData().markupEnabled = true; // Enable use of [#000000] tags to set colour
		dialogueFont.setColor(0f, 0f, 0f, 1f);
		
		gradientTop = new Color(0.0f, 0.0f, 0.0f, 0.8f);
		gradientBottom = new Color(0.0f, 0.0f, 0.0f, 0.5f);
		
		timer = 0f;
		
		// Calculate rendering coordinates / dimensions
		dialoguePos = new Vector2(DIALOGUE_PADDING, DIALOGUE_PADDING);
		dialogueWidth = Othello.GAME_WORLD_WIDTH - (3 * DIALOGUE_PADDING) - MASCOT_HEIGHT;
		mascotPos = new Vector2(Othello.GAME_WORLD_WIDTH - DIALOGUE_PADDING - MASCOT_HEIGHT ,DIALOGUE_PADDING);
		enterWidth = ((float) ENTER_HEIGHT / (float) enterKey.getHeight()) * enterKey.getWidth();
		enterPos = new Vector2(DIALOGUE_PADDING + dialogueWidth - ENTER_PADDING - enterWidth, DIALOGUE_PADDING + ENTER_PADDING);
		fontPos = new Vector2(DIALOGUE_PADDING*2, MASCOT_HEIGHT);
		textWidth = (int) (dialogueWidth - (2 * DIALOGUE_PADDING));
		
		// Tutorial array
		DialogueState tutIntro = new DialogueState("Hey there, my name is [#ff0000]O-Fellow[#000000], let me give you some tips on how to play!", MascotExpression.DEFAULT);
		DialogueState tutExplain1 = new DialogueState("The aim of the game is have as many of your coloured pieces on the board as possible.", MascotExpression.DEFAULT);
		DialogueState tutExplain2a = new DialogueState("Each turn you get to place a piece. Any of your opponents pieces between the piece you place... ", MascotExpression.DEFAULT);
		DialogueState tutExplain2b = new DialogueState("...and the pieces you already have down will be flipped to your colour!", MascotExpression.DEFAULT);

		
		DialogueState tutExplain3 = new DialogueState("The board starts with a 2x2 layout of pieces in the center, let's take a look...", MascotExpression.DEFAULT);
		PieceHighlightState tutShowBoard = new PieceHighlightState(null);
		DialogueState tutExplain4 = new DialogueState("You can only place a piece down if it will flip some of your opponent's pieces.", MascotExpression.DEFAULT);
		DialogueState tutExplain5 = new DialogueState("You are playing as black, so let's see the possibilities for your first move...", MascotExpression.DEFAULT);
		PieceHighlightState tutShowMoves = new PieceHighlightState(new ArrayList<Position>(Arrays.asList(Position.at(4,2), Position.at(5, 3), Position.at(2, 4), Position.at(3, 5))));
		DialogueState tutExplain6 = new DialogueState("Now, time to make your move!", MascotExpression.DEFAULT);
		DialogueState tutExplain7 = new DialogueState("You can place pieces by clicking on the board.", MascotExpression.DEFAULT);
		DialogueState tutExplain8 = new DialogueState("Lets try placing a piece in that top position...", MascotExpression.DEFAULT);
		AssistedMoveState tutFirstMove = new AssistedMoveState((new ArrayList<Position>(Arrays.asList(Position.at(4,2)))), true, new DialogueState("W-what are you doing? That's not the right place!", MascotExpression.SHOCKED));
		// tutShowBoard
		DialogueState tutExplain9 = new DialogueState("Good job, I think you're getting the hang of this!", MascotExpression.DEFAULT);
		DialogueState tutExplain10 = new DialogueState("Now that you understand the basics, you should go and try a real game!", MascotExpression.DEFAULT);
		DialogueState tutExplain11 = new DialogueState("There's no more rules for me to teach you, but there's plenty of strategy to master!", MascotExpression.DEFAULT);
		DialogueState tutExplain12 = new DialogueState("Goodbye for now!", MascotExpression.DEFAULT);
		
		sequence = new TutorialSequence(new ArrayList<TutorialState>(Arrays.asList(tutIntro, tutExplain1, tutExplain2a, 
				tutExplain2b, tutExplain3, tutShowBoard, tutExplain4, tutExplain5, tutShowMoves, tutExplain6, tutExplain7, 
				tutExplain8, tutFirstMove, tutShowBoard, tutExplain9, tutExplain10, tutExplain11, tutExplain12)));
		
		stateInit();
	}
	
	/**
	 * {@inheritDoc}
	 * Draw screen elements corresponding to the current state.
	 * <p>
	 * <ul>
	 * <li> {@code DialogueState} - mascot, dialogue box, key icon, and text
	 * <li> {@code AssistedMoveState} - if message is set to active: mascot, dialogue box, key icon, and text
	 * <li> {@code PieceHighlightState} - key icon in bottom right
	 */
	@Override
	protected void postRender(float delta) {
		timer += delta;
		TutorialState stateToDraw = currentState;
		
		if(stateToDraw instanceof AssistedMoveState && ((AssistedMoveState) currentState).isMessageActive()) {
			stateToDraw = ((AssistedMoveState) currentState).getIncorrectMessage();
		}
		
		// Draw gradient, mascot, dialogue box + text, enter button
		if(stateToDraw instanceof DialogueState) {
			DialogueState dialogueState = (DialogueState) stateToDraw;
			// Get mascot texture
			Texture ofellowTex = null;
			switch(dialogueState.getExpression()){
				case DEFAULT:
					ofellowTex = mascotDefault;
					break;
				case SHOCKED:
					ofellowTex = mascotShocked;
					break;
			}
			// Draw gradient
			// TODO move these methods
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			shape.begin(ShapeType.Filled);
		    shape.rect(0, 0, Othello.GAME_WORLD_WIDTH, Othello.GAME_WORLD_HEIGHT, gradientTop, gradientTop,
		    		gradientBottom, gradientBottom);
		    shape.end();
			
			// Draw screen elements
			spriteBatch.begin();
			spriteBatch.draw(ofellowTex, mascotPos.x, mascotPos.y, MASCOT_HEIGHT, MASCOT_HEIGHT);
			spriteBatch.draw(dialogueBox, dialoguePos.x, dialoguePos.y, dialogueWidth, MASCOT_HEIGHT);
			spriteBatch.setColor(1.0f, 1.0f, 1.0f, Math.abs(((float) Math.sin(timer*2)))); // Pulse effect
			spriteBatch.draw(enterKey, enterPos.x, enterPos.y, enterWidth, ENTER_HEIGHT);
			spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			dialogueFont.draw(spriteBatch, dialogueState.getMessage(), fontPos.x, fontPos.y, textWidth, Align.left , true);
			spriteBatch.end();
		}
		
		// Draw enter button in bottom right
		if(stateToDraw instanceof PieceHighlightState) {
			spriteBatch.begin();
			spriteBatch.setColor(1.0f, 1.0f, 1.0f, Math.abs(((float) Math.sin(timer*2))));
			spriteBatch.draw(enterKey, Othello.GAME_WORLD_WIDTH - DIALOGUE_PADDING - enterWidth * 2, DIALOGUE_PADDING, enterWidth*2, ENTER_HEIGHT*2);
			spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			spriteBatch.end();
		}
	}
	
	/**
	 * Enable or disable piece placement and ghosting under cursor
	 * @param enabled placement enabled
	 */
	private void setPlacementEnabled(Boolean enabled) {
		placementEnabled = enabled;
		boardRenderer.setDrawHighlight(enabled);
	}
	
	/**
	 * {@inheritDoc}
	 * <p>
	 * If {@code currentState} is an {@code AssistedMoveState} and the position is defined as valid, increment the 
	 * current state and return true. For all other states, return true if placement is enabled.
	 */
	@Override
	protected boolean checkInput(Position position) {
		if(!placementEnabled) return false;
		if(!(currentState instanceof AssistedMoveState)) return true;
		
		AssistedMoveState assistedMoveState = (AssistedMoveState) currentState;

		setPlacementEnabled(false);
		if(assistedMoveState.isValidMove(position)) {
			incrementState();
			return true;
		} else {
			assistedMoveState.setMessageActive(true);
			return false;
		}
	}
	
	/**
	 * {@inheritDoc}
	 * <p>
	 * Check if enter has been pressed, and if so advance the current state.
	 * <p>
	 * If {@code currentState} is an {@code AssistedMoveState} with an active message, set the message to inactive and
	 * re-enable placement to give the user another chance to place their piece.
	 * For all other states, increment the current state.
	 */
	@Override
	protected void postUpdate(float delta) {
	    if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
			if(!enterPreviouslyPressed) {
				if(currentState instanceof AssistedMoveState) {
					AssistedMoveState assistedMoveState = (AssistedMoveState) currentState;
					if(assistedMoveState.isMessageActive()) {
						// Enter pressed on "incorrect placement" message. Give the user another chance to place.
						assistedMoveState.setMessageActive(false);
						setPlacementEnabled(true);
					}
				} else {
					incrementState();
					enterPreviouslyPressed = true;
				}
			}
		} else {
			enterPreviouslyPressed = false;
		}	
	}
	
	// TODO move to superclass
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resize(int width, int height) {
		boardRenderer.resize(width, height);
		viewport.update(width, height);
		cam.position.set(width / 2, height / 2, 0);
	}
	
	/**
	 * Run necessary setup after changing to a new state.
	 * <p>
	 * <ul>
	 * <li> Null state - Return to main menu.
	 * <li> States implementing {@code HighlightableState} - Send highlight positions to the {@code BoardView}.
	 * <li> {@code AssistedMoveState} - Allow piece placement.
	 */
	private void stateInit() {
		currentState = sequence.getCurrentState();
		
		// If new state is empty, tutorial has finished, return to main menu
		if(currentState == null) {
			game.setScreen(new MainMenuScreen(game));
		}
		
		if(currentState instanceof HighlightableState) {
			ArrayList<Position> positions = ((HighlightableState) currentState).getHighlightPositions();
			if(positions != null) {
				for(Position pos: positions) {
					boardRenderer.addPieceHighlight(pos);
				}
			}
		}
		if(currentState instanceof AssistedMoveState) {
			setPlacementEnabled(true);
		}
	}
	
	/**
	 * Clean up previous state.
	 * <p>
	 * If state implements {@code HighlightableState} then reset all piece highlights in the {@code BoardView}.
	 */
	private void stateCleanup() {
		if(currentState instanceof HighlightableState) {
			boardRenderer.resetAllPieceHighlights();
		}
	}
	
	/**
	 * Run cleanup, increment the current state, then run init
	 */
	private void incrementState() {
		stateCleanup();
		sequence.incrementState();
		stateInit();
	}
	
	/**
	 * Dispose of textures when application closes
	 */
	@Override
	public void finalize() {
		mascotDefault.dispose();
		mascotShocked.dispose();
		dialogueBox.dispose();
		enterKey.dispose();
	}
}
