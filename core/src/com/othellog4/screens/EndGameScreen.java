package com.othellog4.screens;

import java.time.LocalTime;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.othellog4.Othello;
import com.othellog4.graphics.GraphicsUtil;

/**
 * Screen displayed after a game has finished
 * <p>
 * Displays whether the player has won or lost, along with some statistics
 * 
 * @author James Shorthouse
 * @version 13/02/2018
 */
public class EndGameScreen extends ScreenAdapter{
	
	private boolean won;
	private int numPieces;
	private int timeTaken;
	private int numFlips;
	private int totalScore;
	private GameScreen screen; //  The screen of the game to be rendered behind the victory screen
	private Othello game;
	private SpriteBatch batch;
	private BitmapFont largeFont;
	private BitmapFont mediumFont;
	private BitmapFont smallFont;

	private Camera cam;
	private Viewport viewport; //TODO find a way to reuse viewports so we don't have to keep doing this every time

	private ShapeRenderer shape;

	private Color gradientTop;
	private Color gradientBottom;
	
	private Color messageColor;
	
	private String messageText;
	private float messageX;
	
	//Animation variables
	private float elapsedTime = 0f;
	private float animGradientAlpha = 0f;
	private float animTextBackgroundWidth = 0f;
	private float animTextAlpha = 0f;
	private float animTextBackgroundStartingHeight = 300;
	private float animTextBackgroundHeight = animTextBackgroundStartingHeight;
	private float animTextStartingYPos;
	private float animTextYPos;
	
	private int mascotWidth = 600;
	private float mascotStartingXPos = 0 - mascotWidth;
	private float mascotFinalXPos = 50;
	private float animMascotXPos = mascotStartingXPos;
	private float animTextStatsTimeXPos = Othello.GAME_WORLD_WIDTH;
	private float animTextStatsFlipsXPos = Othello.GAME_WORLD_WIDTH;
	private float animTextStatsPiecesXPos = Othello.GAME_WORLD_WIDTH;
	private float animTextStatsTotalXPos = Othello.GAME_WORLD_WIDTH;
	private float animEnterAlpha = 0;
	
	private float textHeight;
	
	Texture mascot;
	Texture enterKey;
	
	private String textTimeTaken;
	private String textNumPieces;
	private String textNumFlips;
	private String textTotalScore;
	
	private float statsTextX = 700;
	private float statsTextTop = 550;
	private float statsTextSpacing = 100;
	
	float ENTER_HEIGHT = 125;
	float enterWidth;
	
	private boolean animationFinished = false;
	
	public EndGameScreen(Othello game, GameScreen screen, int winState, int timeTaken, int numFlips, int numPieces) {
		int WIN = 0;
		int LOSE = 1;
		int DRAW = 2;
		int ABANDON = 3;
		
		this.screen = screen;
		this.timeTaken = timeTaken;
		this.numFlips = numFlips;
		this.numPieces = numPieces;
		this.game = game;
		this.batch = game.getSpriteBatch();
		
		shape = new ShapeRenderer();
		
		cam = new OrthographicCamera();
		cam.position.set((Othello.GAME_WORLD_WIDTH/1) / 2, (Othello.GAME_WORLD_HEIGHT/1) / 2, 0);
		viewport = new FitViewport(Othello.GAME_WORLD_WIDTH/1, Othello.GAME_WORLD_HEIGHT/1, cam);
		viewport.apply();
		shape.setProjectionMatrix(cam.combined);
		
		largeFont = GraphicsUtil.generateFont("fonts/overpass-bold.otf", 200, 0);
		
		smallFont = GraphicsUtil.generateFont("fonts/overpass-regular.otf", 75, -25);
		mediumFont = GraphicsUtil.generateFont("fonts/overpass-regular.otf", 100, -25);
		
		gradientTop = new Color(0.0f, 0.0f, 0.0f, 0.8f);
		gradientBottom = new Color(0.0f, 0.0f, 0.0f, 0.5f);
		
		enterKey = GraphicsUtil.createMipMappedTex("key_icons/enter.png");
		enterWidth = ((float) ENTER_HEIGHT / (float) enterKey.getHeight()) * enterKey.getWidth();
		
		if(winState == DRAW) {
			messageText = "DRAW!";
			messageColor = new Color(0.3f, 0.3f, 0.3f, 1f);
			mascot = GraphicsUtil.createMipMappedTex("mascot/end_screen/draw.png");
			
		} else if (winState == ABANDON) {
			messageText = "ABANDONED!";
			messageColor = new Color(0.6f, 0.3f, 0.3f, 1f);
			mascot = GraphicsUtil.createMipMappedTex("mascot/end_screen/abandon.png");
			mascotFinalXPos = 0;
		}
		else if(winState == WIN) {
			messageText = "YOU WIN!";
			messageColor = new Color(0.3f, 0.72f, 0.03f, 1f);
			mascot = GraphicsUtil.createMipMappedTex("mascot/end_screen/win.png");
			
		} else {
			messageText = "YOU LOSE!";
			messageColor = new Color(1f, 0.27f, 0.18f, 1f);
			mascot = GraphicsUtil.createMipMappedTex("mascot/end_screen/lose.png");
		}
		
		// Convert time from seconds to standard format
		LocalTime localTime = LocalTime.ofSecondOfDay(timeTaken);
		String formattedTime = "";
		
		// Only show hours if time was > 1h
		if(localTime.getHour() > 0) {
			formattedTime += localTime.getHour() + "h ";
		}
		formattedTime += localTime.getMinute() + "m " + localTime.getSecond() + "s ";
		
		textTimeTaken = "Time taken: " + formattedTime.toString();
		textNumPieces = "Number of pieces: " + numPieces;
		textNumFlips = "Number of flips: " + numFlips;
		textTotalScore = "Total score: " + "TBD";
		
		// Use GlyphLayout to calculate position of text
		GlyphLayout glyph = new GlyphLayout(largeFont, messageText);
		messageX = (Othello.GAME_WORLD_WIDTH / 2) - (glyph.width / 2);
		animTextYPos = animTextStartingYPos = (Othello.GAME_WORLD_HEIGHT / 2) + (glyph.height / 2);
		textHeight = glyph.height;
	}
	
	//Animation stage timings
	private final float stage1Start = 0.0f; // Fade out
	private final float stage2Start = 2.0f; // Text background slide in
	private final float stage3Start = 2.5f; // Text fade in
	private final float stage4Start = 3.5f; // Text background expansion
	private final float stage5Start = 5.0f; // Stats in
	private final float animEndTime = 8.0f;
	
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
		
		// Since animation timing is based off delta time, there is no guarantee that each stage will fully complete
		// or even be run at all.
		// Therefore, each stage should explicitly set the properties of all previously animated elements.
		if(elapsedTime < stage2Start) {
			// Fade out
			//animGradientAlpha = elapsedTime / stage1End;
			animGradientAlpha = GraphicsUtil.smoothAnimationBetween(elapsedTime, 0f, stage1Duration);
			
		} else if(elapsedTime < stage3Start) {
			// Text background slide in
			animGradientAlpha = 1.0f;
			
			//animTextBackgroundWidth = ((elapsedTime - stage1End) / (stage2End - stage1End)) * Othello.GAME_WORLD_WIDTH;
			
			animTextBackgroundWidth = GraphicsUtil.smoothAnimationBetween(elapsedTime, stage2Start, stage2Duration) *
					Othello.GAME_WORLD_WIDTH;
			
		} else if(elapsedTime < stage4Start) {
			// Text fade in
			animGradientAlpha = 1.0f;
			animTextBackgroundWidth = Othello.GAME_WORLD_WIDTH;
			
			//animTextAlpha = Math.min(1, ((elapsedTime - stage2End) / (stage3End - stage2End)) * 2);
			if(elapsedTime < stage3Start + (stage4Start - stage3Start) / 2) {
				animTextAlpha = GraphicsUtil.smoothAnimationBetween(elapsedTime, stage3Start, stage3Duration/2);
			} else {
				animTextAlpha = 1;
			}
			
		} else if(elapsedTime < stage5Start) {
			// Text background expansion
			animGradientAlpha = 1.0f;
			animTextAlpha = 1;
			
			//animTextBackgroundHeight = ((elapsedTime - stage3End) / (stage4End - stage3End)) * 
			//		(Othello.GAME_WORLD_HEIGHT - animTextBackgroundStartingHeight)  + animTextBackgroundStartingHeight;
			
			animTextBackgroundHeight = GraphicsUtil.smoothAnimationBetween(elapsedTime, stage4Start, stage4Duration, 5) *
					(Othello.GAME_WORLD_HEIGHT - animTextBackgroundStartingHeight)  + animTextBackgroundStartingHeight;
			
			// Text move up
//			animTextYPos = ((elapsedTime - stage3End) / (stage4End - stage3End)) * 
//					((Othello.GAME_WORLD_HEIGHT / 2) - (textHeight / 2) - ((animTextBackgroundStartingHeight - textHeight) /2))
//					+ animTextStartingYPos;
			
			animTextYPos = GraphicsUtil.smoothAnimationBetween(elapsedTime, stage4Start, stage4Duration, 5) *
					((Othello.GAME_WORLD_HEIGHT / 2) - (textHeight / 2) - ((animTextBackgroundStartingHeight - textHeight) /2))
					+ animTextStartingYPos;
			
			
			
		} else if(elapsedTime < animEndTime){
			//Stats in
			animGradientAlpha = 1.0f;
			animTextAlpha = 1;
			animTextBackgroundHeight = Othello.GAME_WORLD_HEIGHT;
			animTextYPos = ((Othello.GAME_WORLD_HEIGHT / 2) - (textHeight / 2) - ((animTextBackgroundStartingHeight - textHeight) /2))
					+ animTextStartingYPos;
			
			animMascotXPos = (GraphicsUtil.smoothAnimationBetween(elapsedTime, stage5Start, mascotEntryDuration) * 
					(Math.abs(mascotStartingXPos) + Math.abs(mascotFinalXPos))) + mascotStartingXPos;
			
			animTextStatsTimeXPos = statsTextX +
					((1-GraphicsUtil.smoothAnimationBetween(elapsedTime, stage5Start, textEntryDuration)) * 
					(Othello.GAME_WORLD_WIDTH - statsTextX));
			
			animTextStatsFlipsXPos = statsTextX +
					((1-GraphicsUtil.smoothAnimationBetween(elapsedTime, stage5Start + textEntryOffset, textEntryDuration)) * 
					(Othello.GAME_WORLD_WIDTH - statsTextX));

			animTextStatsPiecesXPos = statsTextX +
					((1-GraphicsUtil.smoothAnimationBetween(elapsedTime, stage5Start + (textEntryOffset * 2), textEntryDuration)) * 
					(Othello.GAME_WORLD_WIDTH - statsTextX));
			
			animTextStatsTotalXPos = statsTextX +
					((1-GraphicsUtil.smoothAnimationBetween(elapsedTime, stage5Start + (textEntryOffset * 4), textEntryDuration)) * 
					(Othello.GAME_WORLD_WIDTH - statsTextX));
		}
		
		
		
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER) && elapsedTime > animEndTime) {
			// Return to main menu
			game.setScreen(new MainMenuScreen(game));
		}
		
		if(elapsedTime > animEndTime) {
			animEnterAlpha = Math.abs(((float) Math.sin((elapsedTime - animEndTime)*2)));
		}
	}
	
	public void render(float delta) {
		update(delta);
		// Render game in background
		screen.render(delta);
		
		// TODO move these methods
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		gradientTop.a = 0.8f * animGradientAlpha;
		gradientBottom.a = 0.5f * animGradientAlpha;
		
		shape.begin(ShapeType.Filled);
	    shape.rect(0, 0, Othello.GAME_WORLD_WIDTH, Othello.GAME_WORLD_HEIGHT, gradientTop, gradientTop,
	    		gradientBottom, gradientBottom);
	    shape.setColor(messageColor);
	    shape.rect(0, (Othello.GAME_WORLD_HEIGHT/2) - animTextBackgroundHeight/2, animTextBackgroundWidth, animTextBackgroundHeight);
	    shape.end();
		
		// Render end screen
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		largeFont.setColor(1f, 1f, 1f, animTextAlpha);
		largeFont.draw(batch, messageText, messageX, animTextYPos);
		
		// Temporary negative width to draw mascot flipped
		batch.draw(mascot, animMascotXPos, 25f, mascotWidth, mascotWidth);
		batch.setColor(1.0f, 1.0f, 1.0f, animEnterAlpha);
		batch.draw(enterKey, (Othello.GAME_WORLD_WIDTH - 50) - enterWidth, 50, enterWidth, ENTER_HEIGHT);
		batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		smallFont.draw(batch, textTimeTaken, animTextStatsTimeXPos, statsTextTop);
		smallFont.draw(batch, textNumFlips, animTextStatsFlipsXPos, statsTextTop - statsTextSpacing);
		smallFont.draw(batch, textNumPieces, animTextStatsPiecesXPos, statsTextTop - statsTextSpacing * 2);
		mediumFont.draw(batch, textTotalScore, animTextStatsTotalXPos, statsTextTop - statsTextSpacing * 3);
		
		batch.end();
	}
	
	public void resize(int width, int height)
	{
		screen.resize(width, height);
	}
}
