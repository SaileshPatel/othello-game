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
	
//	int currentMessageIndex = 0;
//	String[] messages = {"[#000000]Hey there, my name is [#ff0000]O-fellow[#00000]! It looks like it's your first"
//			+ " time playing, so let me give you some tips!", 
//			"[#000000]The aim of the game is to get as many of your coloured pieces on the board as possible!",
//			"[#000000]Each turn you get to place a piece. Any of your opponents pieces between it and pieces you"
//			+ " already have down...",
//			"[#000000]...will be flipped!",
//			"[#000000]The game starts with a 2x2 grid so you can flip some pieces from the start.",
//			"[#000000]Make sure you think ahead, if you can't make a move that will flip pieces you'll have to"
//			+ " forefeit your turn!",
//			"[#000000]Lets make your first turn, place a piece in the highlighted position..."};
	
	Color gradientTop;
	Color gradientBottom;
	
	ShapeRenderer shape;
	
	Camera cam;
	Viewport viewport;

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
		DialogueState firstState = new DialogueState("Hey, welcome to othello, my name is [#ff0000]O-Fellow[#000000], let me show you how to play", MascotExpression.DEFAULT);
		DialogueState secondState = new DialogueState("First, look at these randomly highlighted spaces on the board", MascotExpression.DEFAULT);
		PieceHighlightState middleState = new PieceHighlightState(new ArrayList<Position>(Arrays.asList(Position.at(1,1), Position.at(1, 2), Position.at(2, 1), Position.at(2, 2))));
		DialogueState beforePlace = new DialogueState("Now, place a piece in the highligted square!", MascotExpression.DEFAULT);
		AssistedMoveState moveState = new AssistedMoveState((new ArrayList<Position>(Arrays.asList(Position.at(4,2)))), true, new DialogueState("W-what are you doing? That's not the right place!", MascotExpression.SHOCKED));
		DialogueState thirdState = new DialogueState("Well done, you've completed the tutorial!", MascotExpression.DEFAULT);
		
		sequence = new TutorialSequence(new ArrayList<TutorialState>(Arrays.asList(firstState, secondState, middleState, beforePlace, moveState, thirdState)));
		
		stateInit();
	}
	
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
	
	private void setPlacementEnabled(Boolean bool) {
		placementEnabled = bool;
		boardRenderer.setDrawHighlight(bool);
	}
	
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
	
	@Override
	protected void postUpdate(float delta) {
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
			if(!enterPreviouslyPressed) {
				if(currentState instanceof AssistedMoveState) {
					AssistedMoveState assistedMoveState = (AssistedMoveState) currentState;
					if(assistedMoveState.isMessageActive()) {
						System.out.println("Is message active");
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
	@Override
	public void resize(int width, int height) {
		boardRenderer.resize(width, height);
		viewport.update(width, height);
		cam.position.set(width / 2, height / 2, 0);
	}
	
	private void stateInit() {
		currentState = sequence.getCurrentState();
		
		// If new state is empty, tutorial has finished, return to main menu
		if(currentState == null) {
			game.setScreen(new MainMenuScreen(game));
		}
		
		if(currentState instanceof HighlightableState) {
			System.out.println("Highlightable state!");
			for(Position pos: ((HighlightableState) currentState).getHighlightPositions()) {
				boardRenderer.addPieceHighlight(pos);
			}
		}
		if(currentState instanceof AssistedMoveState) {
			setPlacementEnabled(true);
		}
	}
	
	private void stateCleanup() {
		if(currentState instanceof HighlightableState) {
			boardRenderer.resetAllPieceHighlights();
		}
	}
	
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
