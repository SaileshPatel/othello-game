package com.othellog4.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.othellog4.Othello;
import com.othellog4.environment.Launcher;
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
	protected GameModel model;

	private BitmapFont scoreFont;
	private BitmapFont whiteFont;
	private String blackScore;
	private String whiteScore;
	int buttonWidth = 100;
	int buttonHeight = 100;
	float xPos = 0;
	float yPos = Othello.GAME_WORLD_HEIGHT - buttonHeight;
	private Texture blackPiece;
	private Texture whitePiece;
	boolean gameOver = false;

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

		whitePiece = GraphicsUtil.createMipMappedTex("whitepiece.png");
		blackPiece = GraphicsUtil.createMipMappedTex("blackpiece.png");

		scoreFont = GraphicsUtil.generateFont("segoeuib.ttf", 35, 5);
		scoreFont.setColor(1f, 1f, 1f, 1f);

		whiteFont = GraphicsUtil.generateFont("segoeuib.ttf", 35, 5);
		whiteFont.setColor(0.0f, 0.0f, 0.0f, 1.0f);

		// Add disposable objects to cleanup list
		disposables.add(scoreFont);
		disposables.add(whiteFont);
		disposables.add(blackPiece);
		disposables.add(whitePiece);
	}
	@Override
	protected boolean checkInput(Position position)
	{
		return true;
	}


	@Override
	protected void postRender(float delta) {
		blackScore = Integer.toString(model.getBoard().count(Piece.PIECE_A));
		whiteScore = Integer.toString(model.getBoard().count(Piece.PIECE_B));
		int ScoreB = model.getBoard().count(Piece.PIECE_B);
		int scoreA = model.getBoard().count(Piece.PIECE_A);
		SPRITE_BATCH.begin();
		SPRITE_BATCH.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		SPRITE_BATCH.draw(blackPiece, Othello.GAME_WORLD_WIDTH - 1.4f*buttonWidth, Othello.GAME_WORLD_HEIGHT - 1.5f*buttonHeight, buttonWidth, buttonHeight);
		SPRITE_BATCH.draw(whitePiece, Othello.GAME_WORLD_WIDTH - 2.7f*buttonWidth , Othello.GAME_WORLD_HEIGHT - 1.5f*buttonHeight , buttonWidth, buttonHeight);
		SPRITE_BATCH.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		scoreFont.draw(SPRITE_BATCH, blackScore, 1500, 815 , 500, Align.left, true);
		whiteFont.draw(SPRITE_BATCH, whiteScore,1370 , 815, 500, Align.left, true);
		SPRITE_BATCH.end();

	}
	@Override
	protected void postUpdate(float delta) {
		setPlacementEnabled(model.isWaiting());
		if(!gameOver && model.isGameOver()) {
			Launcher.get().clear();
			System.out.println("Creating new end game screen");
			game.setScreen(new EndGameScreen(game, this, model.score()));
			gameOver = true;
		}
	}

}
