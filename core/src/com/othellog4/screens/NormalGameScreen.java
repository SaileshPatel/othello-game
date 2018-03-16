package com.othellog4.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.othellog4.Othello;
import com.othellog4.environment.Launcher;
import com.othellog4.game.GameException;
import com.othellog4.game.GameModel;
import com.othellog4.game.board.Piece;
import com.othellog4.game.board.Position;
import com.othellog4.graphics.GraphicsUtil;

/**
 * Represents the average game screen.
 * @author 	159014260 John Berg
 * @author BRUNO ZORIMA
 * @since 	07/12/2017
 * @version 07/12/2017
 */
public final class NormalGameScreen extends GameScreen
{
	private GameModel model;

	private BitmapFont whiteFontText;
	private BitmapFont blackFontText;
	private String blackPieceScore;
	private String whitePieceScore;
	int buttonWidth = 100;
	int buttonHeight = 100;
	float xPos = 0;
	float yPos = Othello.GAME_WORLD_HEIGHT - buttonHeight;
	private Texture blackPiece;
	private Texture whitePiece;
	private Texture play_pause_button;
	private Texture play_button;
	boolean gameOver = false;
	private Boolean isNotPlaying;

	/**
	 *
	 * @param model takes a {@link com.othellog4.game.GameModel Model} of the game
	 * @param game takes an instance of {@link com.othellog4.Othello Othello}
	 */
	public NormalGameScreen(
			final GameModel model,
			final Othello game)
	{
		super(model, game);
		super.setPlacementEnabled(true);
		this.model = model;

		isNotPlaying = model.isPlaying();

		whitePiece = GraphicsUtil.createMipMappedTex("whitepiece.png");
		blackPiece = GraphicsUtil.createMipMappedTex("blackpiece.png");
		play_pause_button = GraphicsUtil.createMipMappedTex("gui/pause_button.png");
		play_button = GraphicsUtil.createMipMappedTex("gui/play_button.png");


		FreeTypeFontGenerator titlegenerator = new FreeTypeFontGenerator(Gdx.files.internal("segoeuib.ttf"));
		FreeTypeFontParameter titleparameter = new FreeTypeFontParameter();
		titleparameter.size = 35; // Size in px
		titleparameter.spaceY = 5; // Vertical spacing
		whiteFontText = titlegenerator.generateFont(titleparameter);
		whiteFontText.setUseIntegerPositions(false);
		titlegenerator.dispose();
		whiteFontText.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		whiteFontText.setColor(1f, 1f, 1f, 1f);

		FreeTypeFontGenerator titleGen = new FreeTypeFontGenerator(Gdx.files.internal("segoeuib.ttf"));
		FreeTypeFontParameter titlePara = new FreeTypeFontParameter();
		titlePara.size = 35; // Size in px
		titlePara.spaceY = 5; // Vertical spacing
		blackFontText = titleGen.generateFont(titlePara);
		blackFontText.setUseIntegerPositions(false);
		titleGen.dispose();
		blackFontText.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		blackFontText.setColor(0.0f, 0.0f, 0.0f, 1.0f);
	}
	@Override
	protected boolean checkInput(Position position)
	{
		return true;
	}


	@Override
	protected void postRender(float delta) {
		Vector2 mousePos  = GraphicsUtil.getMousePos();
		blackPieceScore = Integer.toString(model.getBoard().count(Piece.PIECE_A));
		whitePieceScore = Integer.toString(model.getBoard().count(Piece.PIECE_B));
		int scoreB = model.getBoard().count(Piece.PIECE_B);
		int scoreA = model.getBoard().count(Piece.PIECE_A);
		SPRITE_BATCH.begin();
		SPRITE_BATCH.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		SPRITE_BATCH.draw(blackPiece, Othello.GAME_WORLD_WIDTH - 1.4f*buttonWidth, Othello.GAME_WORLD_HEIGHT - 1.5f*buttonHeight, buttonWidth, buttonHeight);
		SPRITE_BATCH.draw(whitePiece, Othello.GAME_WORLD_WIDTH - 2.7f*buttonWidth , Othello.GAME_WORLD_HEIGHT - 1.5f*buttonHeight , buttonWidth, buttonHeight);
		//check whether the game is being played and display either play or pause button according to the game state
		if(!isNotPlaying) {
			SPRITE_BATCH.draw(play_pause_button, Othello.GAME_WORLD_WIDTH - 2*buttonWidth , Othello.GAME_WORLD_HEIGHT - 2.50f*buttonHeight , buttonWidth, buttonHeight);
			if (mousePos.x >= Othello.GAME_WORLD_WIDTH - 2*buttonWidth && mousePos.x < Othello.GAME_WORLD_WIDTH - buttonWidth && mousePos.y >= Othello.GAME_WORLD_HEIGHT - 2.50f*buttonHeight && mousePos.y < Othello.GAME_WORLD_HEIGHT - 1.50f*buttonHeight) {
				if(Gdx.input.justTouched()) {
					try {
						model.pause();
						isNotPlaying=true;
					} catch(GameException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		else {
			SPRITE_BATCH.draw(play_button, Othello.GAME_WORLD_WIDTH - 2*buttonWidth , Othello.GAME_WORLD_HEIGHT - 2.50f*buttonHeight , buttonWidth, buttonHeight);
			if (mousePos.x >= Othello.GAME_WORLD_WIDTH - 2*buttonWidth && mousePos.x < Othello.GAME_WORLD_WIDTH - buttonWidth && mousePos.y >= Othello.GAME_WORLD_HEIGHT - 2.50f*buttonHeight && mousePos.y < Othello.GAME_WORLD_HEIGHT - 1.50f*buttonHeight) {
				if(Gdx.input.justTouched()) {
					try {
						model.resume();
						isNotPlaying=false;
					} catch(GameException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

		//Pause functionality implemented

		//Board score implementation
		SPRITE_BATCH.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		if(scoreA > 9 && scoreB > 9) {
			whiteFontText.draw(SPRITE_BATCH, blackPieceScore, 1490, 815 , 500, Align.left, true);
			blackFontText.draw(SPRITE_BATCH, whitePieceScore,1360 , 815, 500, Align.left, true);
		}
		else if (scoreA > 9) {
			whiteFontText.draw(SPRITE_BATCH, blackPieceScore, 1490, 815 , 500, Align.left, true);
			blackFontText.draw(SPRITE_BATCH, whitePieceScore,1370 , 815, 500, Align.left, true);
		}
		else if(scoreB > 9) {
			blackFontText.draw(SPRITE_BATCH, whitePieceScore,1360 , 815, 500, Align.left, true);
			whiteFontText.draw(SPRITE_BATCH, blackPieceScore, 1500, 815 , 500, Align.left, true);
		}
		else {
			whiteFontText.draw(SPRITE_BATCH, blackPieceScore, 1500, 815 , 500, Align.left, true);
			blackFontText.draw(SPRITE_BATCH, whitePieceScore,1370 , 815, 500, Align.left, true);
		}
		SPRITE_BATCH.end();

	}
	@Override
	protected void postUpdate(float delta) {
		setPlacementEnabled(model.isWaiting());
		if(!gameOver && model.isGameOver()) {
			Launcher.get().clear();
			backButtonEnabled = false;
			game.setScreen(new EndGameScreen(game, this, model.score()));
			gameOver = true;
		}

		updateBackButton(game, model);
	}

}
