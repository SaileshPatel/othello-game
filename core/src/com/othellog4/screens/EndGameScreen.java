package com.othellog4.screens;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.othellog4.Othello;
import com.othellog4.game.GameConclusion;
import com.othellog4.game.GameResult;
import com.othellog4.game.GameScore;
import com.othellog4.game.board.BoardView;
import com.othellog4.game.board.Piece;
import com.othellog4.game.extension.FlipCounter;
import com.othellog4.game.extension.GameExtension;
import com.othellog4.game.player.Participant;
import com.othellog4.graphics.GraphicsUtil;

/**
 * Screen displayed after a game has finished
 * <p>
 * Displays whether the player has won or lost, along with some statistics
 *
 * @author James Shorthouse
 * @version 13/02/2018
 */
public class EndGameScreen extends BaseScreen {

	// The screen of the game to be displayed behind the victory screen
	private GameScreen screen;
	private Othello game;
	private BitmapFont largeFont;
	private BitmapFont mediumFont;
	private BitmapFont smallFont;

	private Color gradientTop;
	private Color gradientBottom;

	private Color messageColor;

	private String messageText;
	private float messageX;

	// Animation constants
	private static int TIME_TAKEN = 0;
	private static int NUM_FLIPS = 1;
	private static int NUM_PIECES = 2;
	private static int TOTAL_SCORE = 3;

	private float[] textXPositions = new float[4];
	private float[] textYPositions = new float[4];
	private String[] textText = new String[4];

	//Animation variables
	private float elapsedTime = 0f;
	private float animGradientAlpha = 0f;
	private float animTextBackgroundWidth = 0f;
	private float animTextAlpha = 0f;
	private float animTextBackgroundStartingHeight = 300;
	private float animTextBackgroundHeight = animTextBackgroundStartingHeight;
	private float animTextStartingYPos;
	private float animTextFinalYPos = 822;
	private float animTextYDistance;
	private float animTextYPos;

	private int mascotWidth = 600;
	private float mascotStartingXPos = 0 - mascotWidth;
	private float mascotFinalXPos = 50;
	private float animMascotDistance;
	private float animMascotXPos = mascotStartingXPos;
	private float animEnterKeyAlpha = 0;

	Texture mascot;
	Texture enterKey;

	private float statsTextFinalX = 700; //TODO rename to finalX
	private float animStatsTextDistance;
	private float statsTextTop = 580;
	private float statsTextSpacing = 40;

	float ENTER_HEIGHT = 125;
	float enterWidth;

	private boolean animationFinished = false;

	public EndGameScreen(Othello game, GameScreen screen, GameScore gameScore) {

		int winState;

		int WIN = 0;
		int LOSE = 1;
		int DRAW = 2;
		int ABANDON = 3;
		int WHITE_WINS = 4;
		int BLACK_WINS = 5;

		Set<Participant> humanPlayers = gameScore.getControlable();

		GameConclusion conclusion = gameScore.conclusion();
		Map<Class<? extends GameExtension>, GameResult> result = gameScore.results();

		int numFlips = result.get(FlipCounter.class).player1Result();
		int p2NumFlips = result.get(FlipCounter.class).player2Result();

		BoardView board = gameScore.getBoard();

		int numPieces = board.count(Piece.PIECE_A); // Black (P1)
		int p2NumPieces = board.count(Piece.PIECE_B); // White (P2)

		//int timeTaken = result.get(Timer.class).player1Result() + result.get(Time.class).player2Result();
		int timeTaken = 5;

		boolean singlePlayer = true;


		if(conclusion.isDraw()) {
			winState = DRAW;
		}
		else if(humanPlayers.size() != 1) {
			System.out.println("Controllable players: " + humanPlayers.size());
			singlePlayer = false;
			if(conclusion.getWinner() == Piece.PIECE_A) {// Black
				winState = BLACK_WINS;
			} else {
				winState = WHITE_WINS;
			}
		} else {
			// Single player
			if(gameScore.winner() == humanPlayers.iterator().next()) {
				winState = WIN;
			} else {
				winState = LOSE;
			}
		}

		int score = 50;
		int p2Score = 100;


		Arrays.fill(textXPositions, Othello.GAME_WORLD_WIDTH);
		Arrays.fill(textYPositions, 200);
		animMascotDistance = mascotFinalXPos - mascotStartingXPos;
		animStatsTextDistance = Othello.GAME_WORLD_WIDTH - statsTextFinalX;



		String player1Name = "BLACK";
		String player2Name = "WHITE";

		this.screen = screen;
		this.game = game;

		largeFont = GraphicsUtil.generateFont("fonts/overpass-bold.otf", 200, 0);

		if(singlePlayer){
			smallFont = GraphicsUtil.generateFont
				("fonts/overpass-regular.otf", 75, -25);
			mediumFont = GraphicsUtil.generateFont
				("fonts/overpass-bold.otf", 90, -25);
		} else {
			smallFont = GraphicsUtil.generateFont
				("fonts/overpass-regular.otf", 50, -20);
			mediumFont = GraphicsUtil.generateFont
				("fonts/overpass-bold.otf", 65, -35);
		}

		gradientTop = new Color(0.0f, 0.0f, 0.0f, 0.8f);
		gradientBottom = new Color(0.0f, 0.0f, 0.0f, 0.5f);

		enterKey = GraphicsUtil.createMipMappedTex("key_icons/enter.png");
		enterWidth = (ENTER_HEIGHT / enterKey.getHeight()) *
				enterKey.getWidth();

		if(singlePlayer) {
			if(winState == DRAW) {
				messageText = "DRAW!";
				messageColor = new Color(0.3f, 0.3f, 0.3f, 1f);
				mascot = GraphicsUtil.createMipMappedTex
						("mascot/end_screen/draw.png");

			} else if (winState == ABANDON) {
				messageText = "ABANDONED!";
				messageColor = new Color(0.6f, 0.3f, 0.3f, 1f);
				mascot = GraphicsUtil.createMipMappedTex
						("mascot/end_screen/abandon.png");
				mascotFinalXPos = 0;
			}
			else if(winState == WIN) {
				messageText = "YOU WIN!";
				messageColor = new Color(0.3f, 0.72f, 0.03f, 1f);
				mascot = GraphicsUtil.createMipMappedTex
						("mascot/end_screen/win.png");

			} else {
				messageText = "YOU LOSE!";
				messageColor = new Color(1f, 0.27f, 0.18f, 1f);
				mascot = GraphicsUtil.createMipMappedTex
						("mascot/end_screen/lose.png");
			}

		textText[NUM_PIECES] = "Number of pieces: " + numPieces;
		textText[NUM_FLIPS] = "Number of flips: " + numFlips;
		textText[TOTAL_SCORE] = "Total score: " + score;

		} else {
			if(winState == BLACK_WINS){
				messageText = player1Name.toUpperCase() + " WINS!";
				mascot = GraphicsUtil.createMipMappedTex
						("mascot/end_screen/win.png"); //TODO change to actual picture
			} else if(winState == WHITE_WINS){
				messageText = player2Name + " WINS!";
				mascot = GraphicsUtil.createMipMappedTex
						("mascot/end_screen/win.png");
			}
			textText[NUM_PIECES] = "Number of pieces:\n" + player1Name + ": " +
				numPieces + "     " + player2Name + ": " + p2NumPieces;
			textText[NUM_FLIPS] = "Number of flips:\n" + player1Name + ": " +
				numFlips + "     " + player2Name + ": " + p2NumFlips;
			textText[TOTAL_SCORE] = "Total score:\n" + player1Name + ": " +
				score + "\n" + player2Name + ": " + p2Score;
			messageColor = new Color(0.3f, 0.72f, 0.03f, 1f);
		}

		// Convert time from seconds to standard format
		LocalTime localTime = LocalTime.ofSecondOfDay(timeTaken);
		String formattedTime = "";

		// Only show hours if time was > 1h
		if(localTime.getHour() > 0) {
			formattedTime += localTime.getHour() + "h ";
		}
		formattedTime += localTime.getMinute() + "m " +
			localTime.getSecond() + "s ";
		textText[TIME_TAKEN] = "Time taken: " + formattedTime.toString();

		//if(!singlePlayer) textTimeTaken = "\n" + textTimeTaken;

		// Use GlyphLayout to calculate position of text
		GlyphLayout glyph = new GlyphLayout(largeFont, messageText);
		messageX = (Othello.GAME_WORLD_WIDTH / 2) - (glyph.width / 2);
		animTextYPos = animTextStartingYPos = (Othello.GAME_WORLD_HEIGHT / 2) +
			(glyph.height / 2);
		animTextYDistance = animTextFinalYPos - animTextYPos;

		// Calculate text positions
		textYPositions[TIME_TAKEN] = statsTextTop;
		textYPositions[TOTAL_SCORE] = 230;

		for(int text = NUM_FLIPS; text <= NUM_PIECES; text++){
			// Find height of text before
			float prevHeight =
				new GlyphLayout(smallFont, textText[text-1]).height;

			textYPositions[text] =
				textYPositions[text-1] - (prevHeight + statsTextSpacing);
		}
	}

	//Animation stage timings
	private final float stage1Start = 0.0f; // Screen fade out
	private final float stage2Start = 2.0f; // Text background slide in
	private final float stage3Start = 2.5f; // Text fade in
	private final float stage4Start = 3.5f; // Text background expansion
	private final float stage5Start = 5.0f; // Stats in
	private final float animEndTime = 8.0f; // All animation complete

	private final float textEntryOffset = 0.4f;

	private final float stage1Duration = stage2Start;
	private final float stage2Duration = stage3Start - stage2Start;
	private final float stage3Duration = stage4Start - stage3Start;
	private final float stage4Duration = stage5Start - stage4Start;
	private final float stage5Duration = animEndTime - stage5Start;

	private final float mascotEntryDuration = 0.5f;
	private final float textEntryDuration = 0.5f;

	public void update(float delta) {
		elapsedTime += delta;

		// Since animation timing is based off delta time, there is no guarantee
		// that each stage will fully complete, or even be run at all.
		// Therefore, each stage should explicitly set the properties of all
		// previously animated elements.
		if(elapsedTime < stage2Start) {
			// Fade out
			animGradientAlpha = GraphicsUtil.smoothAnimationBetween(elapsedTime,
					0f, stage1Duration);

		} else if(elapsedTime < stage3Start) {
			// Text background slide in

			// Previous properties
			animGradientAlpha = 1.0f;

			animTextBackgroundWidth = GraphicsUtil.smoothAnimationBetween(
					elapsedTime, stage2Start, stage2Duration) *
					Othello.GAME_WORLD_WIDTH;

		} else if(elapsedTime < stage4Start) {
			// Text fade in

			// Previous properties
			animGradientAlpha = 1.0f;
			animTextBackgroundWidth = Othello.GAME_WORLD_WIDTH;

			// Only animate for first half of stage
			if(elapsedTime < stage3Start + stage3Duration / 2) {
				animTextAlpha = GraphicsUtil.smoothAnimationBetween(elapsedTime,
						stage3Start, stage3Duration/2);
			} else {
				animTextAlpha = 1;
			}

		} else if(elapsedTime < stage5Start) {
			// Text background expansion

			// Previous properties
			animGradientAlpha = 1.0f;
			animTextBackgroundWidth = Othello.GAME_WORLD_WIDTH;
			animTextAlpha = 1;

			// Background expansion
			animTextBackgroundHeight = (GraphicsUtil.smoothAnimationBetween(
					elapsedTime, stage4Start, stage4Duration, 5) *
					(Othello.GAME_WORLD_HEIGHT - animTextBackgroundStartingHeight))
					+ animTextBackgroundStartingHeight;

			// Text move up
			animTextYPos = (GraphicsUtil.smoothAnimationBetween(elapsedTime,
					stage4Start, stage4Duration, 5) * animTextYDistance)
					+ animTextStartingYPos;

		} else if(elapsedTime < animEndTime){
			// Stats in

			// Previous properties
			animGradientAlpha = 1.0f;
			animTextAlpha = 1;
			animTextBackgroundHeight = Othello.GAME_WORLD_HEIGHT;
			animTextYPos = animTextFinalYPos;

			animMascotXPos = (GraphicsUtil.smoothAnimationBetween(elapsedTime,
					stage5Start, mascotEntryDuration) *  animMascotDistance) +
					mascotStartingXPos;

			textXPositions[TIME_TAKEN] = statsTextFinalX +
					(1 - GraphicsUtil.smoothAnimationBetween(elapsedTime,
					stage5Start, textEntryDuration)) * animStatsTextDistance;

			textXPositions[NUM_FLIPS] = statsTextFinalX +
					(1 - GraphicsUtil.smoothAnimationBetween(elapsedTime,
					stage5Start + textEntryOffset, textEntryDuration)) *
					animStatsTextDistance;

			textXPositions[NUM_PIECES] = statsTextFinalX +
					(1 - GraphicsUtil.smoothAnimationBetween(elapsedTime,
					stage5Start + textEntryOffset * 2, textEntryDuration)) *
					animStatsTextDistance;

			textXPositions[TOTAL_SCORE] = statsTextFinalX +
					(1 - GraphicsUtil.smoothAnimationBetween(elapsedTime,
					stage5Start + textEntryOffset * 3, textEntryDuration)) *
					animStatsTextDistance;
		} else {
			// Animation end state

			// Ensure all elements are in final positions
			if(!animationFinished){
				animGradientAlpha = 1.0f;
				animTextBackgroundWidth = Othello.GAME_WORLD_WIDTH;
				animTextAlpha = 1;
				Arrays.fill(textXPositions, statsTextFinalX);
				animationFinished = true;
			}

			if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
				// Return to main menu
				game.setScreen(new MainMenuScreen(game));
			}

			animEnterKeyAlpha =
				Math.abs((float) Math.sin((elapsedTime - animEndTime)*2));
		}

	}

	@Override
	public void render(float delta) {
		update(delta);
		// Render game in background
		screen.render(delta);

		gradientTop.a = 0.8f * animGradientAlpha;
		gradientBottom.a = 0.5f * animGradientAlpha;

		// Draw background gradient and background colour
		Gdx.gl.glEnable(GL20.GL_BLEND);
		SHAPE_RENDER.begin(ShapeType.Filled);
		SHAPE_RENDER.rect(0, 0, Othello.GAME_WORLD_WIDTH, Othello.GAME_WORLD_HEIGHT,
				gradientTop, gradientTop, gradientBottom, gradientBottom);
		SHAPE_RENDER.setColor(messageColor);
		SHAPE_RENDER.rect(0, (Othello.GAME_WORLD_HEIGHT/2) - animTextBackgroundHeight/2,
				animTextBackgroundWidth, animTextBackgroundHeight);
		SHAPE_RENDER.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);

		// Draw text and textures
		SPRITE_BATCH.begin();
		largeFont.setColor(1f, 1f, 1f, animTextAlpha);
		largeFont.draw(SPRITE_BATCH, messageText, messageX, animTextYPos);
		SPRITE_BATCH.setColor(1.0f, 1.0f, 1.0f, animEnterKeyAlpha);
		SPRITE_BATCH.draw(enterKey, (Othello.GAME_WORLD_WIDTH - 50) - enterWidth, 50,
				enterWidth, ENTER_HEIGHT);

		SPRITE_BATCH.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		SPRITE_BATCH.draw(mascot, animMascotXPos, 25f, mascotWidth, mascotWidth);
		smallFont.draw(SPRITE_BATCH, textText[TIME_TAKEN], textXPositions[TIME_TAKEN],
				textYPositions[TIME_TAKEN]);
		smallFont.draw(SPRITE_BATCH, textText[NUM_FLIPS],  textXPositions[NUM_FLIPS],
				textYPositions[NUM_FLIPS]);
		smallFont.draw(SPRITE_BATCH, textText[NUM_PIECES], textXPositions[NUM_PIECES],
				textYPositions[NUM_PIECES]);
		mediumFont.draw(SPRITE_BATCH, textText[TOTAL_SCORE], textXPositions[TOTAL_SCORE],
				textYPositions[TOTAL_SCORE]);

		SPRITE_BATCH.end();
	}
}
